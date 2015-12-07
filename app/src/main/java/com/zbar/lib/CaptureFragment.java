package com.zbar.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseFragment;
import com.zbar.lib.camera.CameraManager;
import com.zbar.lib.decode.CaptureActivityHandler;
import com.zbar.lib.decode.InactivityTimer;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class CaptureFragment extends BaseFragment implements SurfaceHolder.Callback {

    @Bind(R.id.capture_containter)
    RelativeLayout mContainer;
    @Bind(R.id.capture_crop_layout)
    RelativeLayout mCropLayout;
    @Bind(R.id.capture_scan_line)
    ImageView mQrLineView;
    @Bind(R.id.capture_preview)
    SurfaceView surfaceView;

    final static int QRCODE=1;
    final static int BARCODE=2;
    int type=0;

    CaptureActivityHandler handler;
    boolean hasSurface;
    InactivityTimer inactivityTimer;
    MediaPlayer mediaPlayer;
    boolean playBeep;
    static final float BEEP_VOLUME = 0.50f;
    boolean vibrate;
    int x = 0;
    int y = 0;
    int cropWidth = 0;
    int cropHeight = 0;
    boolean isNeedCapture = false;

    CaptureCallBackListener listener=null;

    public static CaptureFragment getInstance(int type) {
        CaptureFragment fragment=new CaptureFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView() {
        type=getArguments().getInt("type");
        if (type==QRCODE) {
            return R.layout.fragment_qrscan;
        }
        else {
            return R.layout.fragment_barscan;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener=(CaptureCallBackListener) ((Activity) context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // 初始化 CameraManager
        CameraManager.init(getActivity().getApplicationContext());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(getActivity());

        TranslateAnimation mAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
        mAnimation.setDuration(1500);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        mQrLineView.setAnimation(mAnimation);
    }

    boolean flag = true;
    protected void light() {
        if (flag == true) {
            flag = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            flag = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        playBeep = true;
        AudioManager audioService = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    public void handleDecode(String result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        if (result.equals("")) {
            listener.onCaptureFail();
            // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
            // handler.sendEmptyMessage(R.id.restart_preview);
        } else {
            listener.onCaptureSuccess(result);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);

            Point point = CameraManager.get().getCameraResolution();
            int width = point.y;
            int height = point.x;

            int x = mCropLayout.getLeft() * width / mContainer.getWidth();
            int y = mCropLayout.getTop() * height / mContainer.getHeight();

            int cropWidth = mCropLayout.getWidth() * width / mContainer.getWidth();
            int cropHeight = mCropLayout.getHeight() * height / mContainer.getHeight();

            setX(x);
            setY(y);
            setCropWidth(cropWidth);
            setCropHeight(cropHeight);
            // 设置是否需要截图
            setNeedCapture(true);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            listener.onCaptureFail();
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public Handler getHandler() {
        return handler;
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    public boolean isNeedCapture() {
        return isNeedCapture;
    }

    public void setNeedCapture(boolean isNeedCapture) {
        this.isNeedCapture = isNeedCapture;
    }

}

package com.renyu.carclient.commons;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.renyu.carclient.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;

/**
 * Created by renyu on 15/10/17.
 */
public class CommonUtils {

    /**
     * 初始化相关文件路径
     */
    public static void loadDir() {
        String imgCacheDir = Environment.getExternalStorageDirectory()+File.separator+ParamUtils.IMAGECACHE;
        File file = new File(imgCacheDir);
        file.mkdirs();
    }

    /**
     * 初始化imageloader相关参数
     * @param context
     */
    public static void initImageLoader(Context context) {
        String imgCacheDir = Environment.getExternalStorageDirectory()+File.separator+ParamUtils.IMAGECACHE;
        File file = new File(imgCacheDir);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(400, 800)
                .diskCacheExtraOptions(400, 800, null)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY)
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * imageloader显示配置
     * @return
     */
    public static DisplayImageOptions getDefaultDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    /**
     * 获取首字母
     * @param str 文字字符串
     * @return
     */
    public static String getFirstPinyin(String str) {
        String result="";
        char[] nameChar=str.toCharArray();
        HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            //汉字取首字母
            if(nameChar[0]>128) {
                result=String.valueOf(PinyinHelper.toHanyuPinyinStringArray(nameChar[0])[0].charAt(0));
            }
            //大写字母转小写
            else if(nameChar[0]>=65&&nameChar[0]<=90) {
                result=String.valueOf(nameChar[0]).toLowerCase();
            }
            //小写字母直接返回
            else if(nameChar[0]>=97&&nameChar[0]<=122) {
                result=String.valueOf(nameChar[0]);
            }
            //异常字符用#表示
            else {
                result="#";
            }
        } catch(Exception e) {
            result="#";
        }
        return result;
    }

    /**
     * 微信分享
     * @param context
     * @param text
     * @param url
     * @param title
     * @param isFriend
     * @return
     */
    public String sendWeixin(Context context, String text, String url, String title, boolean isFriend) {
        IWXAPI api= WXAPIFactory.createWXAPI(context, ParamUtils.WEIXIN_SHAREID);
        int wxSdkVersion = api.getWXAppSupportAPI();
        if (wxSdkVersion >= 0x21020001) {
            api.registerApp(ParamUtils.WEIXIN_SHAREID);
            WXWebpageObject webpage=new WXWebpageObject();
            webpage.webpageUrl=url;
            WXMediaMessage msg=new WXMediaMessage(webpage);
            msg.title=title;
            msg.description=text;
            msg.thumbData=Bitmap2Bytes(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            SendMessageToWX.Req req=new SendMessageToWX.Req();
            req.transaction=buildTransaction("webpage");
            req.message=msg;
            req.scene=isFriend?SendMessageToWX.Req.WXSceneTimeline:SendMessageToWX.Req.WXSceneSession;
            api.sendReq(req);
            return "";
        } else {
            return "您当前使用的微信版本过低或未安装，分享失败";
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * Bitmap转换成byte[]
     * @param bm
     * @return
     */
    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * md5加密
     * @param s
     * @return
     */
    public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}

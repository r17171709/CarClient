package com.renyu.carclient.commons;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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
import com.renyu.carclient.model.AreaModel;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * Created by renyu on 15/10/17.
 */
public class CommonUtils {

    /**
     * 初始化相关文件路径
     */
    public static void loadDir() {
        String imgCacheDir = ParamUtils.IMAGECACHE;
        File file = new File(imgCacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        String dbDir=ParamUtils.DB;
        File file_db=new File(dbDir);
        if (!file_db.exists()) {
            file_db.mkdirs();
        }
    }

    /**
     * 初始化imageloader相关参数
     * @param context
     */
    public static void initImageLoader(Context context) {
        String imgCacheDir = ParamUtils.IMAGECACHE;
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

    /**
     * 得到屏幕高度
     * @return 单位:px
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 通过assets复制文件
     * @param oldName
     * @param newPath
     * @param context
     */
    public static void copyAssetsFile(String oldName, String newPath, Context context) {
        if (new File(newPath+File.separator+oldName).exists()) {
            return;
        }
        AssetManager manager=context.getAssets();
        try {
            int byteread=0;
            InputStream inStream=manager.open(oldName);
            FileOutputStream fs=new FileOutputStream(newPath+File.separator+oldName);
            byte[] buffer=new byte[1444];
            while ((byteread = inStream.read(buffer))!=-1) {
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取城市名称
     * @param id
     * @return
     */
    public static String getCityInfo(String id) {
        File file=new File(ParamUtils.DB+File.separator+"area.db");
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(file.getPath(), null);
        Cursor cs=db.rawQuery("select * from area_code where area_code.id="+id, null);
        cs.moveToFirst();
        String parentId="";
        for(int i=0;i<cs.getCount();i++) {
            cs.moveToPosition(i);
            parentId=cs.getString(1);
        }
        cs.close();
        db.close();
        return parentId;
    }


    /**
     * 获取省份信息
     */
    public static ArrayList<AreaModel> getProvice() {
        File file=new File(ParamUtils.DB+File.separator+"area.db");
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(file.getPath(), null);
        Cursor cs=db.rawQuery("select * from area_code where area_code.parentId=310000 OR area_code.parentId=110000 OR area_code.parentId=120000 OR area_code.parentId=500000 union all select * from area_code where area_code.parentId=1", null);
        cs.moveToFirst();
        ArrayList<AreaModel> models=new ArrayList<>();
        for(int i=0;i<cs.getCount();i++) {
            cs.moveToPosition(i);
            AreaModel model=new AreaModel();
            model.setId(cs.getString(0));
            model.setValue(cs.getString(1));
            model.setParentid(cs.getString(2));
            models.add(model);
        }
        cs.close();
        db.close();
        return models;
    }

    /**
     * 获取城区信息
     * @param id
     * @return
     */
    public static ArrayList<AreaModel> getCity(String id) {
        File file=new File(ParamUtils.DB+File.separator+"area.db");
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(file.getPath(), null);
        Cursor cs=db.rawQuery("select * from area_code where area_code.parentId="+id, null);
        cs.moveToFirst();
        ArrayList<AreaModel> models=new ArrayList<>();
        for(int i=0;i<cs.getCount();i++) {
            cs.moveToPosition(i);
            AreaModel model=new AreaModel();
            model.setId(cs.getString(0));
            model.setValue(cs.getString(1));
            model.setParentid(cs.getString(2));
            models.add(model);
        }
        cs.close();
        db.close();
        return models;
    }

    /**
     * 获取省份ID
     * @param id
     * @return
     */
    public static String getProvinceId(String id) {
        File file=new File(ParamUtils.DB+File.separator+"area.db");
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(file.getPath(), null);
        Cursor cs=db.rawQuery("select * from area_code where area_code.id="+id, null);
        cs.moveToFirst();
        String parentId="";
        for(int i=0;i<cs.getCount();i++) {
            cs.moveToPosition(i);
            parentId=cs.getString(2);
        }
        cs.close();
        db.close();
        return parentId;
    }

    /**
     * 拍照后刷新系统相册
     * @param context
     * @param newFile
     */
    public static void refreshAlbum(Context context, String newFile) {
        //刷新文件夹
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(context, new String[]{ParamUtils.IMAGECACHE}, null, null);
        }
        else {
            Intent scan_dir=new Intent(Intent.ACTION_MEDIA_MOUNTED);
            scan_dir.setData(Uri.fromFile(new File(ParamUtils.IMAGECACHE)));
            context.sendBroadcast(scan_dir);
        }
        //刷新文件
        Intent intent_scan=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent_scan.setData(Uri.fromFile(new File(newFile)));
        context.sendBroadcast(intent_scan);
    }

    /**
     * 获取压缩图片路径
     * @param filePath 文件路径名称
     * @return
     */
    public static String getScalePicturePathName(String filePath) {
        try {
            BitmapFactory.Options opts=new BitmapFactory.Options();
            //此时返回bitmap为空
            opts.inJustDecodeBounds=true;
            BitmapFactory.decodeFile(filePath, opts);
            int srcWidth=opts.outWidth;
            int srcHeight=opts.outHeight;
            //图片格式不正确
            if(srcWidth<400||srcHeight<400) {
                return "";
            }
            //如果原始图片的宽高
            if(srcWidth<1000||srcHeight<1000) {
                return filePath;
            }
            // 缩放比例
            double ratio=2;
            // 设置输出宽度、高度
            BitmapFactory.Options newOpts=new BitmapFactory.Options();
            newOpts.inSampleSize=(int) (ratio);
            newOpts.inJustDecodeBounds=false;
            // 减少对Aphla通道
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
            newOpts.outWidth=(int) (srcWidth/ratio);
            newOpts.outHeight=(int) (srcHeight/ratio);
            Matrix matrix=new Matrix();
            matrix.postRotate(readPictureDegree(filePath));
            // 创建新的图片
            Bitmap bitmap=BitmapFactory.decodeFile(filePath, newOpts);
            bitmap=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            //生成新图片
            String dirPath=ParamUtils.IMAGECACHE;
            File cameraFile=new File(dirPath+"/"+System.currentTimeMillis()+".jpg");
            FileOutputStream fos=null;
            if (!cameraFile.exists()) {
                cameraFile.createNewFile();
            }
            fos=new FileOutputStream(cameraFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            bitmap.recycle();
            bitmap=null;
            return cameraFile.getPath();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return filePath;
        }
    }

    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface=new ExifInterface(path);
            int orientation=exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree=90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree=180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /**
     * 获取相册图片路径
     * @param uri
     * @param context
     * @return
     */
    public static String getPath(final Uri uri, final Context context) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}

package com.android.graphictools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by hp on 2018/5/19.
 */

public class GraphicUtils {
    private static final String TAG = "GraphicTools::GraphicUtils";
    private String mSrcFileDirectory = "/Pictures/";
    private String mDstFileDirectory = mSrcFileDirectory + "Dump/";
    private long mDecodeIntervalTime = 0;
    private long mEncodeIntervalTime = 0;

    public long getCostTime(boolean decodeTime) {
        return decodeTime? mDecodeIntervalTime:mEncodeIntervalTime;
    }
    private long getCurrentTime() {
        Date d = new Date();
        return d.getTime();
    }
    public boolean writeBitmapToFile(Bitmap bmp, String dstFilePath) {
        if (null == dstFilePath) return false;
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int rowBytes = bmp.getRowBytes();
        int[] pixels = new int[rowBytes * h];
        bmp.getPixels(pixels, 0, rowBytes, 0, 0, w, h);

        try {
            FileOutputStream fos = new FileOutputStream(dstFilePath);
            fos.write(pixels.toString().getBytes());
            fos.flush();
            fos.close();
            Log.d(TAG, "Write to " + dstFilePath + " success!");
        } catch (IOException e) {
            Log.d(TAG, "Write to " + dstFilePath + " fail!");
            return false;
        }
        return true;
    }
    public boolean writeByteArrayOutputStreamToFile(String dstPath, ByteArrayOutputStream baos) {
        if (null == dstPath) return false;
        try {
            FileOutputStream fos = new FileOutputStream(dstPath);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            Log.d(TAG, "Write to " + dstPath + " success!");
        } catch (IOException e) {
            Log.d(TAG, "Write to " + dstPath + " fail!");
            return false;
        }
        return true;
    }
    public String deleteDirectory() {
        String sdAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dstDirPath = sdAbsolutePath + mDstFileDirectory;
        if (null == dstDirPath) return null;
        File dir = new File(dstDirPath);
        if (!dir.exists()) return null;
        if (dir.isDirectory()) {
            File file[] = dir.listFiles();
            for (int i = 0; i < file.length; i++) {
                file[i].delete();
            }
        }
        dir.delete();
        return dstDirPath;
    }
    public String generateBitmapFileName(Bitmap bmp) {
        if (null == bmp) return null;
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int rowBytes = bmp.getRowBytes();
        return Integer.toString(w) + "_"
                + Integer.toString(h) + "_"
                + Integer.toString(rowBytes)
                + ".bin";
    }
    public String getCompressDstFileName(Bitmap.CompressFormat format) {
        String name = null;
        if (format == Bitmap.CompressFormat.JPEG) {
            name = "out.jpg";
        } else if (format == Bitmap.CompressFormat.PNG) {
            name = "out.png";
        } else if (format == Bitmap.CompressFormat.WEBP) {
            name = "out.webp";
        } else {
            name = "out.jpg";
        }
        return name;
    }
    public String getSrcFilePath(String fileName) {
        if (null == fileName) return null;
        String sdAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String srcDirPath = sdAbsolutePath + mSrcFileDirectory;
        return srcDirPath + fileName;
    }
    public String getDstFilePath(String fileName) {
        if (null == fileName) return null;
        String sdAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dstDirPath = sdAbsolutePath + mDstFileDirectory;
        File dir = new File(dstDirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dstDirPath + fileName;
    }
    public int getSampleSize(String sampleSize) {
        int s = 1;
        if (null == sampleSize) return s;
        if (sampleSize.equals("1")) {
            s = 1;
        } else if (sampleSize.equals("2")) {
            s = 2;
        } else if (sampleSize.equals("4")) {
            s = 4;
        } else if (sampleSize.equals("8")) {
            s = 8;
        } else {
            s = 1;
        }
        return s;
    }
    public Bitmap.Config getPreColorType(String preColorType) {
        Bitmap.Config pct = Bitmap.Config.ARGB_8888;
        if (null == preColorType) return pct;

        if (preColorType.equals("ALPHA_8")) {
            pct = Bitmap.Config.ALPHA_8;
        } else if (preColorType.equals("RGB_565")) {
            pct = Bitmap.Config.RGB_565;
        } else if (preColorType.equals("ARGB_4444")) {
            pct = Bitmap.Config.ARGB_4444;
        } else if (preColorType.equals("ARGB_8888")) {
            pct = Bitmap.Config.ARGB_8888;
        } else if (preColorType.equals("RGBA_F16")) {
            pct = Bitmap.Config.ARGB_8888;
        } else if (preColorType.equals("HARDWARE")) {
            pct = Bitmap.Config.ARGB_8888;
        } else if (preColorType.equals("YUV420SP")) {
            pct = Bitmap.Config.ARGB_8888;
        } else {
            pct = Bitmap.Config.ARGB_8888;
        }
        return pct;
    }
    public boolean getScale(String scale) {
        boolean s = false;
        if (null == scale) return s;
        if (scale.equals("true")) {
            s = true;
        } else if (scale.equals("false")) {
            s = false;
        } else {
            s = false;
        }
        return s;
    }
    public boolean getShowBitmap(String showBitmap) {
        boolean s = false;
        if (null == showBitmap) return s;
        if (showBitmap.equals("true")) {
            s = true;
        } else if (showBitmap.equals("false")) {
            s = false;
        } else {
            s = false;
        }
        return s;
    }
    public Bitmap.CompressFormat getOutFormat(String outFormat) {
        if (null == outFormat) return Bitmap.CompressFormat.JPEG;
        Bitmap.CompressFormat f = Bitmap.CompressFormat.JPEG;
        if (outFormat.equals("JPEG")) {
            f = Bitmap.CompressFormat.JPEG;
        } else if (outFormat.equals("PNG")) {
            f = Bitmap.CompressFormat.PNG;
        } else if (outFormat.equals("WEBP")) {
            f = Bitmap.CompressFormat.WEBP;
        } else {
            f = Bitmap.CompressFormat.JPEG;
        }
        return f;
    }
    public Bitmap decodeFile(String srcFilePath, int sampleSize,
                             Bitmap.Config preColorType, boolean scale) {
        if (null == srcFilePath) return null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inSampleSize = sampleSize;
        opt.inPreferredConfig = preColorType;
        opt.inScaled = scale;
        long startDecodeTime = getCurrentTime();
        Bitmap bmp = BitmapFactory.decodeFile(srcFilePath, opt);
        mDecodeIntervalTime = getCurrentTime() - startDecodeTime;
        return bmp;
    }
    public boolean compressBitmap(Bitmap bmp, Bitmap.CompressFormat format,
                                  int quality, ByteArrayOutputStream baos) {
        if (null == bmp) return false;
        long startTime = getCurrentTime();
        boolean ret = bmp.compress(format, quality, baos);
        mEncodeIntervalTime = getCurrentTime() - startTime;
        return ret;
    }
    public boolean mkdirForHwcDump(String path) {
        boolean ret = true;
        if (null == path) return false;
        File dir = new File(path);
        if (!dir.exists()) {
            ret = dir.mkdir();
        }
        return ret;
    }
    public void dumpsys(String service) {
        return;
    }
}

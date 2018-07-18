package practice.lxn.cn.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

/**
 * 描述：图片加载框架
 */
public class ImageLoader {
    private static final long DISK_CACH_SIZE = 1024 * 1024 * 50;
    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDiskCache;
    private boolean mIsDiskLruCacheCreated = false;
    private ImageLoader(Context context) {
        int memory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = memory / 8;
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        File diskCacheDir = getDiskCacheDir(context, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (getAvalableSpace(diskCacheDir) > DISK_CACH_SIZE) {
            try {
                mDiskCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACH_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        String cachePath;
        if (externalAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath +  File.separator + uniqueName);
    }

    private long getAvalableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        StatFs statFs = new StatFs(path.getPath());
        return statFs.getBlockSize() * statFs.getAvailableBlocks();
    }
}

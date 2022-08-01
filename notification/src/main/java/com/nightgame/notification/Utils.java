package com.nightgame.notification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.IconCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class Utils
{
    private static String _Tag = "Notification";

    public static void log(String msg)
    {
        Log.d(_Tag, msg);
    }

    public static void warring(String msg)
    {
        Log.w(_Tag, msg);
    }

    public static void error(String msg)
    {
        Log.e(_Tag, msg);
    }

    /**
     * Bitmap转Icon
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static Icon getIconByBitmap(Bitmap bitmap)
    {

        return Icon.createWithBitmap(bitmap);
    }

    /**
     * Bitmap转IconCompat
     */
    public static IconCompat getIconCompatByBitmap(Bitmap bitmap)
    {
        return IconCompat.createWithBitmap(bitmap);
    }

    /**
     * 下载图片
     */
    public static void downloadImg(Context context, String url, NotifyItem item)
    {
        Glide.with(context).asBitmap().load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Bitmap>()
        {
            @Override
            public void onResourceReady(@NonNull Bitmap res, @Nullable Transition<? super Bitmap> transition)
            {
                if (item.getDownload() != null)
                {
                    item.getDownload().downloadSucc(url, res);
                }
            }
            @Override
            public void onLoadCleared(@Nullable Drawable placeholder)
            {
                if (item.getDownload() != null)
                {
                    item.getDownload().downloadCleared(url);
                }
            }

            @Override
            public void onLoadFailed(Drawable errorDrawable)
            {
                if (item.getDownload() != null)
                {
                    item.getDownload().downloadFail(url);
                }
            }
        });
    }

}

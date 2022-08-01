package com.nightgame.notification.Interface;

import android.graphics.Bitmap;

public interface IDownload
{
    void downloadSucc(String path, Bitmap res);

    void downloadFail(String path);

    void downloadCleared(String path);
}

package com.nightgame.notification;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import com.nightgame.notification.Interface.IDownload;

import java.util.concurrent.atomic.AtomicInteger;

public class NotificationBase
{
    public static Notification instance;

    protected static Activity _MainAct;
    private final static AtomicInteger _Integer = new AtomicInteger(0);
    private final static Uri _SoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    private static String _ChannelId = "Channel";


    public static Notification init(Activity act)
    {
        if (act == null)
        {
            Utils.error("Activity is null");
            return null;
        }
        instance = new Notification();
        _MainAct = act;
        return instance;
    }

    // region 纯图片消息

    /**
     * 纯图片消息
     */
    protected void startDownloadImg(NotifyItem item)
    {
        item.setDownload(new IDownload()
        {
            @Override
            public void downloadSucc(String path, Bitmap res)
            {
                //继续下载SmallIcon
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && item.getSmallType() == ImgType.Http)
                {
                    item.setDownload(new IDownload()
                    {
                        @Override
                        public void downloadSucc(String path, Bitmap smallRes)
                        {
                            showImg(res, smallRes, item);
                        }

                        @Override
                        public void downloadFail(String path)
                        {
                            Utils.error("downloadFail : " + path);
                        }

                        @Override
                        public void downloadCleared(String path)
                        {
                            Utils.error("downloadCleared : " + path);

                        }
                    });
                    Utils.downloadImg(_MainAct, item.getSmallUrl(), item);
                }
                else
                {
                    showImg(res, item);
                }
            }

            @Override
            public void downloadFail(String path)
            {
                Utils.error("downloadFail : " + path);
            }

            @Override
            public void downloadCleared(String path)
            {
                Utils.error("downloadCleared : " + path);
            }
        });

        Utils.downloadImg(_MainAct, item.getBgUrl(), item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showImg(Bitmap backImg, Bitmap smallImg, NotifyItem item)
    {
        NotificationCompat.Builder notificationBuilder = getImgNotification(backImg, smallImg);

        NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(item.getEnableVibration());//震动
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId(), notificationBuilder.build());
    }

    private void showImg(Bitmap backImg, NotifyItem item)
    {
        NotificationCompat.Builder notificationBuilder = getImgNotification(backImg, item);

        NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(item.getEnableVibration());//震动
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId(), notificationBuilder.build());
    }

    protected void showImg(NotifyItem item)
    {
        NotificationCompat.Builder notificationBuilder = getImgNotification(item);

        NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(item.getEnableVibration());//震动
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId(), notificationBuilder.build());
    }


    private NotificationCompat.Builder getImgNotification(Bitmap backImg, NotifyItem item)
    {

        PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

        notificationBuilder.setSmallIcon(item.getSmallIcon());
        notificationBuilder.setContent(getRemoteViews(backImg));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(_SoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.build();

        return notificationBuilder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private NotificationCompat.Builder getImgNotification(Bitmap backImg, Bitmap smallImg)
    {

        PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

        notificationBuilder.setSmallIcon(Utils.getIconCompatByBitmap(smallImg));
        notificationBuilder.setContent(getRemoteViews(backImg));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(_SoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.build();

        return notificationBuilder;
    }

    private NotificationCompat.Builder getImgNotification(NotifyItem item)
    {

        PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

        notificationBuilder.setSmallIcon(item.getSmallIcon());
        notificationBuilder.setContent(getRemoteViews(item.getBg()));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(_SoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.build();

        return notificationBuilder;
    }


    private RemoteViews getRemoteViews(Bitmap bitmap)
    {
        int layoutId = R.layout.layout_sdk_notify;
        RemoteViews remoteViews = new RemoteViews(_MainAct.getPackageName(), layoutId);
        remoteViews.setViewVisibility(R.id.notify_title, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.notify_content, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.notify_appName, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.notify_appNameIcon, View.INVISIBLE);
        remoteViews.setImageViewBitmap(R.id.notify_background, bitmap);

        return remoteViews;
    }

    private RemoteViews getRemoteViews(int bg)
    {
        int layoutId = R.layout.layout_sdk_notify;
        RemoteViews remoteViews = new RemoteViews(_MainAct.getPackageName(), layoutId);
        remoteViews.setViewVisibility(R.id.notify_title, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.notify_content, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.notify_appName, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.notify_appNameIcon, View.INVISIBLE);
        remoteViews.setImageViewResource(R.id.notify_background, bg);

        return remoteViews;
    }

    //endregion

    //region 图文消息

    protected void startDownloadImgAndTxt(NotifyItem item)
    {
        item.setDownload(new IDownload()
        {
            @Override
            public void downloadSucc(String path, Bitmap res)
            {
                //继续下载SmallIcon
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && item.getSmallType() == ImgType.Http)
                {
                    item.setDownload(new IDownload()
                    {
                        @Override
                        public void downloadSucc(String path, Bitmap smallRes)
                        {
                            //继续下载Icon
                            if (item.getIconType() == ImgType.Http)
                            {
                                item.setDownload(new IDownload()
                                {
                                    @Override
                                    public void downloadSucc(String path, Bitmap iconRes)
                                    {
                                        showImgIconAndTxt(res, smallRes, iconRes, item);
                                    }

                                    @Override
                                    public void downloadFail(String path)
                                    {
                                        Utils.error("downloadFail : " + path);
                                    }

                                    @Override
                                    public void downloadCleared(String path)
                                    {
                                        Utils.error("downloadCleared : " + path);
                                    }
                                });
                                Utils.downloadImg(_MainAct, item.getIconUrl(), item);
                            }
                            else
                            {
                                showImgAndTxt(res, smallRes, item);
                            }
                        }

                        @Override
                        public void downloadFail(String path)
                        {
                            Utils.error("downloadFail : " + path);
                        }

                        @Override
                        public void downloadCleared(String path)
                        {
                            Utils.error("downloadCleared : " + path);
                        }
                    });
                    Utils.downloadImg(_MainAct, item.getSmallUrl(), item);
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && item.getIconType() == ImgType.Http)
                    {
                        item.setDownload(new IDownload()
                        {
                            @Override
                            public void downloadSucc(String path, Bitmap iconRes)
                            {
                                showImgIconAndTxt(res, iconRes, item);
                            }

                            @Override
                            public void downloadFail(String path)
                            {
                                Utils.error("downloadFail : " + path);
                            }

                            @Override
                            public void downloadCleared(String path)
                            {
                                Utils.error("downloadCleared : " + path);
                            }
                        });
                        Utils.downloadImg(_MainAct, item.getIconUrl(), item);
                    }
                    else
                    {
                        showImgAndTxt(res, item);
                    }

                }
            }

            @Override
            public void downloadFail(String path)
            {
                Utils.error("downloadFail : " + path);
            }

            @Override
            public void downloadCleared(String path)
            {
                Utils.error("downloadCleared : " + path);
            }
        });

        Utils.downloadImg(_MainAct, item.getBgUrl(), item);
    }

    private void showImgAndTxt(Bitmap backImg, NotifyItem item)
    {
        NotificationCompat.Builder notificationBuilder = getImgAndTxtNotification(backImg, item);

        NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(item.getEnableVibration());//震动
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId(), notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showImgAndTxt(Bitmap backImg, Bitmap smallImg, NotifyItem item)
    {
        NotificationCompat.Builder notificationBuilder = getImgAndTxtNotification(backImg, smallImg, item);

        NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(item.getEnableVibration());//震动
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId(), notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showImgIconAndTxt(Bitmap backImg, Bitmap smallImg, Bitmap iconImg, NotifyItem item)
    {
        NotificationCompat.Builder notificationBuilder = getImgIconAndTxtNotification(backImg, smallImg, iconImg, item);

        NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(item.getEnableVibration());//震动
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId(), notificationBuilder.build());
    }

    private void showImgIconAndTxt(Bitmap backImg, Bitmap iconImg, NotifyItem item)
    {
        NotificationCompat.Builder notificationBuilder = getImgIconAndTxtNotification(backImg, iconImg, item);

        NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(item.getEnableVibration());//震动
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId(), notificationBuilder.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private NotificationCompat.Builder getImgAndTxtNotification(Bitmap backImg, Bitmap smallImg, NotifyItem item)
    {
        PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

        notificationBuilder.setSmallIcon(Utils.getIconCompatByBitmap(smallImg));
        notificationBuilder.setContent(getImgAndTxtRemoteViews(backImg, item));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(_SoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.build();

        return notificationBuilder;
    }


    private NotificationCompat.Builder getImgAndTxtNotification(Bitmap backImg, NotifyItem item)
    {

        PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

        notificationBuilder.setSmallIcon(item.getSmallIcon());
        notificationBuilder.setContent(getImgAndTxtRemoteViews(backImg, item));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(_SoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.build();

        return notificationBuilder;
    }

    private NotificationCompat.Builder getImgIconAndTxtNotification(Bitmap backImg, Bitmap icon, NotifyItem item)
    {

        PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

        notificationBuilder.setSmallIcon(item.getSmallIcon());
        notificationBuilder.setContent(getImgAndTxtRemoteViews(backImg, icon, item));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(_SoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.build();

        return notificationBuilder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private NotificationCompat.Builder getImgIconAndTxtNotification(Bitmap backImg, Bitmap smallImg, Bitmap icon, NotifyItem item)
    {
        PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

        notificationBuilder.setSmallIcon(Utils.getIconCompatByBitmap(smallImg));
        notificationBuilder.setContent(getImgAndTxtRemoteViews(backImg, icon, item));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(_SoundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.build();

        return notificationBuilder;
    }

    /**
     * 创建图片跟文字界面
     */
    private RemoteViews getImgAndTxtRemoteViews(Bitmap bitmap, NotifyItem item)
    {
        int layoutId = R.layout.layout_sdk_notify;
        RemoteViews remoteViews = new RemoteViews(_MainAct.getPackageName(), layoutId);
        remoteViews.setImageViewBitmap(R.id.notify_background, bitmap);
        remoteViews.setTextViewText(R.id.notify_title, item.getTitle());
        remoteViews.setTextViewText(R.id.notify_content, item.getContent());
        remoteViews.setImageViewResource(R.id.notify_appNameIcon, item.getIcon());
        remoteViews.setTextViewText(R.id.notify_appName, item.getAppName());
        return remoteViews;
    }

    /**
     * 创建图片跟文字界面
     */
    private RemoteViews getImgAndTxtRemoteViews(Bitmap bitmap, Bitmap icon, NotifyItem item)
    {
        int layoutId = R.layout.layout_sdk_notify;
        RemoteViews remoteViews = new RemoteViews(_MainAct.getPackageName(), layoutId);
        remoteViews.setImageViewBitmap(R.id.notify_background, bitmap);
        remoteViews.setTextViewText(R.id.notify_title, item.getTitle());
        remoteViews.setTextViewText(R.id.notify_content, item.getContent());
        remoteViews.setImageViewBitmap(R.id.notify_appNameIcon, icon);
        remoteViews.setTextViewText(R.id.notify_appName, item.getAppName());
        return remoteViews;
    }

    //endregion

    //region 纯文字消息

    protected void txtNotification(NotifyItem item)
    {
        PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

        notificationBuilder.setSmallIcon(item.getSmallIcon());
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setContentTitle(item.getTitle());
        notificationBuilder.setContentText(item.getContent());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(_SoundUri);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.build();

        NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(item.getEnableVibration());//震动
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(getId(), notificationBuilder.build());
    }

    protected void txtNotification(NotifyItem item, String smallIcon)
    {
        item.setDownload(new IDownload()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void downloadSucc(String path, Bitmap res)
            {
                PendingIntent pendingIntent = PendingIntent.getActivity(_MainAct, getId()/* Request code */, _MainAct.getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_MainAct, _ChannelId);

                notificationBuilder.setSmallIcon(Utils.getIconCompatByBitmap(res));
                notificationBuilder.setContentIntent(pendingIntent);
                notificationBuilder.setContentTitle(item.getTitle());
                notificationBuilder.setContentText(item.getContent());
                notificationBuilder.setAutoCancel(true);
                notificationBuilder.setSound(_SoundUri);
                notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                notificationBuilder.build();

                NotificationManager notificationManager = (NotificationManager) _MainAct.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    NotificationChannel channel = new NotificationChannel(_ChannelId, "Channel title", NotificationManager.IMPORTANCE_DEFAULT);
                    channel.enableVibration(item.getEnableVibration());//震动
                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(getId(), notificationBuilder.build());
            }

            @Override
            public void downloadFail(String path)
            {
                Utils.error("downloadFail : " + path);
            }

            @Override
            public void downloadCleared(String path)
            {
                Utils.error("downloadCleared : " + path);
            }
        });
        Utils.downloadImg(_MainAct, smallIcon, item);
    }

    //endregion


    private int getId()
    {
        return _Integer.incrementAndGet();
    }
}

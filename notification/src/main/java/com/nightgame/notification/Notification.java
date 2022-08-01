package com.nightgame.notification;

import android.text.TextUtils;

import com.nightgame.notification.Interface.INotification;

public class Notification extends NotificationBase implements INotification
{

    //region 纯图片推送

    /**
     * 纯图片推送
     */
    public void pushImage(final String url, final String smallIcon)
    {
        pushImage(url, smallIcon, false);
    }

    /**
     * 纯图片推送
     */
    public void pushImage(final String url, final String smallIcon, final boolean enableVibration)
    {
        if (TextUtils.isEmpty(url))
        {
            Utils.error("url is null");
            return;
        }

        if (TextUtils.isEmpty(smallIcon))
        {
            Utils.error("smallIcon is null");
            return;
        }

        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.Image);
        item.setBgUrl(url);
        item.setEnableVibration(enableVibration);

        startDownloadImg(item);
    }

    /**
     * 纯图片推送
     */
    public void pushImage(final String url, final int smallIcon)
    {
        pushImage(url, smallIcon, false);
    }

    /**
     * 纯图片推送
     */
    public void pushImage(final String url, final int smallIcon, final boolean enableVibration)
    {
        if (TextUtils.isEmpty(url))
        {
            Utils.error("url is null");
            return;
        }

        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.Image);
        item.setBgUrl(url);
        item.setEnableVibration(enableVibration);

        startDownloadImg(item);
    }

    /**
     * 纯图片推送
     */
    public void pushImage(final int bg, final int smallIcon)
    {
        pushImage(bg, smallIcon, false);
    }

    /**
     * 纯图片推送
     */
    public void pushImage(final int bg, final int smallIcon, final boolean enableVibration)
    {
        if (bg == 0)
        {
            Utils.error("bg is null");
            return;
        }

        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.Image);
        item.setBg(bg);
        item.setEnableVibration(enableVibration);

        showImg(item);
    }

    //endregion

    //region 纯文字推送
    /**
     * 纯文字推送
     */
    public void pushText(final String title, final String content, final String smallIcon)
    {
        pushText(title, content, smallIcon, false);
    }

    /**
     * 纯文字推送
     */
    public void pushText(final String title, final String content, final String smallIcon, boolean enableVibration)
    {
        if (TextUtils.isEmpty(title))
        {
            Utils.error("title is null");
            return;
        }
        if (TextUtils.isEmpty(content))
        {
            Utils.error("content is null");
            return;
        }

        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.Text);
        item.setTitle(title);
        item.setContent(content);
        item.setEnableVibration(enableVibration);

        txtNotification(item, smallIcon);
    }

    /**
     * 纯文字推送
     */
    public void pushText(final String title, final String content, final int smallIcon)
    {
        pushText(title, content, smallIcon, false);
    }

    /**
     * 纯文字推送
     */
    public void pushText(final String title, final String content, final int smallIcon, boolean enableVibration)
    {
        if (TextUtils.isEmpty(title))
        {
            Utils.error("title is null");
            return;
        }
        if (TextUtils.isEmpty(content))
        {
            Utils.error("content is null");
            return;
        }

        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.Text);
        item.setTitle(title);
        item.setContent(content);
        item.setEnableVibration(enableVibration);

        txtNotification(item);
    }
    //endregion

    //region 图文推送

    /**
     * 图文推送
     */
    public void pushImgAndTxt(final String appName, final String url, final String title, final String content, final int icon, final int smallIcon)
    {
        pushImgAndTxt(appName, url, title, content, icon, smallIcon, false);
    }

    /**
     * 图文推送
     */
    public void pushImgAndTxt(final String appName, final String url, final String title, final String content, final int icon, final int smallIcon, final boolean enableVibration)
    {
        if (TextUtils.isEmpty(url))
        {
            Utils.error("url is null");
            return;
        }
        if (TextUtils.isEmpty(title))
        {
            Utils.error("title is null");
            return;
        }
        if (TextUtils.isEmpty(content))
        {
            Utils.error("content is null");
            return;
        }
        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.ImageAndText);
        item.setBgUrl(url);
        item.setTitle(title);
        item.setContent(content);
        item.setIcon(icon);
        item.setAppName(appName);
        item.setEnableVibration(enableVibration);

        startDownloadImgAndTxt(item);
    }

    /**
     * 图文推送
     */
    public void pushImgAndTxt(final String appName,final String url, final String title, final String content, final int icon, final String smallIcon)
    {
        pushImgAndTxt(appName, url, title, content, icon, smallIcon, false);
    }

    /**
     * 图文推送
     */
    public void pushImgAndTxt(final String appName,final String url, final String title, final String content, final int icon, final String smallIcon, final boolean enableVibration)
    {
        if (TextUtils.isEmpty(url))
        {
            Utils.error("url is null");
            return;
        }
        if (TextUtils.isEmpty(title))
        {
            Utils.error("title is null");
            return;
        }
        if (TextUtils.isEmpty(content))
        {
            Utils.error("content is null");
            return;
        }
        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.ImageAndText);
        item.setBgUrl(url);
        item.setTitle(title);
        item.setContent(content);
        item.setIcon(icon);
        item.setAppName(appName);
        item.setEnableVibration(enableVibration);

        startDownloadImgAndTxt(item);
    }

    /**
     * 图文推送
     */
    public void pushImgAndTxt(final String appName,final String url, final String title, final String content, final String icon, final int smallIcon)
    {
        pushImgAndTxt(appName, url, title, content, icon, smallIcon, false);
    }

    /**
     * 图文推送
     */
    public void pushImgAndTxt(final String appName,final String url, final String title, final String content, final String icon, final int smallIcon, final boolean enableVibration)
    {
        if (TextUtils.isEmpty(url))
        {
            Utils.error("url is null");
            return;
        }
        if (TextUtils.isEmpty(title))
        {
            Utils.error("title is null");
            return;
        }
        if (TextUtils.isEmpty(content))
        {
            Utils.error("content is null");
            return;
        }
        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.ImageAndText);
        item.setBgUrl(url);
        item.setTitle(title);
        item.setContent(content);
        item.setIconUrl(icon);
        item.setAppName(appName);
        item.setEnableVibration(enableVibration);

        startDownloadImgAndTxt(item);
    }

    /**
     * 图文推送
     */
    public void pushImgAndTxt(final String appName, final String url, final String title, final String content, final String icon, final String smallIcon)
    {
        pushImgAndTxt(appName, url, title, content, icon, smallIcon, false);
    }

    /**
     * 图文推送
     */
    public void pushImgAndTxt(final String appName, final String url, final String title, final String content, final String icon, final String smallIcon, final boolean enableVibration)
    {
        if (TextUtils.isEmpty(url))
        {
            Utils.error("url is null");
            return;
        }
        if (TextUtils.isEmpty(title))
        {
            Utils.error("title is null");
            return;
        }
        if (TextUtils.isEmpty(content))
        {
            Utils.error("content is null");
            return;
        }
        NotifyItem item = new NotifyItem(_MainAct, smallIcon);
        item.setType(NotifyType.ImageAndText);
        item.setBgUrl(url);
        item.setTitle(title);
        item.setContent(content);
        item.setIconUrl(icon);
        item.setAppName(appName);
        item.setEnableVibration(enableVibration);

        startDownloadImgAndTxt(item);
    }




    //endregion
}
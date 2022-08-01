package com.nightgame.notification.Interface;

public interface INotification
{
    void pushImage(final String url, int smallIcon);

    void pushImage(final String url, int smallIcon, boolean enableVibration);

    void pushText(final String title, final String content, final int smallIcon);

    void pushText(final String title, final String content, final int smallIcon, boolean enableVibration);

    void pushImgAndTxt(final String appName, final String url, final String title, final String content, final int icon, final int smallIcon);

    void pushImgAndTxt(final String appName, final String url, final String title, final String content, final int icon, final int smallIcon, boolean enableVibration);
}

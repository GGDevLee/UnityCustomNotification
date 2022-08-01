package com.nightgame.notification;

import android.content.Context;
import android.media.Image;

import com.nightgame.notification.Interface.IDownload;

public class NotifyItem
{
    private Context _Context = null;
    private String _Id;
    private String _Title;
    private String _Content;
    private String _AppName = null;
    private int _SmallIcon = -1;
    private String _SmallIconUrl = null;
    private int _Icon = -1;
    private String _IconUrl = null;
    private int _Bg = 0;
    private String _BgUrl = null;

    private ImgType _BgType = ImgType.Http;
    private ImgType _SmallType = ImgType.Http;
    private ImgType _IconType = ImgType.Http;

    private NotifyType _Type = NotifyType.Text;
    private IDownload _IDownload = null;
    private boolean _EnableVibration = false;


    public void setType(NotifyType type)
    {
        _Type = type;
    }

    public NotifyType getType()
    {
        return _Type;
    }


    public void setId(String id)
    {
        _Id = id;
    }
    public String getId()
    {
        return _Id;
    }


    public void setTitle(String title)
    {
        _Title = title;
    }
    public String getTitle()
    {
        return _Title;
    }


    public void setContent(String content)
    {
        _Content = content;
    }
    public String getContent()
    {
        return _Content;
    }


    public void setSmallIcon(int icon)
    {
        _SmallType = ImgType.Res;
        _SmallIcon = icon;
    }
    public int getSmallIcon()
    {
        return _SmallIcon;
    }
    public void setSmallUrl(String small)
    {
        _SmallType = ImgType.Http;
        _SmallIconUrl = small;
    }
    public String getSmallUrl()
    {
        return _SmallIconUrl;
    }


    public void setIcon(int icon)
    {
        _IconType = ImgType.Res;
        _Icon = icon;
    }
    public int getIcon()
    {
        return _Icon;
    }
    public void setIconUrl(String iconUrl)
    {
        _IconType = ImgType.Http;
        _IconUrl = iconUrl;
    }
    public String getIconUrl()
    {
        return _IconUrl;
    }


    public void setBg(int bg)
    {
        _BgType = ImgType.Res;
        _Bg = bg;
    }
    public int getBg()
    {
        return _Bg;
    }
    public void setBgUrl(String bg)
    {
        _BgType = ImgType.Http;
        _BgUrl = bg;
    }
    public String getBgUrl()
    {
        return _BgUrl;
    }



    public void setAppName(String name)
    {
        _AppName = name;
    }

    public String getAppName()
    {
        return _AppName;
    }



    public ImgType getBgType()
    {
        return _BgType;
    }

    public ImgType getSmallType()
    {
        return _SmallType;
    }

    public ImgType getIconType()
    {
        return _IconType;
    }


    public void setDownload(IDownload download)
    {
        _IDownload = download;
    }

    public IDownload getDownload()
    {
        return _IDownload;
    }

    public void setEnableVibration(boolean state)
    {
        _EnableVibration = state;
    }

    public boolean getEnableVibration()
    {
        return _EnableVibration;
    }

    public Context getContext()
    {
        return _Context;
    }

    public NotifyItem(Context context, int smallIcon)
    {
        _Context = context;
        setSmallIcon(smallIcon);
    }

    public NotifyItem(Context context, String smallIcon)
    {
        _Context = context;
        setSmallUrl(smallIcon);
    }
}

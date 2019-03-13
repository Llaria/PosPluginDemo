package sun.sundy.posplugindemo;

import android.app.Application;

import com.qihoo360.replugin.RePluginApplication;

public class PluginApplication extends RePluginApplication{

    public static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}

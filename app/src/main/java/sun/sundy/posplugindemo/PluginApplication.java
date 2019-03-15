package sun.sundy.posplugindemo;

import android.app.Application;
import android.content.Context;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginConfig;

public class PluginApplication extends RePluginApplication{

    public static Application app;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //-------------- 开发的时候不验证签名 -----------
//        RePlugin.App.attachBaseContext(this,
//                new RePluginConfig()
//                        .setVerifySign(false)
//                        .setPrintDetailLog(true)
//                        .setUseHostClassIfNotFound(true)
//                        .setMoveFileWhenInstalling(false));
        // FIXME 允许接收rpRunPlugin等Gradle Task，发布时请务必关掉，以免出现问题
        RePlugin.enableDebugger(base, BuildConfig.DEBUG);
    }

    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig c = new RePluginConfig();

        // 允许“插件使用宿主类”。默认为“关闭”
        c.setUseHostClassIfNotFound(true);

        // FIXME RePlugin默认会对安装的外置插件进行签名校验，这里先关掉，避免调试时出现签名错误
        c.setVerifySign(!BuildConfig.DEBUG);

        // 针对“安装失败”等情况来做进一步的事件处理
//        c.setEventCallbacks(new HostEventCallbacks(this));

        // FIXME 若宿主为Release，则此处应加上您认为"合法"的插件的签名，例如，可以写上"宿主"自己的。
        // RePlugin.addCertSignature("AAAAAAAAA");

        // 在Art上，优化第一次loadDex的速度
        // c.setOptimizeArtLoadDex(true);
        return c;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        RePlugin.App.onCreate();
        RePlugin.enableDebugger(this,true);
    }
}

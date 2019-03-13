package sun.sundy.posplugindemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void open(View view) {
        Intent intent = RePlugin.createIntent("sun.sundy.pospluginone",
                "sun.sundy.pospluginone.PluginOneActivity");
        if (!RePlugin.startActivity(this, intent)){
            ToastUtils.showLazzToast("启动失败！");
        }
    }

    public void install(View view) {
        String pluginApk = "pluginone.apk";
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + pluginApk;
        System.out.println("======fileName:" + fileName);
        File pluginFile = new File(fileName);
        //文件不存在就返回
        if (!pluginFile.exists()){
            System.out.println("=====不存在");
            return;
        }
        PluginInfo info = null;
        if (pluginFile.exists()) {
            info = RePlugin.install(fileName);
        }
        if (info != null){
            //预先加载
            RePlugin.preload(info);
            ToastUtils.showLazzToast("安装 pluginone 成功 " + info.getName());
        }
    }

    public void unInstall(View view) {
        if (RePlugin.uninstall("sun.sundy.pospluginone")){
            ToastUtils.showLazzToast("卸载成功！");
        }else {
            ToastUtils.showLazzToast("卸载失败");
        }

    }
}

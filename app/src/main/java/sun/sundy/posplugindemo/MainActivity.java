package sun.sundy.posplugindemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;

import java.io.File;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionGen
                .with(this)
                .addRequestCode(0x13)
                .permissions(
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.READ_PHONE_STATE
                )
                .request();
    }

    public void open(View view) {
        Intent intent = RePlugin.createIntent("sun.sundy.pospluginone",
                "sun.sundy.pospluginone.PluginOneActivity");
        if (!RePlugin.startActivity(MainActivity.this, intent)){
            ToastUtils.showLazzToast("启动失败！");
        }
    }

    public void install(View view) {
        String pluginApk = "pospluginone-debug.apk";
//        String pluginApk = "app-release.apk";
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + pluginApk;
        System.out.println("======fileName:" + fileName);
        File pluginFile = new File(fileName);
        //文件不存在就返回
        if (!pluginFile.exists()){
            System.out.println("=====不存在");
            ToastUtils.showLazzToast("不存在");
            return;
        }
        PluginInfo info = null;
        if (pluginFile.exists()) {
            info = RePlugin.install(fileName);
        }
        if (info != null){
            //预先加载
            RePlugin.preload(info);
            ToastUtils.showLazzToast("安装 pospluginone-debug 成功 " + info.getName());
        }else {
            ToastUtils.showLazzToast("安装失败！");
        }
    }

    public void unInstall(View view) {
        if (RePlugin.uninstall("sun.sundy.pospluginone")){
            ToastUtils.showLazzToast("卸载成功！");
        }else {
            ToastUtils.showLazzToast("卸载失败");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    //权限申请成功
    @PermissionSuccess(requestCode = 0x13)
    public void doSomething() {

    }

    //申请失败
    @PermissionFail(requestCode = 0x13)
    public void doFailSomething() {

    }
}

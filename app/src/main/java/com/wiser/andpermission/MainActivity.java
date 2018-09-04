package com.wiser.andpermission;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wiser.permission.AndPermission;
import com.wiser.permission.PermissionNo;
import com.wiser.permission.PermissionYes;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AndPermission.hasPermission(this, Manifest.permission.READ_SMS)){
            //执行业务
            Toast.makeText(this,"执行业务",Toast.LENGTH_LONG).show();
        }else {
            //申请权限
//            Toast.makeText(this,"申请权限",Toast.LENGTH_LONG).show();
            //申请权限
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.READ_SMS)
                    .send();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //只需要调用这一句，第一个参数是当前Activity/Fragment，回调方法写在当前Activity/Fragment
        AndPermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    // 成功回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionYes(100)
    private void getSucceed(List<String> grantedPermissions) {
        // TODO 申请权限成功。
        Toast.makeText(this,"申请成功",Toast.LENGTH_LONG).show();
    }

    @PermissionNo(100)
    private void getDefeated(List<String> deniedPermissions) {
        AndPermission.defineSettingDialog(this, 1);
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defineSettingDialog(this, 1);
        }
    }
}

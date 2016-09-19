package com.baguette.studioh.baugette;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TextView tv = ((TextView)findViewById(R.id.settingsScreenName));
        RoundedImageView riv = ((RoundedImageView)findViewById(R.id.settingsProfileImage));
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache()).build();
        com.nostra13.universalimageloader.core.ImageLoader il = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        il.init(config);
        il.displayImage("http://"+UserInformation.ServerIP+"/profile-images/image_"+UserInformation.myUser.getUser().getId()+".png",riv);
        tv.setText(UserInformation.myUser.getUser().getScreenName());
    }
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.SettingActivity));
        System.gc();
    }
    protected void settingsClick1(View v) {
        Intent intent = new Intent(getApplicationContext(), AccountInfoActivity.class);
        startActivity(intent);
    }
    protected void settingsClick2(View v) {
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.putExtra("userID",UserInformation.myUser.getUser().getId());
        startActivity(intent);
    }
    protected void settingsClick3(View v) {
        Intent intent = new Intent(getApplicationContext(), ExplainBugActivity.class);
        startActivity(intent);
    }

}

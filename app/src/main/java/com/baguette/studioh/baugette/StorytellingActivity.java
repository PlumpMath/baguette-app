package com.baguette.studioh.baugette;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class StorytellingActivity extends AppCompatActivity {

    ImageView iv;
    ImageLoaderConfiguration config;
    com.nostra13.universalimageloader.core.ImageLoader il = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    int index=2, IMAGE_COUNT=16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storytelling);
        iv = (ImageView)findViewById(R.id.storyImageView);
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache()).build();
        il.init(config);
        il.displayImage("assets://joong_1.png", iv);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.StorytellingActivity));
        System.gc();
    }

    public void storyImageClick(View view) {
        if (index++>IMAGE_COUNT) {
            Toast.makeText(getApplicationContext(), "The story is over :)",Toast.LENGTH_LONG);
            finish();
        }
        il.displayImage("assets://joong_"+index+".png", iv);
    }
}

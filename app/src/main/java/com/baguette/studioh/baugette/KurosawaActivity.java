package com.baguette.studioh.baugette;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class KurosawaActivity extends AppCompatActivity {
    int PICK_DRAWING=10, PICK_PHOTO=20;
    Uri PhotoUri, DrawingUri;
    Boolean photoChosen, drawingChosen;

    ImageLoaderConfiguration config;
    com.nostra13.universalimageloader.core.ImageLoader il = com.nostra13.universalimageloader.core.ImageLoader.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurosawa);
        photoChosen = false; drawingChosen = false;
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache()).build();
        il.init(config);
    }

    public void kurosawaNext(View view) {
        EditText et = (EditText)findViewById(R.id.uploadTitleInput);
        if (et.getText().toString().equals("")) {
            Toast.makeText(this, "Title is empty.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!photoChosen) {
            Toast.makeText(this, "Please choose a photo.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!drawingChosen) {
            Toast.makeText(this, "Please choose a drawing.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(getApplicationContext(), RubyActivity.class);
        intent.putExtra("Title", et.getText().toString());
        intent.putExtra("PhotoUri", PhotoUri.toString());
        intent.putExtra("DrawingUri", DrawingUri.toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.KurosawaActivity));
        System.gc();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_PHOTO) {
                PhotoUri = data.getData();
                ((View)findViewById(R.id.uploadPhotoFilter)).setBackgroundColor(Color.argb(75,255,255,255));
                il.displayImage(PhotoUri.toString(), ((ImageView)findViewById(R.id.uploadPhotoImage)));
                photoChosen = true;
            }
            else if (requestCode == PICK_DRAWING) {
                DrawingUri = data.getData();
                ((View)findViewById(R.id.uploadPictureFilter)).setBackgroundColor(Color.argb(75,255,255,255));
                il.displayImage(DrawingUri.toString(), ((ImageView)findViewById(R.id.uploadDrawingImage)));
                drawingChosen = true;
            }
        }
    }

    public void uploadPhotoBtnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PHOTO);
    }

    public void uploadDrawingBtnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_DRAWING);
    }

    public void back(View view) {
        this.finish();
    }
}

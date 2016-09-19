package com.baguette.studioh.baugette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by studioh on 8/21/16.
 */
public class LoadImage extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imv;
    private String path;

    public LoadImage(ImageView imv) {
        this.imv = imv;
        if(imv.getTag()!=null) this.path = imv.getTag().toString();
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bitmap = null;
        File file = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + path);

        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (imv.getTag() != null)
        {
            if (!imv.getTag().toString().equals(path)) {
               /* The path is not same. This means that this
                  image view is handled by some other async task.
                  We don't do anything and return. */
                return;
            }
        }

        if(result != null && imv != null){
            imv.setVisibility(View.VISIBLE);
            imv.setImageBitmap(result);
        }else{
            imv.setVisibility(View.GONE);
        }
    }

}
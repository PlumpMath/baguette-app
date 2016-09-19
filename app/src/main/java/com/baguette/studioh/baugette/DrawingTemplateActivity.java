package com.baguette.studioh.baugette;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.simplify.ink.InkView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DrawingTemplateActivity extends AppCompatActivity {
    InkView ink;
    GridView colorSelector;
    ColorListAdapter cla = new ColorListAdapter();

    private String[] ColorIds = {"#ffff0000", "#ffff5e00", "#ffffbb00", "#ff1ddb16", "#ff0100ff", "#ff000000", "#fff15f5f", "#fff29661", "#fff2cb61", "#ff86e57f", "#ff6b66ff", "#ffffffff",
            "#ffcc3d3d", "#ffcc723d", "#ffcca63d", "#ff47c83e", "#ff4641d9", "#ff8c8c8c", "#ff980000", "#ff993800", "#ff997000", "#ff2f9d27", "#ff050099", "#ff5d5d5d",
            "#ff670000", "#ff662500", "#ff664b00", "#ff22741c", "#ff030066", "#ff353535"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_template);

        ink = (InkView) findViewById(R.id.inkTmp);
        ink.setColor(Color.argb(255,0,0,0));
        ink.setMinStrokeWidth(1.5f);
        ink.setMaxStrokeWidth(6f);

        colorSelector = (GridView)findViewById(R.id.ColorSelectorTmp);
        colorSelector.setAdapter(cla);

        for(String str : ColorIds)
            cla.addItem(str);

        colorSelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ink.setColor(Color.parseColor(ColorIds[position]));
                ink.setMinStrokeWidth(1.5f);
                ink.setMaxStrokeWidth(6f);
                colorSelector.setVisibility(View.GONE);
            }
        });
    }

    public void LineBtnClick(View view) {
        colorSelector.setVisibility(View.VISIBLE);
    }

    public void EraseBtnClick(View view) {
        ink.setMinStrokeWidth(6f);
        ink.setMaxStrokeWidth(6f);
    }

    public void ClearBtnClick(View view) {
        ink.clear();
    }

    public void SaveBtnClick(View view)  {
        //SaveImage(overlay(drawableToBitmap(getResources().getDrawable(R.drawable.qqqq)), ink.getBitmap()));
        SaveImage(ink.getBitmap(Color.parseColor("#FFFFFF")));
    }
    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }
    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Pictures");
        myDir.mkdirs();
        Random generator = new Random();
        Integer n = generator.nextInt(100000);
        String fname = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss ").format(new Date())+n+".png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(this, "File saved to: "+fname, Toast.LENGTH_LONG).show();
            //new SingleMediaScanner(this, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void back(View view) {
        finish();
    }
}

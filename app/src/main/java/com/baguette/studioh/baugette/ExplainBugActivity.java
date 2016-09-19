package com.baguette.studioh.baugette;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExplainBugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_bug);
    }

    public void expBugOnClick1(View view) {
        Intent intent = new Intent(getApplicationContext(), LifeVideoActivity.class);
        startActivity(intent);
    }

    public void expBugOnClick2(View view) {
        Intent intent = new Intent(getApplicationContext(), StorytellingActivity.class);
        startActivity(intent);
    }
    public void expBugOnClick3(View view) {
        Intent intent = new Intent(getApplicationContext(), DrawingTemplateActivity.class);
        startActivity(intent);
    }
    public void expBugOnClick4(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/culturalinstitute/beta/u/0/search?q=lee+jung+seob"));
        startActivity(browserIntent);
    }
}

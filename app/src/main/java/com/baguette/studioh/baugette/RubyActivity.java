package com.baguette.studioh.baugette;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Picture;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RubyActivity extends AppCompatActivity {

    String Title, Drawing, Photography;
    EditText memoryTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruby);
        memoryTxt = (EditText)findViewById(R.id.postMemoryTxt);
        Intent intent = getIntent();
        Title = intent.getStringExtra("Title");
        Drawing = intent.getStringExtra("DrawingUri");
        Photography = intent.getStringExtra("PhotoUri");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.KurosawaActivity));
        System.gc();
    }
    public void back(View view) {
        this.finish();
    }

    public void postConfirmClick(final View view) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Uploading memory..");
        pd.show();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://"+UserInformation.ServerIP+"/api/post/write",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        MultipartUpload mpu = new MultipartUpload();
                        Gson gson = new Gson();
                        PostBase pb = gson.fromJson(response, PostBase.class);
                        mpu.DoUpload(getApplicationContext(), ("http://" + UserInformation.ServerIP + "/upload/content-images/image_"+pb.getpostid()+"_1.png"), new File(Tools.getPath(getApplicationContext(), Uri.parse(Photography))));
                        mpu.DoUpload(getApplicationContext(), ("http://" + UserInformation.ServerIP + "/upload/content-images/image_"+pb.getpostid()+"_2.png"), new File(Tools.getPath(getApplicationContext(), Uri.parse(Drawing))));
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Upload complete", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), SNSMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "Error while uploading memory: Check your network status.", Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uploaderID", UserInformation.myUser.getUser().getId());
                params.put("explanation", memoryTxt.getText().toString());
                params.put("title", Title);
                return params;
            }
        };
        rq.add(postRequest);
    }
}

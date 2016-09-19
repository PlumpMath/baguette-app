package com.baguette.studioh.baugette;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountInfoActivity extends AppCompatActivity {

    final int PICK_BACKGROUND=10, PICK_SIGN=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
    }
    @Override
    protected void onResume() {
        super.onResume();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache()).build();
        com.nostra13.universalimageloader.core.ImageLoader il = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        il.init(config);
        il.displayImage("http://"+UserInformation.ServerIP+"/profile-images/image_"+UserInformation.myUser.getUser().getId()+".png", (ImageView)(findViewById(R.id.accountProfileImage)));
        il.displayImage("http://"+UserInformation.ServerIP+"/background-images/image_"+UserInformation.myUser.getUser().getId()+".png", (ImageView)(findViewById(R.id.accountBackgroundImage)));
        ((TextView)findViewById(R.id.accountInfoID)).setText("ID: "+UserInformation.myUser.getUser().getUserIDString());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.UserProfileActivity));
        System.gc();
    }

    public void onACIUpdateCick(View view) {
        RequestQueue rq = Volley.newRequestQueue(this);
        boolean flag = false;
        final TextView usernameText = (TextView)findViewById(R.id.AccountInfoUsername);
        final TextView passwordText = (TextView)findViewById(R.id.AccountInfoPW);
        final TextView passwordConfirmText = (TextView)findViewById(R.id.AccountInfoPWConfirm);
        final TextView quoteText = (TextView)findViewById(R.id.AccountInfoQuote);

        if (!usernameText.getText().toString().equals("")) {
            flag = true;
            //TODO: Username(screenname) text updating
            StringRequest postRequest = new StringRequest(Request.Method.PUT, "http://"+UserInformation.ServerIP+"/api/user/change/screenname",
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Username successfully changed", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(getApplicationContext(), "Error while processing request: Check your network status.", Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userIDString", UserInformation.myUser.getUser().getUserIDString());
                    params.put("newScreenName", usernameText.getText().toString());
                    return params;
                }
            };
            rq.add(postRequest);
        }
        if (!quoteText.getText().toString().equals("")) {
            flag = true;
            //TODO: Quote text updating
            StringRequest postRequest = new StringRequest(Request.Method.PUT, "http://"+UserInformation.ServerIP+"/api/user/change/quote",
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Quote successfully changed", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(getApplicationContext(), "Error while processing request: Check your network status.", Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userIDString", UserInformation.myUser.getUser().getUserIDString());
                    params.put("quote", quoteText.getText().toString());
                    return params;
                }
            };
            rq.add(postRequest);
        }
        if (!passwordText.getText().toString().equals("")) {
            if (!passwordText.getText().toString().equals(passwordConfirmText.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                return;
            }
            flag = true;
            StringRequest postRequest = new StringRequest(Request.Method.PUT, "http://"+UserInformation.ServerIP+"/api/user/change/password",
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Password successfully changed", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(getApplicationContext(), "Error while processing request: Check your network status.", Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userIDString", UserInformation.myUser.getUser().getUserIDString());
                    params.put("password", passwordConfirmText.getText().toString());
                    return params;
                }
            };
            rq.add(postRequest);
        }
        if (flag) {
            //TODO: User update

        }

    }

    public void AccountBGChangeBtn(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_BACKGROUND);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == PICK_BACKGROUND) && (resultCode == Activity.RESULT_OK)) {
            MultipartUpload mpu = new MultipartUpload();
            mpu.DoUpload(getApplicationContext(), ("http://" + UserInformation.ServerIP + "/upload/background-images/image_"+UserInformation.myUser.getUser().getId()+".png"), new File(Tools.getPath(getApplicationContext(), data.getData())));
            onResume();
        }
        else if ((requestCode == PICK_SIGN) && (resultCode == Activity.RESULT_OK)) {
            MultipartUpload mpu = new MultipartUpload();
            mpu.DoUpload(getApplicationContext(), ("http://" + UserInformation.ServerIP + "/upload/profile-images/image_"+UserInformation.myUser.getUser().getId()+".png"), new File(Tools.getPath(getApplicationContext(), data.getData())));
            onResume();
        }
    }

    public void accountProfileSignatureClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_SIGN);
    }
}

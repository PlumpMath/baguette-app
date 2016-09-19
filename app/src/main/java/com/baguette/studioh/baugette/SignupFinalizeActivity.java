package com.baguette.studioh.baugette;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.HashMap;
import java.util.Map;

public class SignupFinalizeActivity extends AppCompatActivity {

    private String id, username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_finalize);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        username = intent.getStringExtra("Username");
        password = intent.getStringExtra("Password");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.SignupFinalizeActivity));
        System.gc();
    }

    public void onSignupFinalizeClick(View view) {
        final String quote = ((EditText)findViewById(R.id.signupQuote)).getText().toString().trim();
        if (quote.equals("")) {
            Toast.makeText(this, "Quote is empty", Toast.LENGTH_LONG);
            return;
        }
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Signing up..");

        //Login request
        pd.show();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://"+UserInformation.ServerIP+"/api/user/add",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Gson gson = new Gson();
                        UserInformation.myUser = gson.fromJson(response, UserBase.class);
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Signed up, please log in.",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Error while creating account, please check your network, or try another name", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userIDString", id);
                params.put("screenName", username);
                params.put("password", password);
                params.put("quote", quote);
                return params;
            }
        };
        rq.add(postRequest);
    }
}

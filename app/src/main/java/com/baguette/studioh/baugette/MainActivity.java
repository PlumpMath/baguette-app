package com.baguette.studioh.baugette;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.MainActivity));
        System.gc();
    }

    public void onLoginBtnClick(View view) throws JSONException {

        EditText et1 = (EditText) findViewById(R.id.editID);
        EditText et2 = (EditText) findViewById(R.id.editPassword);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Logging in..");

        if (et1.getText().toString().equals("") || et2.getText().toString().equals(""))
            return;

        //Login request
        pd.show();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://"+UserInformation.ServerIP+"/api/user/login",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.v("loginResult", response.toString());
                        Gson gson = new Gson();
                        UserInformation.myUser = gson.fromJson(response, UserBase.class);
                        pd.dismiss();
                        Intent loginSuccess = new Intent(getApplicationContext(), SNSMainActivity.class);
                        //Intent loginSuccess = new Intent(getApplicationContext(), UserProfileActivity.class);
                        //Intent loginSuccess = new Intent(getApplicationContext(), AccountInfoActivity.class);
                        //Intent loginSuccess = new Intent(getApplicationContext(), PostDetailActivity.class);
                        //loginSuccess.putExtra("postID", "57b8a37bf60d8ae115fbe660");
                        //Intent loginSuccess = new Intent(getApplicationContext(), KurosawaActivity.class);
                        //Intent loginSuccess = new Intent(getApplicationContext(), DrawingActivity.class);
                        loginSuccess.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        loginSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginSuccess);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Login error: Please check your credentials or connection", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userIDString", ((EditText)findViewById(R.id.editID)).getText().toString());
                params.put("password", ((EditText)findViewById(R.id.editPassword)).getText().toString());
                return params;
            }
        };
        rq.add(postRequest);
    }

    public void btnSignUpBtnClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}

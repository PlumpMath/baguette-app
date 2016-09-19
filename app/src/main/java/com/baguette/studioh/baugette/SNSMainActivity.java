package com.baguette.studioh.baugette;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SNSMainActivity extends AppCompatActivity {

    LinearLayout ll;
    ListView contentListView;
    PostBase pb;
    PostPreviewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_snsmain);
        super.onCreate(savedInstanceState);
        ll = (LinearLayout)findViewById(R.id.mainEmptyMessage);
        contentListView = (ListView)findViewById(R.id.feedListview);
        listViewAdapter = new PostPreviewAdapter();
        contentListView.setAdapter(listViewAdapter);
        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String PostID = pb.getPosts().get(listViewAdapter.getCount()-1-position).getId();
                Intent loginSuccess = new Intent(getApplicationContext(), PostDetailActivity.class);
                loginSuccess.putExtra("postID", PostID);
                startActivity(loginSuccess);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        UpdateContent();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.SNSMainActivity));
        System.gc();
    }
    public void back(View view) {
        this.finish();
    }

    public void asdfasdf(View view) {
        Log.v("","");
    }

    public void UpdateContent() {
        final ProgressDialog pd =  new ProgressDialog(this);
        pd.setMessage("Loading feed..");
        pd.show();
        listViewAdapter.Clear();
        listViewAdapter.notifyDataSetChanged();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.GET, "http://"+UserInformation.ServerIP+"/api/user/"+UserInformation.myUser.getUser().getUserIDString()+"/stream",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSON",response);
                        Gson gson = new Gson();
                        pb = gson.fromJson(response, PostBase.class);
                        List<Post> posRes = pb.getPosts();
                        for(int i=posRes.size()-1;i>=0;i--) {
                            listViewAdapter.addItem("http://"+UserInformation.ServerIP+"/profile-images/image_"+posRes.get(i).getUploader()+".png",
                                    "http://"+UserInformation.ServerIP+"/content-images/image_"+posRes.get(i).getId()+"_1.png",
                                    "http://"+UserInformation.ServerIP+"/content-images/image_"+posRes.get(i).getId()+"_2.png",
                                    posRes.get(i).getTitle(), posRes.get(i).getUploaderName(),
                                    Integer.toString(posRes.get(i).getLikes().size()), Integer.toString(posRes.get(i).getComments().size()),
                                    posRes.get(i).getExplanation());
                        }
                        if (posRes.size()!=0) ll.setVisibility(View.GONE);
                        else ll.setVisibility(View.VISIBLE);
                        listViewAdapter.notifyDataSetChanged();
                        pd.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "Error while processing request: Check your network status.", Toast.LENGTH_LONG).show();
                    }
                });
        rq.add(postRequest);
    }

    public void mainPostWriteClick(View view) {
        Intent intent = new Intent(this, KurosawaActivity.class);
        startActivity(intent);
    }

    public void mainSettingsOnClick(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void MainProfileviewClick(View view) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Opening profile..");
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.putExtra("userID",UserInformation.myUser.getUser().getId());
        startActivity(intent);
    }

    public void mainDoodleBoardClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DrawingActivity.class);
        startActivity(intent);
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

    public void mainSearchClick(View view) {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }
}

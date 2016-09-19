package com.baguette.studioh.baugette;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    private String PageUserIDString;
    private User PageUser;
    private PostBase pb;
    private Boolean isFollowing;
    private ImageView followIV;

    ImageLoaderConfiguration config;
    com.nostra13.universalimageloader.core.ImageLoader il = com.nostra13.universalimageloader.core.ImageLoader.getInstance();

    ListView contentListView;
    PostPreviewAdapter listViewAdapter = new PostPreviewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        PageUserIDString = intent.getStringExtra("userID");
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.signup_bg);
        Bitmap res = Tools.blur((Context)(getApplicationContext()), bm, 25);
        ImageView iv = (ImageView)findViewById(R.id.userProfileBackground);
        iv.setImageBitmap(res);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        followIV = (ImageView)findViewById(R.id.profileFollowImage);

        contentListView = (ListView)findViewById(R.id.postContentView);
        contentListView.setAdapter(listViewAdapter);
        contentListView.setClickable(true);

        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache()).build();
        il.init(config);
    }
    @Override
    protected void onResume() {
        super.onResume();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading content..");
        pd.show();
        final RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.GET, "http://"+UserInformation.ServerIP+"/api/user/idfromCode/"+PageUserIDString,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String responsed) {
                        StringRequest postsRequest2 = new StringRequest(Request.Method.GET, "http://"+UserInformation.ServerIP+"/api/post/user/"+responsed.substring(1,responsed.length()-1),
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.v("loginResult", response.toString());
                                        Gson gson = new Gson();
                                        pb = gson.fromJson(response, PostBase.class);
                                        pd.dismiss();
                                        for(int i=pb.getPosts().size()-1;i>=0;i--) {
                                            Post post = pb.getPosts().get(i);
                                            //profile preview title author
                                            listViewAdapter.addItem("http://"+UserInformation.ServerIP+"/profile-images/image_"+post.getUploader()+".png",
                                                    "http://"+UserInformation.ServerIP+"/content-images/image_"+post.getId()+"_1.png",
                                                    "http://"+UserInformation.ServerIP+"/content-images/image_"+post.getId()+"_2.png",
                                                    post.getTitle(), post.getUploaderName(),
                                                    Integer.toString(post.getLikes().size()), Integer.toString(post.getComments().size()),
                                                    post.getExplanation());
                                        }

                                        Tools.setListViewHeightBasedOnChildren(contentListView);
                                        listViewAdapter.notifyDataSetChanged();
                                        Log.v("","");
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(), "Error while loading page", Toast.LENGTH_LONG).show();
                                    }
                                });
                        rq.add(postsRequest2);
                        StringRequest userRequestd = new StringRequest(Request.Method.GET, "http://"+UserInformation.ServerIP+"/api/user/"+responsed.substring(1,responsed.length()-1),
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.v("loginResult", response.toString());
                                        Gson gson = new Gson();
                                        User[] ubs = gson.fromJson(response, User[].class);
                                        User ub = ubs[0];
                                        PageUser = ub;
                                        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                                                .memoryCache(new WeakMemoryCache()).build();
                                        com.nostra13.universalimageloader.core.ImageLoader il = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                                        il.init(config);
                                        il.displayImage("http://"+UserInformation.ServerIP+"/profile-images/image_"+ub.getId()+".png", (ImageView)(findViewById(R.id.profileImageView)));
                                        il.displayImage("http://"+UserInformation.ServerIP+"/background-images/image_"+ub.getId()+".png", (ImageView)(findViewById(R.id.userProfileBackground)));
                                        ((TextView)findViewById(R.id.profileQuoteView)).setText(ub.getQuote());
                                        ((TextView)findViewById(R.id.profileScreenName)).setText(ub.getScreenName());
                                        ((TextView)findViewById(R.id.profileTopBarName)).setText(ub.getScreenName());
                                        ((TextView)findViewById(R.id.profileFollowers)).setText(ub.getFollowers().size()+" FOLLOWERS");
                                        ((TextView)findViewById(R.id.profileFollowing)).setText(ub.getFollowing().size()+" FOLLOWING");
                                        isFollowing = ub.getFollowers().contains(UserInformation.myUser.getUser().getId());
                                        CheckLighting();
                                        pd.dismiss();

                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(), "Error while loading page", Toast.LENGTH_LONG).show();
                                    }
                                });
                        rq.add(userRequestd);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error while loading :(", Toast.LENGTH_LONG).show();
                        Log.e("error",error.toString());
                        pd.dismiss();
                    }
                });
        rq.add(postRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.UserProfileActivity));
        System.gc();
    }

    public void back(View view) {
        this.finish();
    }

    public void CheckLighting() {
        if (isFollowing) il.displayImage("drawable://"+R.drawable.light_on, followIV);
        else il.displayImage("drawable://"+R.drawable.person_ico, followIV);
    }

    public void followButtonClicked(View view) {
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(isFollowing?"Unfollowing..":"Following..");
        pd.show();
        if (PageUser.getUserIDString().equals(UserInformation.myUser.getUser().getUserIDString())) {
            Toast.makeText(getApplicationContext(), "You can't follow yourself", Toast.LENGTH_LONG).show();
            return;
        }
        Log.v("asdfasdf","http://"+UserInformation.ServerIP+"/api/user"+PageUser.getUserIDString()+(isFollowing?"/unfollow":"/follow"));
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://"+UserInformation.ServerIP+"/api/user/"+PageUser.getUserIDString()+(isFollowing?"/unfollow":"/follow"),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        isFollowing = !isFollowing;
                        onResume();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error while following :(", Toast.LENGTH_LONG).show();
                        Log.e("error",error.toString());
                        pd.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("followerUserIDString", UserInformation.myUser.getUser().getUserIDString());
                return params;
            }
        };
        rq.add(postRequest);
    }
}

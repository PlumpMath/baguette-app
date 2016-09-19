package com.baguette.studioh.baugette;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.Map;

public class PostDetailActivity extends AppCompatActivity {

    public Post postItem;
    public String postID;
    public Boolean iLikeThis = false;

    ListView commentListView;
    CommentAdapter commentAdapter = new CommentAdapter();
    ImageLoaderConfiguration config;
    com.nostra13.universalimageloader.core.ImageLoader il = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        postID = getIntent().getStringExtra("postID");

        commentListView = (ListView)findViewById(R.id.detailCommentView);
        commentListView.setAdapter(commentAdapter);
        rq  = Volley.newRequestQueue(getApplicationContext());
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache()).build();
        il.init(config);
    }
    @Override
    protected void onResume() {
        super.onResume();
        commentAdapter.Clear();
        StringRequest postRequest = new StringRequest(Request.Method.GET, "http://"+UserInformation.ServerIP+"/api/post/"+postID,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        PostBase pb = gson.fromJson(response, PostBase.class);
                        postItem = pb.getPost();
                        il.displayImage("http://"+UserInformation.ServerIP+"/profile-images/image_"+postItem.getUploader()+".png",(RoundedImageView)findViewById(R.id.detailProfileImage));
                        il.displayImage("http://"+UserInformation.ServerIP+"/content-images/image_"+postID+"_1.png",(ImageView)findViewById(R.id.detailPreviewImage1));
                        il.displayImage("http://"+UserInformation.ServerIP+"/content-images/image_"+postID+"_2.png",(ImageView)findViewById(R.id.detailPreviewImage2));
                        ((TextView)findViewById(R.id.detailAuthorText)).setText(postItem.getUploaderName());
                        ((TextView)findViewById(R.id.detailTitleText)).setText(postItem.getTitle());
                        ((TextView)findViewById(R.id.detailAdditionalText)).setText(postItem.getExplanation());
                        if (postItem.getLikes() != null) {
                            ((TextView) findViewById(R.id.detailLikeCount)).setText(Integer.toString(postItem.getLikes().size()));
                        }
                        else ((TextView)findViewById(R.id.detailLikeCount)).setText("0");
                        if (postItem.getComments() != null)
                            ((TextView)findViewById(R.id.detailCommentCount)).setText(Integer.toString(postItem.getComments().size()));
                        else ((TextView)findViewById(R.id.detailCommentCount)).setText("0");

                        for(Comment com : postItem.getComments()) {
                            commentAdapter.addItem(com.getUploaderUsername(), com.getContent());
                        }
                        Tools.setListViewHeightBasedOnChildren(commentListView);

                        if (postItem.getLikes().contains(UserInformation.myUser)) iLikeThis = true;
                        commentAdapter.notifyDataSetChanged();
                        CheckLighting();
                    }
                },
                new Response.ErrorListener()
        {
            @Override
                    public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while loading page", Toast.LENGTH_LONG).show();
                Log.e("error",error.toString());
            }
        });
        rq.add(postRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.PostDetailActivity));
        System.gc();
    }

    public void detailCommentSendBtn(View view) {
        if(((EditText)findViewById(R.id.detailCommentText)).getText().toString().trim().equals(""))
            return;
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Posting comment..");
        pd.show();
        Log.v("qwerqer","http://"+UserInformation.ServerIP+"/api/post/"+postID+"/comment");
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://"+UserInformation.ServerIP+"/api/post/"+postID+"/comment",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        ((EditText)findViewById(R.id.detailCommentText)).setText("");
                        onResume();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error while loading", Toast.LENGTH_LONG).show();
                        Log.e("error",error.toString());
                        pd.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", UserInformation.myUser.getUser().getId());
                params.put("content", ((EditText)findViewById(R.id.detailCommentText)).getText().toString());
                return params;
            }
        };
        rq.add(postRequest);
    }

    private void CheckLighting() {
        ImageView iv = (ImageView)findViewById(R.id.detailLightbulb);
        if (iLikeThis) il.displayImage("drawable://"+R.drawable.light_on, iv);
        else il.displayImage("drawable://"+R.drawable.light_off, iv);
    }

    public void lightbulbClick(View view) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Turning "+(iLikeThis?"off":"on")+" the lightbulb..");
        pd.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://"+UserInformation.ServerIP+"/api/post/"+postID+(iLikeThis?"/unlike":"/like"),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        iLikeThis = !iLikeThis;
                        onResume();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error while playing with the lightbulb :(", Toast.LENGTH_LONG).show();
                        Log.e("error",error.toString());
                        pd.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", UserInformation.myUser.getUser().getId());
                return params;
            }
        };
        rq.add(postRequest);
    }

    public void openProfileClick(View view) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Opening profile..");
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.putExtra("userID",postItem.getUploader());
        startActivity(intent);
    }
    public void back(View view) {
        this.finish();
    }
}
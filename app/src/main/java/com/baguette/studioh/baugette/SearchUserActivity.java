package com.baguette.studioh.baugette;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ByteArrayPool;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SearchUserActivity extends AppCompatActivity {

    private User[] result;
    private EditText et;
    private ListView lv;
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        et = (EditText)findViewById(R.id.searchText);
        lv = (ListView)findViewById(R.id.search_result);
        listAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#000000"));
                return textView;
            }
        };
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ProgressDialog pd = new ProgressDialog(getApplicationContext());
                pd.setMessage("Opening profile..");
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                intent.putExtra("userID",result[position].getId());
                startActivity(intent);
            }
        });
    }

    public void searchSendBtn(View view) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Searching..");

        //Login request
        pd.show();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.GET, "http://"+UserInformation.ServerIP+"/api/user/search/"+et.getText().toString(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Gson gson = new Gson();
                        result = gson.fromJson(response, User[].class);
                        for(User us : result) {
                            listAdapter.add(us.getScreenName()+" (Login ID: "+us.getUserIDString()+")");
                        }
                        listAdapter.notifyDataSetChanged();
                        pd.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Error while searching :(", Toast.LENGTH_LONG).show();
                    }
                });
        rq.add(postRequest);
    }
}

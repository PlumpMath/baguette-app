package com.baguette.studioh.baugette;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by studioh on 8/21/16.
 */
public class MultipartUpload {
    OkHttpClient client = new OkHttpClient();
    public void DoUpload(final Context context, final String url, final File file) {
        Thread th = new Thread(new Runnable() {
            public void run() {
                try {
                    upload(url, file);
                    Toast.makeText(context, "File upload finished", Toast.LENGTH_LONG);
                }
                catch (Exception e) {
                    Log.e("ServerError", e.toString());
                };
            }
        });
        th.start();
        try {
            th.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private Response upload(String url, File file) throws IOException {
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Response response = this.client.newCall(request).execute();
        return response;
    }
}
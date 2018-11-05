package com.jadiong.customrequestdemo;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class CustomByteRequest extends Request<Post> {

    private final Gson gson;

    private MainActivity.PostResponseListener postResponseListener;

    public CustomByteRequest(int method, String url, MainActivity.PostResponseListener postListener,
                             @Nullable Response.ErrorListener listener) {
        super(method, url, listener);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        postResponseListener = postListener;
    }

    @Override
    protected Response<Post> parseNetworkResponse(NetworkResponse response) {

        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            List<Post> posts = Arrays.asList(gson.fromJson(json, Post[].class));

            Post data = posts.get(0);
            return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            Log.d("JD.Error", e.toString());
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Post response) {
        postResponseListener.onResponse(response);
    }
}

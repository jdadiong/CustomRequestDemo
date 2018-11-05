package com.jadiong.customrequestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static final String ENDPOINT = "https://kylewbanks.com/rest/posts.json";

    private RequestQueue requestQueue;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView1);

        requestQueue = Volley.newRequestQueue(this);
        CustomByteRequest request = new CustomByteRequest(Request.Method.GET, ENDPOINT, postResponseListener, errorListener);
        requestQueue.add(request);
    }

    public interface PostResponseListener {

        void onResponse(Post response);
    }

    PostResponseListener postResponseListener = new PostResponseListener() {
        @Override
        public void onResponse(Post response) {
            textView.setText("Name of blog post is: " + response.title);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };
}

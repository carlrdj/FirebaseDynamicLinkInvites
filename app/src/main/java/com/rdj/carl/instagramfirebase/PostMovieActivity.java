package com.rdj.carl.instagramfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PostMovieActivity extends AppCompatActivity {
    private TextView tvTitlePost;
    private static final String KEY_EXTRA_POST = "key_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_movie);

        tvTitlePost = (TextView) findViewById(R.id.tvTitlePost);

        if(getIntent().getExtras() != null){
            tvTitlePost.setText(getIntent().getExtras().getString(KEY_EXTRA_POST));
        }
    }
}

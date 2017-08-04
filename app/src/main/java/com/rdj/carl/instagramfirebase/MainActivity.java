package com.rdj.carl.instagramfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private static final String PROJECT_ID = "https://ghn2t.app.goo.gl/";
    private static final String STARWARS_KEY = "StarWars";
    private static final String AVENGERS_KEY = "LosVengadores";
    private static final String DEEP_LINK = "http://carl.rdj.com/";
    private static final String KEY_EXTRA_POST = "key_post";
    private Button bShareStarWars;
    private Button bShareAvengers;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bShareStarWars = (Button) findViewById(R.id.bShareStarWars);
        bShareAvengers = (Button) findViewById(R.id.bShareAvengers);

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(AppInvite.API)
                .build();

        boolean autoLaunchDeepLink = false;
        AppInvite.AppInviteApi.getInvitation(googleApiClient, this,autoLaunchDeepLink)
                .setResultCallback(new ResultCallback<AppInviteInvitationResult>() {
                    @Override
                    public void onResult(@NonNull AppInviteInvitationResult appInviteInvitationResult) {
                        if (appInviteInvitationResult.getStatus().isSuccess()){
                            Intent intent = appInviteInvitationResult.getInvitationIntent();
                            String deepLink = AppInviteReferral.getDeepLink(intent);
                            String[] strArra = deepLink.split(DEEP_LINK);
                            String id_post = strArra[1];

                            Intent i = new Intent(MainActivity.this, PostMovieActivity.class);
                            if (id_post.equals(STARWARS_KEY + "/")){
                                i.putExtra(KEY_EXTRA_POST, STARWARS_KEY);
                            }
                            if (id_post.equals(AVENGERS_KEY + "/")){
                                i.putExtra(KEY_EXTRA_POST, AVENGERS_KEY);
                            }
                            startActivity(i);
                        }
                    }
                });

        bShareStarWars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDeepLink(buildDeepLink(STARWARS_KEY));
            }
        });

        bShareAvengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDeepLink(buildDeepLink(AVENGERS_KEY));
            }
        });

    }
    private String buildDeepLink(String key){
        return PROJECT_ID + "?link=http://carl.rdj.com/" + key + "/" + "&apn=" + getApplicationContext().getPackageName();
    }

    private void shareDeepLink(String deepLink){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Firebase DeepLink Share");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

package com.karthikkompelli.linkedinloginsampleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.karthikkompelli.linkedinlogin.Test;
import com.karthikkompelli.linkedinlogin.builder.LinkedInBuilder;
import com.karthikkompelli.linkedinlogin.data.LinkedInUserDetails;
import com.karthikkompelli.linkedinlogin.utils.KeyUtils;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserDetails;
    private ImageView ivUserImage;
    private View buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = findViewById(R.id.buttonLogin);
        tvUserDetails = findViewById(R.id.tvUserDetails);
        ivUserImage = findViewById(R.id.ivUserImage);

        buttonLogin.setOnClickListener(v -> {
            Test test = new Test();
            tvUserDetails.setText(test.getString("Karthik"));
            Intent intent = new LinkedInBuilder.Builder(this)
                    .setClientId(getString(R.string.client_id))//CLIENT_ID
                    .setClientSecret(getString(R.string.client_secret))//CLIENT_SECRET
                    .setRedirectUri(getString(R.string.redirect_uri))//REDIRECT_URI
                    .setStateValue(getString(R.string.state_value))//STATE_VALUE
                    .setScopeValue(KeyUtils.BOTH_EMAIL_USERDETAILS_SCOPE_VALUE)//PASS_SCOPE_VALUE_HERE
                    .build();
            startActivityForResult(intent, KeyUtils.REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case KeyUtils.REQUEST_CODE: {
                    LinkedInUserDetails linkedInUser = data.getParcelableExtra(KeyUtils.KEY_LINKEDIN_CONTENT);
                    if (linkedInUser != null) {
                        buttonLogin.setVisibility(View.GONE);
                        tvUserDetails.setText(linkedInUser.getFirstName() + " " + linkedInUser.getLastName() + "\n\n" + linkedInUser.getEmail());
//                        ivUserImage.loadImage(linkedInUser.image, RequestOptions.circleCropTransform())
                        loadImage(ivUserImage, linkedInUser.getImage(), RequestOptions.circleCropTransform());
                    } else {
                        //handle the error
                    }
                }
                break;
            }
        } else {
            //Login failed handle error
        }
    }

    private void loadImage(ImageView imageView, String url, RequestOptions requestOptions) {
        RequestBuilder<Drawable> requestBuilder = Glide.with(imageView.getContext()).load(url);
        if (requestOptions != null) {
            requestBuilder.apply(requestOptions);
        }
        requestBuilder.into(imageView);
    }
}

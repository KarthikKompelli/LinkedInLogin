package com.karthikkompelli.linkedinlogin.presentation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.karthikkompelli.linkedinlogin.R;
import com.karthikkompelli.linkedinlogin.data.AccessTokenResponse;
import com.karthikkompelli.linkedinlogin.data.LinkedInUserDetails;
import com.karthikkompelli.linkedinlogin.data.UserEmailResponse;
import com.karthikkompelli.linkedinlogin.data.UserListResponse;
import com.karthikkompelli.linkedinlogin.domain.ApiParam;
import com.karthikkompelli.linkedinlogin.domain.WebApiClient;
import com.karthikkompelli.linkedinlogin.utils.KeyUtils;
import com.karthikkompelli.linkedinlogin.utils.ShowToastUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.AUTHORIZATION_CODE;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.AUTHORIZATION_VALUE;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.BEARER;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.CLIENT_ID_KEY;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.CODE;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.COMMON_URL;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.ERROR;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.KEY_LINKEDIN_CONTENT;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.REDIRECT_URI_KEY;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.RESPONSE_TYPE;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.SCOPE;
import static com.karthikkompelli.linkedinlogin.utils.KeyUtils.STATE;


public class MiLinkedInActivity extends AppCompatActivity {

    private CookieManager cookieManager;
    private LinkedInUserDetails userDetails = new LinkedInUserDetails();
    private String oAuthUrl = null;
    private String clientId = null;
    private String clientSecret = null;
    private String redirectUri = null;
    private String accessToken = null;
    private String stateValue = null;
    private String scopeValue = null;
    private WebView wvLinkIn;

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_linked_in);
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().hasExtra(KeyUtils.CLIENT_ID)) {
                clientId = getIntent().getStringExtra(KeyUtils.CLIENT_ID);
            }
            if (getIntent().hasExtra(KeyUtils.CLIENT_SECRET)) {
                clientSecret = getIntent().getStringExtra(KeyUtils.CLIENT_SECRET);
            }
            if (getIntent().hasExtra(KeyUtils.REDIRECT_URI)) {
                redirectUri = getIntent().getStringExtra(KeyUtils.REDIRECT_URI);
            }
            if (getIntent().hasExtra(KeyUtils.STATE_VALUE)) {
                stateValue = getIntent().getStringExtra(KeyUtils.STATE_VALUE);
            }
            if (getIntent().hasExtra(KeyUtils.SCOPE_VALUE_KEY)) {
                scopeValue = getIntent().getStringExtra(KeyUtils.SCOPE_VALUE_KEY);
            }
        }
        wvLinkIn = findViewById(R.id.wvLinkIn);
        cookieManager = CookieManager.getInstance();
        cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookies(null);
        WebViewDatabase db = WebViewDatabase.getInstance(this);
        db.clearHttpAuthUsernamePassword();
        wvLinkIn.setWebViewClient(new MyWebClient());
        oAuthUrl =
                COMMON_URL + RESPONSE_TYPE + "=" + CODE + "&" + CLIENT_ID_KEY + "=" + clientId + "&" + REDIRECT_URI_KEY + "=" + redirectUri + "&" + STATE + "=" + stateValue + "&" + SCOPE + "=" + scopeValue;
        wvLinkIn.loadUrl(oAuthUrl);
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            cookieManager.setAcceptCookie(true);
        }

        @RequiresApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return handleUrl(request.getUrl().toString());
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return handleUrl(url);
        }

        /**
         * If uri doesnot contain redirect uri then return false
         * If uri contains error then reload url again
         * If  uri state value didnot match with state value then returns false
         * If authorization code is null then return false otherwise call Access token  api using authorization code
         **/

        public boolean handleUrl(String url) {
            Uri uri = Uri.parse(url);
            if (!uri.toString().contains(redirectUri)) {
                return false;
            } else if (uri.getQueryParameter(ERROR) != null) {
                wvLinkIn.loadUrl(oAuthUrl);
                if (null != uri.getQueryParameter(KeyUtils.ERROR_DESCRIPTION).toString()) {
                    ShowToastUtils.showToast(MiLinkedInActivity.this, uri.getQueryParameter(KeyUtils.ERROR_DESCRIPTION).toString());
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                }
                return false;
            } else if (!uri.getQueryParameter(STATE).equals(stateValue)) {
                return false;
            } else if (uri.getQueryParameter(CODE) == null) {
                return false;
            } else {
                String code = uri.getQueryParameter(CODE);
                if (code != null) {
                    callAccessTokenApi(code);
                }
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.getSettings().setUseWideViewPort(false);
        }
    }

    /**
     * Get access token api from linked in get request
     */
    private void callAccessTokenApi(String code) {
        HashMap<String, String> hashMap = new HashMap<>();

        if (clientId != null)
            hashMap.put(ApiParam.CLIENT_ID, clientId);
        if (redirectUri != null)
            hashMap.put(ApiParam.REDIRECT_URI, redirectUri);
        if (clientSecret != null)
            hashMap.put(ApiParam.CLIENT_SECRET, clientSecret);
        hashMap.put(ApiParam.CODE, code);
        hashMap.put(ApiParam.GRANT_TYPE, AUTHORIZATION_CODE);

        WebApiClient.webApiTwitchAccessToken(ApiParam.BASE_URL)
                .callAccessTokenApi(AUTHORIZATION_VALUE, hashMap)
                .enqueue(new Callback<AccessTokenResponse>() {
                    @Override
                    public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    accessToken = response.body().getAccessToken().toString();
                                    if (scopeValue.equals(KeyUtils.ONLY_PROFILE_SCOPE) || scopeValue.equals(KeyUtils.BOTH_EMAIL_USERDETAILS_SCOPE_VALUE)) {
                                        if (null != response.body().getAccessToken()) {
                                            callUserDetailsApi(response.body().getAccessToken());
                                        }
                                    } else if (scopeValue.equals(KeyUtils.ONLY_EMAIL_SCOPE) || scopeValue.equals(KeyUtils.BOTH_EMAIL_USERDETAILS_SCOPE_VALUE)) {
                                        if (null != response.body().getAccessToken()) {
                                            callUserEmailApi(response.body().getAccessToken());
                                        }
                                    } else {
                                        setResult(Activity.RESULT_CANCELED, new Intent());
                                        finish();
                                    }
                                } else {
                                    setResult(Activity.RESULT_CANCELED, new Intent());
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    /**
     * Get user details api using access token(firstname,lastname,image)
     */
    private void callUserDetailsApi(String accessToken) {
        WebApiClient.webApiTwitchAccessToken(ApiParam.BASE_URL_USER)
                .callUserDetailsApi(BEARER + " " + accessToken)
                .enqueue(new Callback<UserListResponse>() {
                    @Override
                    public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    UserListResponse userDetailsResponse = response.body();
                                    if (userDetailsResponse != null) {
                                        if (null != userDetailsResponse.getFirstName().getLocalized().getEnglishUs()) {
                                            userDetails.setFirstName(userDetailsResponse.getFirstName().getLocalized().getEnglishUs());
                                        }
                                        if (null != userDetailsResponse.getLastName().getLocalized().getEnglishUs()) {
                                            userDetails.setLastName(userDetailsResponse.getLastName().getLocalized().getEnglishUs());
                                        }

                                        if (userDetailsResponse.getProfilePicture().getDisplayImageUrl().getElements().get(0).getIdentifiers().get(0).getIdentifier() != null) {
                                            userDetails.setImage(userDetailsResponse.getProfilePicture().getDisplayImageUrl().getElements().get(0).getIdentifiers().get(0).getIdentifier());
                                        }
                                        if (scopeValue.equals(KeyUtils.ONLY_EMAIL_SCOPE) || scopeValue.equals(KeyUtils.BOTH_EMAIL_USERDETAILS_SCOPE_VALUE)) {
                                            callUserEmailApi(accessToken);
                                        } else {
                                            Intent intent = new Intent();
                                            intent.putExtra(KEY_LINKEDIN_CONTENT, userDetails);
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        }
                                    }
                                } else {
                                    setResult(Activity.RESULT_CANCELED, new Intent());
                                    finish();
                                }
                            } else {
                                setResult(Activity.RESULT_CANCELED, new Intent());
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserListResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    /**
     * Get Login user email
     */
    private void callUserEmailApi(String accessToken) {
        WebApiClient.webApiTwitchAccessToken(ApiParam.BASE_URL_USER)
                .callUserEmailApi(BEARER + " " + accessToken)
                .enqueue(new Callback<UserEmailResponse>() {
                    @Override
                    public void onResponse(Call<UserEmailResponse> call, Response<UserEmailResponse> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    UserEmailResponse userDetailsResponse = response.body();
                                    if (userDetailsResponse != null) {
                                        Intent intent = new Intent();
                                        if (userDetailsResponse.getElements().get(0).getHandleData().getEmailAddress() != null) {
                                            userDetails.setEmail(userDetailsResponse.getElements().get(0).getHandleData().getEmailAddress());
                                        }
                                        intent.putExtra(KEY_LINKEDIN_CONTENT, userDetails);
                                        setResult(Activity.RESULT_OK, intent);
                                        finish();
                                    } else {
                                        setResult(Activity.RESULT_CANCELED, new Intent());
                                        finish();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserEmailResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

    }
}

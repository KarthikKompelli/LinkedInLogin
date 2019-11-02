package com.karthikkompelli.linkedinlogin.builder;

import android.content.Context;
import android.content.Intent;

import com.karthikkompelli.linkedinlogin.presentation.MiLinkedInActivity;
import com.karthikkompelli.linkedinlogin.utils.KeyUtils;

public class LinkedInBuilder {

    public static class Builder {
        private Context context;
        private String clientId = null;
        private String clientSecret = null;
        private String redirectUri = null;
        private String stateValue = null;
        private String scopeValue = null;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder setStateValue(String stateValue) {
            this.stateValue = stateValue;
            return this;
        }

        public Builder setScopeValue(String scopeValue) {
            this.scopeValue = scopeValue;
            return this;
        }

        public Intent build() {
            Intent intent = new Intent(context, MiLinkedInActivity.class);
            if (clientId != null) {
                intent.putExtra(KeyUtils.CLIENT_ID, clientId);
            }
            if (clientSecret != null) {
                intent.putExtra(KeyUtils.CLIENT_SECRET, clientSecret);
            }
            if (redirectUri != null) {
                intent.putExtra(KeyUtils.REDIRECT_URI, redirectUri);
            }
            if (stateValue != null) {
                intent.putExtra(KeyUtils.STATE_VALUE, stateValue);
            }
            if (scopeValue != null) {
                intent.putExtra(KeyUtils.SCOPE_VALUE_KEY, scopeValue);
            }
            return intent;
        }
    }
}

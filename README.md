# LinkedInLogin
Easy to use LinkedIn Login Android - Java

### Key Features

* Integrate LinkedIn Login

# Usage

* Dependencies

    Step 1. Add the JitPack repository to your build file
    
    Add it in your root build.gradle at the end of repositories:

    ```groovy
	    allprojects {
		    repositories {
			    ...
			    maven { url 'https://jitpack.io' }
		    }
	    }
    ``` 

    Step 2. Add the dependency
    
    Add it in your app module build.gradle:
    
    ```groovy
        dependencies {
            ...
            implementation 'com.github.KarthikKompelli:LinkedInLogin:0.1.0'
        }
    ``` 
       
* Implementation

    Step 1.Call MiLinkedinActivity from your activity class
    
    ```java
    Intent intent = new LinkedInBuilder.Builder(this)
                .setClientId(getString(R.string.client_id))//CLIENT_ID
                .setClientSecret(getString(R.string.client_secret))//CLIENT_SECRET
                .setRedirectUri(getString(R.string.redirect_uri))//REDIRECT_URI
                .setStateValue(getString(R.string.state_value))//STATE_VALUE
                .setScopeValue(KeyUtils.BOTH_EMAIL_USERDETAILS_SCOPE_VALUE)//PASS_SCOPE_VALUE_HERE
                .build();
    startActivityForResult(intent, KeyUtils.REQUEST_CODE);
    //For get only Email address pass scope value -->KeyUtils.ONLY_EMAIL_SCOPE
    //For get only user information pass scope value -->KeyUtils.ONLY_PROFILE_SCOPE
    //For get both email and user information pass scope value -->KeyUtils.BOTH_EMAIL_USERDETAILS_SCOPE_VALUE	   
    ```
    Step 2.Get data from onActivityResult Method
    
    ```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case KeyUtils.REQUEST_CODE: {
                    LinkedInUserDetails linkedInUser = data.getParcelableExtra(KeyUtils.KEY_LINKEDIN_CONTENT);
                    if (linkedInUser != null) {
                        // use linkedinUser information
                    } else {
                        // handle the error
                    }
                }
                break;
            }
        } else {
            //Login failed handle error
        }
    }
   ```
### Requirments   

* Android X
* Min sdk >=14

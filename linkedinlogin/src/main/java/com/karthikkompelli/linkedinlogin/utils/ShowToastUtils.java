package com.karthikkompelli.linkedinlogin.utils;

import android.content.Context;
import android.widget.Toast;

public class ShowToastUtils {

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}

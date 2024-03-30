package com.startbutton.sdk.interfaces;

import com.startbutton.sdk.models.APIResponse;

public interface OnActivityResultListener {
    void onActivityResult(int resultCode, APIResponse data);
}

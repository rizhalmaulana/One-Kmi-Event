package com.rizal.onekmievent.utils;

import com.rizal.onekmievent.model.NewsResponses;

public interface NewsSourceCallback {
    void onSuccess(NewsResponses newsResponses);
    void onFailed(String error);
}

package com.example.maamagic.interfaces;

import com.example.maamagic.models.OrderModel;
import com.example.maamagic.models.User;

public interface UserFetchListener {
    void onUserFetched(User user);
    void onUserFetchedError(String error);

}

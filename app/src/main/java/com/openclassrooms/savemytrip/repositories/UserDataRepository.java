package com.openclassrooms.savemytrip.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.savemytrip.database.dao.UserDao;
import com.openclassrooms.savemytrip.models.User;

public class UserDataRepository {

    private final UserDao mUserDao;

    public UserDataRepository(UserDao userDao) {
        mUserDao = userDao;
    }

    // --- GET USER ---
    public LiveData<User> getUser(long userId) {
        return mUserDao.getUser(userId);
    }

}

package com.openclassrooms.savemytrip.injections;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.savemytrip.database.SaveMyTripDatabase;
import com.openclassrooms.savemytrip.repositories.ItemDataRepository;
import com.openclassrooms.savemytrip.repositories.UserDataRepository;
import com.openclassrooms.savemytrip.todolist.ItemViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ItemDataRepository mItemDataRepository;
    private final UserDataRepository mUserDataRepository;
    private static Executor mExecutor;
    private static ViewModelFactory sViewModelFactory;

    public static ViewModelFactory getInstance(Context context) {
        if (sViewModelFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sViewModelFactory == null) {
                    sViewModelFactory = new ViewModelFactory(context);
                }
            }
        }
        return sViewModelFactory;
    }

    private ViewModelFactory(Context context) {

        SaveMyTripDatabase database = SaveMyTripDatabase.getInstance(context);
        mItemDataRepository = new ItemDataRepository(database.itemDao());
        mUserDataRepository = new UserDataRepository(database.userDao());
        mExecutor = Executors.newSingleThreadExecutor();

    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ItemViewModel.class)) {
            return (T) new ItemViewModel(mItemDataRepository, mUserDataRepository, mExecutor);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}

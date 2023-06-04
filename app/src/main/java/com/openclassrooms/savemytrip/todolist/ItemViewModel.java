package com.openclassrooms.savemytrip.todolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.savemytrip.models.Item;
import com.openclassrooms.savemytrip.models.User;
import com.openclassrooms.savemytrip.repositories.ItemDataRepository;
import com.openclassrooms.savemytrip.repositories.UserDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ItemViewModel extends ViewModel {

    // REPOSITORIES
    private final ItemDataRepository mItemDataRepository;
    private final UserDataRepository mUserDataRepository;
    private final Executor mExecutor;

    // DATA
    // @Nullable
    private LiveData<User> currentUser;

    public ItemViewModel(ItemDataRepository itemDataRepository, UserDataRepository
            userDataRepository, Executor executor) {

        mItemDataRepository = itemDataRepository;
        mUserDataRepository = userDataRepository;
        mExecutor = executor;

    }

    public void init(long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = mUserDataRepository.getUser(userId);
    }

    // -------------------
    // FOR USER
    // -------------------
    public LiveData<User> getUser() {
        return this.currentUser;
    }

    // -------------------
    // FOR ITEM
    // -------------------
    public LiveData<List<Item>> getItems(long userId) {
        return mItemDataRepository.getItems(userId);
    }

    public void createItem(String text, int category, long userId) {
        mExecutor.execute(() -> {
            mItemDataRepository.createItem(new Item(text, category, userId));
        });
    }

    public void deleteItem(long itemId) {
        mExecutor.execute(() -> {
            mItemDataRepository.deleteItem(itemId);
        });
    }

    public void updateItem(Item item) {
        mExecutor.execute(() -> mItemDataRepository.updateItem(item));
    }

}

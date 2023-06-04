package com.openclassrooms.savemytrip.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.savemytrip.database.dao.ItemDao;
import com.openclassrooms.savemytrip.models.Item;

import java.util.List;

public class ItemDataRepository {

    private final ItemDao mItemDao;

    public ItemDataRepository(ItemDao itemDao) {
        mItemDao = itemDao;
    }

    // --- GET ---
    public LiveData<List<Item>> getItems(long userId) {
        return mItemDao.getItems(userId);
    }

    // --- CREATE ---
    public void createItem(Item item) {
        mItemDao.insertItem(item);
    }

    // --- DELETE ---
    public void deleteItem(long itemId) {
        mItemDao.deleteItem(itemId);
    }

    // --- UPDATE ---
    public void updateItem(Item item) {
        mItemDao.updateItem(item);
    }

}

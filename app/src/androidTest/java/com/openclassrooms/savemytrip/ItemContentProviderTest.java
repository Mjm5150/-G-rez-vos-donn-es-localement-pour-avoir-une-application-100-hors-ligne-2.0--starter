package com.openclassrooms.savemytrip;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.junit.Assert.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.savemytrip.database.SaveMyTripDatabase;
import com.openclassrooms.savemytrip.provider.ItemContentProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class ItemContentProviderTest {

    // fOR DATA
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static long USER_ID = 1;

    @Before
    public void setUp() {

        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                SaveMyTripDatabase.class).allowMainThreadQueries().build();

        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext()
                .getContentResolver();

    }

    @Test
    public void getItemsWhenNoItemInserted() {

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ItemContentProvider
                .URI_ITEM, USER_ID), null, null, null, null);

        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();

    }

    @Test
    public void insertAndGetItem() {

        // BEFORE : Adding demo item
        final Uri userUri = mContentResolver.insert(ItemContentProvider.URI_ITEM, generateItem());

        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ItemContentProvider
                .URI_ITEM, USER_ID), null, null, null, null);

        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("text")), is("Visite cet " +
                "endroit de rêve !"));

    }

    // ---

    private ContentValues generateItem() {

        final ContentValues values = new ContentValues();
        values.put("text", "Visite cet endroit de rêve !");
        values.put("category", "0");
        values.put("isSelected", "false");
        values.put("userId", "1");
        return values;

    }

}

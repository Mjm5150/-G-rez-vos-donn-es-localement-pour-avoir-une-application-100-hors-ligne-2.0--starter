package com.openclassrooms.savemytrip.todolist;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.savemytrip.R;
import com.openclassrooms.savemytrip.databinding.ActivityTodoListBinding;
import com.openclassrooms.savemytrip.injections.ViewModelFactory;
import com.openclassrooms.savemytrip.models.Item;
import com.openclassrooms.savemytrip.models.User;

import java.util.List;


public class TodoListActivity extends AppCompatActivity implements ItemAdapter.Listener {
    private ActivityTodoListBinding binding;

    // 1 - FOR DATA
    private ItemViewModel mItemViewModel;
    private ItemAdapter mItemAdapter;
    private static final int USER_ID = 1; // Declaration en static à des fins de test

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityTodoListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 8 - Configure view (with recyclerview) & ViewModel
        configureViewModel();

        initView();

        // 9 - Get current user and items from Database
        getCurrentUser();

        getItems();

    }

    // -------------------
    // UI
    // -------------------

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        configureSpinner();
        // 7 - Create item after user clicked on button
        binding.todoListActivityButtonAdd.setOnClickListener(view -> {

            createItem();

        });
        // 4 - Configure RecyclerView
        configureRecyclerView();
    }

    // 4 - Configure RecyclerView
    private void configureRecyclerView() {

        this.mItemAdapter = new ItemAdapter(this);
        binding.todoListActivityRecyclerView.setAdapter(this.mItemAdapter);
        binding.todoListActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    // 5 - Update view (username & picture)
    private void updateView(User user) {

        if (user == null) return;

        binding.todoListActivityHeaderProfileText.setText(user.getUsername());

        Glide.with(this).load(user.getUrlPicture()).apply(RequestOptions.circleCropTransform())
                .into(binding.todoListActivityHeaderProfileImage);

    }

    // 6 - Update the list of items
    private void updateItemsList(List<Item> items) {

        this.mItemAdapter.updateData(items);

    }

    private void configureSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.todoListActivitySpinner.setAdapter(adapter);
    }


    @Override
    public void onClickDeleteButton(Item item) {

        // 7 - Delete item after user clicked on button
        deleteItem(item);

    }

    @Override
    public void onItemClick(Item item) {

        // 7 - Update item after user clicked on it
        updateItem(item);

    }

    // ---------------
    // DATA
    // ---------------

    // 2 - Configuring ViewModel
    private void configureViewModel() {

        this.mItemViewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance(this)).get(ItemViewModel.class);
        this.mItemViewModel.init(USER_ID);

    }

    // 3 - Get current user
    private void getCurrentUser() {

        LiveData<User> userLiveData = mItemViewModel.getUser();

        if (userLiveData != null) {

            // expression lambda, référence de méthode en utilisant l'opérateur ::
            // nomClasse::nomMéthode
            userLiveData.observe(this, this::updateView);

        }

    }

    // 3 - Get all items for a user
    private void getItems() {

        this.mItemViewModel.getItems(USER_ID).observe(this, this::updateItemsList);

    }

    // 3 - Create new item
    private void createItem() {

        mItemViewModel.createItem(binding.todoListActivityEditText.getText().toString(),
                binding.todoListActivitySpinner.getSelectedItemPosition(), USER_ID);

        binding.todoListActivityEditText.setText("");

    }

    // 3 - Delete an item
    private void deleteItem(Item item) {

        this.mItemViewModel.deleteItem(item.getId());

    }

    // 3 - Update an item (selected or not)
    private void updateItem(Item item) {

        item.setSelected(!item.isSelected());

        this.mItemViewModel.updateItem(item);

    }


}

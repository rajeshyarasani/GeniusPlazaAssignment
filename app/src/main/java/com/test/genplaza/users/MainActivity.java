package com.test.genplaza.users;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;

import com.test.genplaza.users.databinding.MainActivityBinding;
import com.test.genplaza.users.ui.main.ImageListAdapter;
import com.test.genplaza.users.ui.main.MainViewModel;
import com.test.genplaza.users.ui.main.PaginationGridLayoutManager;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

    private MainActivityBinding mDataBinding;

    private void initializeData() {
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mainViewModel = new MainViewModel(this);
        mDataBinding.setViewModel(mainViewModel);

        setSupportActionBar(mDataBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Users List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mainViewModel.loadUsers(1);
        mainViewModel.imageDataList.addOnPropertyChangedCallback(imageDataListChangeListener);
        setImageListAdapter();

    }

    private android.databinding.Observable.OnPropertyChangedCallback imageDataListChangeListener = new android.databinding.Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(android.databinding.Observable observable, int i) {
            if (imagesAdapter != null) {
                imagesAdapter.refreshAdapter(mainViewModel.imageDataList.get());
            }
        }
    };
    private boolean isLoading = false;
    private boolean isLastPage = false;
    ImageListAdapter imagesAdapter = null;

    private void setImageListAdapter() {
        PaginationGridLayoutManager linearLayoutManager = new PaginationGridLayoutManager(this, 3);

        RecyclerView recyclerView = mDataBinding.rvUsers;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        imagesAdapter = new ImageListAdapter(this, mainViewModel.imageDataList.get());
        recyclerView.setAdapter(imagesAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Disable user interaction while ProgressBar is visible
     */
    public void setUntouchableWindowFlag() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * enable user interaction while ProgressBar is visible
     */
    public void clearWindowFlags() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        Utils.hideKeyboard(this);
    }
}

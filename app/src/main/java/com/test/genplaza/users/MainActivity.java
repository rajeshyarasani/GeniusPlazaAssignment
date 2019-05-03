package com.test.genplaza.users;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;

import com.test.genplaza.users.databinding.MainActivityBinding;
import com.test.genplaza.users.ui.main.ImageListAdapter;
import com.test.genplaza.users.ui.main.MainViewModel;
import com.test.genplaza.users.ui.main.PaginationGridLayoutManager;
import com.test.genplaza.users.ui.main.PaginationLinearLayoutManager;
import com.test.genplaza.users.ui.main.PaginationScrollListener;
import com.test.genplaza.users.ui.main.UserListAdapter;
import com.test.genplaza.users.ui.network.UsersResponse;

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
        mainViewModel.loadUsers(true);
        mainViewModel.imageDataList.addOnPropertyChangedCallback(imageDataListChangeListener);
        setImageListAdapter();

    }

    private android.databinding.Observable.OnPropertyChangedCallback imageDataListChangeListener = new android.databinding.Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(android.databinding.Observable observable, int i) {
            if (imagesAdapter != null) {
//                imagesAdapter.refreshAdapter(mainViewModel.imageDataList.get());
UsersResponse usersResponse = mainViewModel.imageDataList.get();
                isLastPage = usersResponse.getPage() * usersResponse.getPerPage() > usersResponse.getTotal();
//                if (!isLastPage)
//                    lastKey = usersResponse.getPage();

                if (usersResponse.getPage()==1) {
                    imagesAdapter.addAll(usersResponse.getUserList());

                    if (!isLastPage) imagesAdapter.addLoadingFooter();
                }else {
                    imagesAdapter.removeLoadingFooter();
                    isLoading = false;

                    imagesAdapter.addAll(usersResponse.getUserList());

                    if (!isLastPage)
                        imagesAdapter.addLoadingFooter();
                }
            }
        }
    };
    private boolean isLoading = false;
    private boolean isLastPage = false;
    UserListAdapter imagesAdapter = null;

    private void setImageListAdapter() {
        RecyclerView recyclerView = mDataBinding.rvUsers;

//        PaginationGridLayoutManager linearLayoutManager = new PaginationGridLayoutManager(this, 3);
        PaginationLinearLayoutManager linearLayoutManager = new PaginationLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        imagesAdapter = new UserListAdapter(this, mainViewModel.imageDataList.get().getUserList());
        recyclerView.setAdapter(imagesAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                mainViewModel.loadUsers(false);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
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

    public void launchAddUserActivity() {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
}

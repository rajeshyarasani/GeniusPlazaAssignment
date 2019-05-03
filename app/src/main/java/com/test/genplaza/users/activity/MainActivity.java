package com.test.genplaza.users.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;

import com.test.genplaza.users.R;
import com.test.genplaza.users.databinding.MainActivityBinding;
import com.test.genplaza.users.viewmodel.MainViewModel;
import com.test.genplaza.users.helpers.PaginationLinearLayoutManager;
import com.test.genplaza.users.helpers.PaginationScrollListener;
import com.test.genplaza.users.adapter.UserListAdapter;
import com.test.genplaza.users.network.model.UsersResponse;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel = null;
    private UserListAdapter userListAdapter = null;
    private MainActivityBinding mDataBinding;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    /**
     * This is the observable callback when user response changes the api call
     */
    private android.databinding.Observable.OnPropertyChangedCallback userListChangeListener = new android.databinding.Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(android.databinding.Observable observable, int i) {
            if (userListAdapter != null) {
                UsersResponse usersResponse = mainViewModel.usersResponseObservableField.get();
                isLastPage = usersResponse.getPage() * usersResponse.getPerPage() > usersResponse.getTotal();

                if (usersResponse.getPage() == 1) {
                    userListAdapter.addAll(usersResponse.getUserList());

                    if (!isLastPage) userListAdapter.addLoadingFooter();
                } else {
                    userListAdapter.removeLoadingFooter();
                    isLoading = false;

                    userListAdapter.addAll(usersResponse.getUserList());

                    if (!isLastPage)
                        userListAdapter.addLoadingFooter();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

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
        mainViewModel.usersResponseObservableField.addOnPropertyChangedCallback(userListChangeListener);
        setUserListAdapter();
    }

    /**
     * Set adapter to display the list of users
     */
    private void setUserListAdapter() {
        RecyclerView recyclerView = mDataBinding.rvUsers;
        PaginationLinearLayoutManager linearLayoutManager = new PaginationLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        userListAdapter = new UserListAdapter(this, mainViewModel.usersResponseObservableField.get().getUserList());
        recyclerView.setAdapter(userListAdapter);
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
    }

    public void launchAddUserActivity() {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
}

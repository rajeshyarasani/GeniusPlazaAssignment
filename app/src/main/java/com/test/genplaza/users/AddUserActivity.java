package com.test.genplaza.users;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.test.genplaza.users.databinding.AddUserScreenBinding;
import com.test.genplaza.users.ui.main.AddUserVM;
import com.test.genplaza.users.ui.main.Utils;

public class AddUserActivity extends AppCompatActivity {
    private AddUserVM addUserVM = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

    private AddUserScreenBinding mDataBinding;

    private void initializeData() {
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.add_user_screen);
        addUserVM = new AddUserVM(this);
        mDataBinding.setViewModel(addUserVM);

        setSupportActionBar(mDataBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add User");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
        Utils.hideKeyboard(this);
    }

}

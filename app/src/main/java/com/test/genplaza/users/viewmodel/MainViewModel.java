package com.test.genplaza.users.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.test.genplaza.users.activity.MainActivity;
import com.test.genplaza.users.network.ApiManager;
import com.test.genplaza.users.network.model.UsersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel class for main activity
 */
public class MainViewModel extends BaseObservable {

    public ObservableBoolean showLoader = new ObservableBoolean(false);
    public ObservableBoolean noUsers = new ObservableBoolean(false);

    public ObservableField<UsersResponse> usersResponseObservableField = new ObservableField<UsersResponse>(new UsersResponse());

    private MainActivity mainActivity;
    private int pageNumber = 1;

    public MainViewModel(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void addUser() {
        mainActivity.launchAddUserActivity();
    }

    public void loadUsers(boolean isFirstTime) {
        if (isFirstTime) {
            showLoader.set(true);
        }
        ApiManager apiManager = ApiManager.getInstance();
        apiManager.fetchUserList(pageNumber, new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                onSuccess(call, response);
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                onApiFailure(call, t);
            }
        });
    }

    private void onApiFailure(Call<UsersResponse> call, Throwable t) {
        mainActivity.clearWindowFlags();
        showLoader.set(false);
        noUsers.set(true);
    }

    private void onSuccess(Call<UsersResponse> call, Response<UsersResponse> response) {
        if (response == null || response.body() == null || (response != null && response.errorBody() != null)) {
            onApiFailure(call, null);
            return;
        }
        mainActivity.clearWindowFlags();
        showLoader.set(false);
        noUsers.set(false);
        UsersResponse usersResponse = response.body();
        if (usersResponse == null || usersResponse.getUserList() == null || usersResponse.getUserList().isEmpty()) {
            noUsers.set(true);
        }
        usersResponseObservableField.set(usersResponse);
        pageNumber = usersResponse.getPage() + 1;
    }

}

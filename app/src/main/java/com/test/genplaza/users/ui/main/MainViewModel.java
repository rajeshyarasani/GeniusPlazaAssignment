package com.test.genplaza.users.ui.main;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.test.genplaza.users.MainActivity;
import com.test.genplaza.users.ui.network.ApiManager;
import com.test.genplaza.users.ui.network.User;
import com.test.genplaza.users.ui.network.UsersResponse;
import com.test.genplaza.users.ui.network.ApiClientListener;
import com.test.genplaza.users.ui.network.NetworkModule;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ViewModel class for main activity
 */
public class MainViewModel extends BaseObservable {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private static final int STATUS_SUCCESS = 200;

    public ObservableBoolean showLoader = new ObservableBoolean(false);
    public ObservableBoolean noImages = new ObservableBoolean(false);

    //    public ObservableField<String> searchData = new ObservableField<>("");
    public ObservableField<UsersResponse> imageDataList = new ObservableField<UsersResponse>(new UsersResponse());

    private MainActivity mainActivity;

    public MainViewModel(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private int pageNumber = 1;

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
        noImages.set(true);
    }

    private void onSuccess(Call<UsersResponse> call, Response<UsersResponse> response) {
        if (response == null || response.body() == null || (response != null && response.errorBody() != null)) {
            onApiFailure(call, null);
            return;
        }
        mainActivity.clearWindowFlags();
        showLoader.set(false);
        noImages.set(false);
        UsersResponse usersResponse = response.body();
        if (usersResponse == null || usersResponse.getUserList() == null || usersResponse.getUserList().isEmpty()) {
            noImages.set(true);
        }
        imageDataList.set(usersResponse);
        pageNumber = usersResponse.getPage()+1;
    }

    /**
     * After edit text changed
     *
     * @param s
     */
//    public void afterTextChanged(Editable s) {
//        searchData.set(s.toString());
//    }
}

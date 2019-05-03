package com.test.genplaza.users.ui.main;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
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
    public ObservableField<List<User>> imageDataList = new ObservableField<List<User>>(new ArrayList<User>());

    private MainActivity mainActivity;

    public MainViewModel(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void loadUsers(int pageNumber) {
        ApiManager apiManager = ApiManager.getInstance();
        apiManager.createUser(pageNumber, new Callback<UsersResponse>() {
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
        imageDataList.set(usersResponse.getUserList());
    }

    /**
     * Load images from api call
     */
    public void loadUsersOnApiCall() {
        Log.d(TAG, "on search click");
        ApiClientListener apiClientListener = NetworkModule.getRetrofitInstance().create(ApiClientListener.class);
//        String searchData = this.searchData.get();
//        Log.d(TAG, "searchData is " + searchData);
//        if (searchData == null || searchData.isEmpty()) {
//            return;
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainActivity.setUntouchableWindowFlag();
        Call<UsersResponse> imageSearchResponse = apiClientListener.getImageSearchResponse(1);
        showLoader.set(true);
        imageSearchResponse.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (response == null || response.body() == null || (response != null && response.errorBody() != null)) {
                    onFailure(call, null);
                    return;
                }
                mainActivity.clearWindowFlags();
                showLoader.set(false);
                noImages.set(false);
                UsersResponse usersResponse = response.body();
                if (usersResponse == null || usersResponse.getUserList() == null || usersResponse.getUserList().isEmpty()) {
                    noImages.set(true);
                }
                imageDataList.set(usersResponse.getUserList());
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                mainActivity.clearWindowFlags();
                showLoader.set(false);
                noImages.set(true);
            }
        });
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

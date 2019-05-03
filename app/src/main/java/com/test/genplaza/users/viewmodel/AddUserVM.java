package com.test.genplaza.users.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.widget.Toast;

import com.test.genplaza.users.activity.AddUserActivity;
import com.test.genplaza.users.network.ApiManager;
import com.test.genplaza.users.network.model.NewUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel class for main activity
 */
public class AddUserVM extends BaseObservable {

    public ObservableBoolean showLoader = new ObservableBoolean(false);

    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> job = new ObservableField<>("");
    private AddUserActivity addUserActivity;

    public AddUserVM(AddUserActivity addUserActivity) {
        this.addUserActivity = addUserActivity;
    }

    public void fetchUser() {
        showLoader.set(true);
        addUserActivity.setUntouchableWindowFlag();
        ApiManager apiManager = ApiManager.getInstance();
        NewUser user = new NewUser(name.get(), job.get());
        apiManager.fetchUser(user, new Callback<NewUser>() {
            @Override
            public void onResponse(Call<NewUser> call, Response<NewUser> response) {
                onSuccess(call, response);
            }

            @Override
            public void onFailure(Call<NewUser> call, Throwable t) {
                onApiFailure(call, t);
            }
        });
    }

    private void onApiFailure(Call<NewUser> call, Throwable t) {
        Toast.makeText(addUserActivity,
                "Unable to createUser "
                , Toast.LENGTH_LONG).show();
        addUserActivity.clearWindowFlags();
        showLoader.set(false);
    }

    private void onSuccess(Call<NewUser> call, Response<NewUser> response) {
        if (response == null || response.body() == null || (response != null && response.errorBody() != null)) {
            onApiFailure(call, null);
            return;
        }
        addUserActivity.clearWindowFlags();
        showLoader.set(false);
        NewUser newUser = response.body();
        Toast.makeText(addUserActivity,
                String.format("Successfully created the User %s with job %s at %s with id %s",
                        newUser.getName(),
                        newUser.getJob(),
                        newUser.getCreatedAt(),
                        newUser.getId()),
                Toast.LENGTH_LONG)
                .show();
        addUserActivity.finish();
    }

    /**
     * After edit text changed
     *
     * @param s
     */
    public void afterNameTextChanged(Editable s) {
        name.set(s.toString());
    }

    public void afterJobTextChanged(Editable s) {
        job.set(s.toString());
    }
}

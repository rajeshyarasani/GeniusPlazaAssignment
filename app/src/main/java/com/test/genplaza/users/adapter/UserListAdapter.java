package com.test.genplaza.users.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.genplaza.users.R;
import com.test.genplaza.users.network.model.User;

import java.util.List;

/**
 * ImageListAdapter to display the grid images
 */
public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<User> userList;
    private Context context;
    private boolean isLoadingAdded = false;

    public UserListAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user, parent, false);

                viewHolder = new ViewHolder(view);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress_view, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        switch (getItemViewType(position)) {
            case ITEM:
                onBindUserViewHolder(holder, position);
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    public void onBindUserViewHolder(RecyclerView.ViewHolder vh, int position) {
        ViewHolder holder = (ViewHolder) vh;
        final String imgUrl;
        holder.userAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_default_image_thumbnail));
        if (userList == null || userList.isEmpty() || userList.get(position).getAvatar() == null
                || userList.get(position).getAvatar().isEmpty()) {
            holder.userAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_default_image_thumbnail));
            imgUrl = "";
        } else {
            imgUrl = userList.get(position).getAvatar();
            Picasso.with(context)
                    .load(imgUrl)
                    .resize(200, 200)
                    .placeholder(R.drawable.ic_default_image_thumbnail)
                    .into(holder.userAvatar);
        }

        holder.userName.setText(userList.get(position).getUserName());
    }


    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == userList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

     /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(User item) {
        userList.add(item);
        notifyItemInserted(userList.size() - 1);
    }

    public void addAll(List<User> mcList) {
        for (User mc : mcList) {
            add(mc);
        }
    }

    public void remove(User city) {
        int position = userList.indexOf(city);
        if (position > -1) {
            userList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        try {
            add(User.class.newInstance());
        } catch (Exception e) {

        }
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = userList.size() - 1;
        User item = getItem(position);

        if (item != null) {
            userList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public User getItem(int position) {
        return userList.get(position);
    }

    // -- Helper methods ended

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView userAvatar;
        private TextView userName;

        public ViewHolder(View view) {
            super(view);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            userName = itemView.findViewById(R.id.user_name);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
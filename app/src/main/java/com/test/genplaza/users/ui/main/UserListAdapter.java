package com.test.genplaza.users.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.genplaza.users.R;
import com.test.genplaza.users.ui.network.User;
import com.test.genplaza.users.ui.network.UsersResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageListAdapter to display the grid images
 */
public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_IMAGE_ID = "image_id";
    private List<User> userList;
    private Context context;
    private boolean isLoadingAdded = false;

    public UserListAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
    }

    private boolean isLastPage;
    private int lastKey;
//    public void refreshAdapter(UsersResponse usersResponse, boolean isFirst) {
//        isLastPage = usersResponse.getTotalPages() * usersResponse.getPerPage() > usersResponse.getTotal();
//        if (!isLastPage)
//            lastKey = usersResponse.getPage();
//
//        if (isFirst) {
//            addAll(usersResponse.getUserList());
//
//            if (!isLastPage) addLoadingFooter();
//        }else {
//            removeLoadingFooter();
//            isLoading = false;
//
//            addAll(usersResponse.getUserList());
//
//            if (!isLastPage)
//                addLoadingFooter();
//        }
//
//        this.userList.clear();
//        this.userList = usersResponse.getUserList();
//        notifyDataSetChanged();
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_image_grid, parent, false);

                viewHolder = new ViewHolder(view);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress_view, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
//        return new ViewHolder(view);
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
        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_default_image_thumbnail));
        if (userList == null || userList.isEmpty() || userList.get(position).getAvatar() == null
                || userList.get(position).getAvatar().isEmpty()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_default_image_thumbnail));
            imgUrl = "";
        } else {
            imgUrl = userList.get(position).getAvatar();
            Picasso.with(context)
                    .load(imgUrl)
                    .resize(200, 200)
                    .placeholder(R.drawable.ic_default_image_thumbnail)
                    .into(holder.imageView);
        }


        //start full screen image activity
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgUrl.isEmpty()) {
                    Toast.makeText(context, "Image cannot be viewed, thumbnail is not found", Toast.LENGTH_LONG).show();
                    return;
                }
//                Intent intent = new Intent(context, ImageViewerActivity.class);
//                intent.putExtra(KEY_IMAGE_URL, imgUrl);
//                intent.putExtra(KEY_IMAGE_ID, imgId);
//                context.startActivity(intent);
            }
        });

        holder.title.setText(userList.get(position).getUserName());
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

    /*Helper methods ended*/

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;

        public ViewHolder(View view) {
            super(view);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);

        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
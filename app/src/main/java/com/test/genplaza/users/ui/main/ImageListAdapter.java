package com.test.genplaza.users.ui.main;

import android.content.Context;
import android.content.Intent;
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
import com.test.genplaza.users.ui.network.NetworkModule;

import java.util.List;

/**
 * ImageListAdapter to display the grid images
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_IMAGE_ID = "image_id";
    private List<User> userList;
    private Context context;

    public ImageListAdapter(Context context, List<User> imageDataList) {
        this.userList = imageDataList;
        this.context = context;
    }

    public void refreshAdapter(List<User> userList) {
        this.userList.clear();
        this.userList = userList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String imgUrl;
        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_default_image_thumbnail));
        if (userList==null || userList.isEmpty() || userList.get(position).getAvatar() == null
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;

        public ViewHolder(View view) {
            super(view);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);

        }
    }
}
package com.test.genplaza.users.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.genplaza.users.R;

import java.util.List;

/**
 * Addapter class for image comments
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<String> commentList;
    private Context context;

    public CommentListAdapter(Context context, List<String> commentList) {
        this.commentList = commentList;
        this.context = context;
    }

    public void refreshAdapter(List<String> commentList) {
        this.commentList.clear();
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentListAdapter.ViewHolder holder, int position) {
        if (commentList.isEmpty() || commentList.get(position) == null || commentList.get(position).isEmpty()) {
            holder.commentTv.setText("");
        } else {
            holder.commentTv.setText(commentList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView commentTv;

        public ViewHolder(View view) {
            super(view);
            commentTv = itemView.findViewById(R.id.tv_comment);

        }
    }
}
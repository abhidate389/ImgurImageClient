package com.sample.imgurimageclient.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.PagedListAdapter;

import com.sample.imgurimageclient.R;
import com.sample.imgurimageclient.repository.models.ImageComment;
import com.sample.imgurimageclient.viewholder.ImageCommentViewHolder;

public class ImageCommentsAdapter extends PagedListAdapter<ImageComment, ImageCommentViewHolder> {

    private static final String TAG = ImageCommentsAdapter.class.getSimpleName();

    public ImageCommentsAdapter() {
        super(ImageComment.CALLBACK);
    }

    @Override
    public ImageCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comment_view_item, parent, false);
        return new ImageCommentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ImageCommentViewHolder holder, int position) {
        if (getItem(position) != null) {
             holder.bindTo(getItem(position));
        } else {
            Log.d(TAG,"Error binding");
        }
    }

}

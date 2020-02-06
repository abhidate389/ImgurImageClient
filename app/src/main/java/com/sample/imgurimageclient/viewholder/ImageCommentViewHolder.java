package com.sample.imgurimageclient.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.imgurimageclient.R;
import com.sample.imgurimageclient.repository.models.ImageComment;

public class ImageCommentViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    public ImageCommentViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.comment);
    }

    public void bindTo(ImageComment imageComment) {
        textView.setText(imageComment.getComment());
    }
}

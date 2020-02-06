package com.sample.imgurimageclient.repository.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comments")
public class ImageComment {

    @PrimaryKey(autoGenerate = true)
    private int commentId;

    @ColumnInfo(name = "image_id")
    private String imageId;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "comment")
    private String comment;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static final DiffUtil.ItemCallback<ImageComment> CALLBACK = new DiffUtil.ItemCallback<ImageComment>() {
        @Override
        public boolean areItemsTheSame(@NonNull ImageComment imageComment1, @NonNull ImageComment imageComment2) {
            return imageComment1.commentId == imageComment2.commentId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ImageComment imageComment1, @NonNull ImageComment imageComment2) {
            return imageComment1.equals(imageComment2);
            //return true;
        }
    };
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        ImageComment imageComment = (ImageComment) obj;

        return imageComment.commentId == this.commentId && imageComment.imageUrl == this.imageUrl;
    }
}

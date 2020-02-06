package com.sample.imgurimageclient.repository.storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sample.imgurimageclient.repository.models.ImageComment;

import java.util.List;

@Dao
public interface ImageCommentsDao {


    @Query("SELECT * FROM comments where image_id = :imageId")
    List<ImageComment> loadComments(String imageId);

    /**
     * Insert a image in the database.
     *
     * @ image to be inserted.
     */
    @Insert
    void insertImageComments(ImageComment imageComment);

}

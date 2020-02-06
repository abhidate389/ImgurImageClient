package com.sample.imgurimageclient.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.PagedListAdapter;

import com.sample.imgurimageclient.R;
import com.sample.imgurimageclient.listerners.ItemClickListener;
import com.sample.imgurimageclient.repository.models.Image;
import com.sample.imgurimageclient.viewholder.ImageViewHolder;

public class ImagePageListAdapter extends PagedListAdapter<Image, ImageViewHolder> {

    private ItemClickListener itemClickListener;
    private static final String TAG = ImagePageListAdapter.class.getSimpleName();

    public ImagePageListAdapter(ItemClickListener itemClickListener) {
        super(Image.CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.image_view_item,parent,false);
        return new ImageViewHolder(view,itemClickListener);

    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        if(getItem(position) != null){
            holder.bindTo(getItem(position));
        }
        else{
            Log.d(TAG,"Error binding");
        }


    }
}

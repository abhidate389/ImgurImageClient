package com.sample.imgurimageclient.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.imgurimageclient.R;
import com.sample.imgurimageclient.listerners.ItemClickListener;
import com.sample.imgurimageclient.repository.models.Image;
import com.sample.imgurimageclient.utilities.Constants;
import com.squareup.picasso.Picasso;

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView textView;
    private final ImageView imageView;
    private ItemClickListener itemClickListener;
    private Image image;

    public ImageViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView1);
        imageView = itemView.findViewById(R.id.imageView);
        this.itemClickListener = itemClickListener;
        itemView.setOnClickListener(this);

    }

    /**
     *
     * @param image
     * bind image to image view and title to textview
     */
    public void bindTo(Image image) {
        this.image = image;
        textView.setText(image.getTitle());
        if(image.getImageUrl() != null) {
            Picasso.get().load(image.getImageUrl()).into(imageView);
        }
        else{
            Picasso.get().load(Constants.IMAGE_URL + image.getImageId()+ ".png").into(imageView);
        }
    }
    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(image); // call the onClick in the OnItemClickListener
        }
    }
}

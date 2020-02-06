package com.sample.imgurimageclient.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.imgurimageclient.R;
import com.sample.imgurimageclient.adapter.ImageCommentsAdapter;
import com.sample.imgurimageclient.databinding.FragmentDetailsBinding;
import com.sample.imgurimageclient.repository.models.Image;
import com.sample.imgurimageclient.repository.models.ImageComment;
import com.sample.imgurimageclient.utilities.Constants;
import com.sample.imgurimageclient.viewmodel.ImageDetailViewModel;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDetailFragment extends Fragment {

    private static final String TAG = ImageDetailFragment.class.getSimpleName();
    private ImageDetailViewModel viewModel;
    private RecyclerView recyclerView;
    private EditText editText;
    private Button submit;
    private Image image;
    private ImageCommentsAdapter pageListAdapter;
    private Toolbar toolbar;
    public ImageDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(ImageDetailViewModel.class);
        View view = binding.getRoot();
        viewModel.getImage().observe(this, binding::setImage);

        recyclerView = view.findViewById(R.id.commentRecycleView);
        submit = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.editText);
        toolbar = view.findViewById(R.id.toolbar);
        image = viewModel.getImage().getValue();

        pageListAdapter = new ImageCommentsAdapter();
        toolbar.setTitle(viewModel.getImage().getValue().getTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        displayComments();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    ImageComment imageComment = new ImageComment();
                    imageComment.setComment(editText.getText().toString());
                    editText.setText("");
                    Image image = viewModel.getImage().getValue();
                    imageComment.setImageId(image.getImageId());
                    imageComment.setImageUrl(image.getImageUrl());

                    viewModel.addImage(imageComment);
                    viewModel.getComments(image.getImageId()).observe(getActivity(), pageListAdapter::submitList);
                    pageListAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    /**
     * adding layout for showing comments
     */
    public void displayComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewModel.getComments(image.getImageId()).observe(this, pageListAdapter::submitList);
        recyclerView.setAdapter(pageListAdapter);
    }

    /**
     *
     * @param imageView
     * @param image
     * Binding image to image src
     */
    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView imageView, Image image) {
        if(image.getImageUrl() != null) {
            Picasso.get().load(image.getImageUrl()).into(imageView);
        }
        else{
            //image url null
            Log.d(TAG,image.getImageId());
            Picasso.get().load(Constants.IMAGE_URL + image.getImageId()+ ".jpg").into(imageView);
        }

    }

}

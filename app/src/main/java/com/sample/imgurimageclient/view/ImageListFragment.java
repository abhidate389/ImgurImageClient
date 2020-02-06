package com.sample.imgurimageclient.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sample.imgurimageclient.R;
import com.sample.imgurimageclient.adapter.ImagePageListAdapter;
import com.sample.imgurimageclient.databinding.FragmentImagesBinding;
import com.sample.imgurimageclient.di.MyApplication;
import com.sample.imgurimageclient.listerners.ItemClickListener;
import com.sample.imgurimageclient.repository.models.Image;
import com.sample.imgurimageclient.utilities.Constants;
import com.sample.imgurimageclient.viewmodel.ImageDetailViewModel;
import com.sample.imgurimageclient.viewmodel.ImageListViewState;
import com.sample.imgurimageclient.viewmodel.ImageViewModel;
import com.sample.imgurimageclient.viewmodel.ImageViewModelFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends Fragment implements ItemClickListener{

    private static final String TAG = ImageListFragment.class.getSimpleName();

    protected ImageViewModel viewModel;
    protected ImageDetailViewModel detailsViewModel;
    private String searchQuery;
    //use to inject image model factorycat
    @Inject
    ImageViewModelFactory imageViewModelFactory;


    FragmentImagesBinding binding;

    private Disposable disposable;

    public ImageListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getAppComponent().doInjection(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_images,container,false);
        View view = binding.getRoot();

        /**
         * If savedInstanceState is not null means its configuration change so get data
         * from savedInstanceState and add it to search view
         */

        if(savedInstanceState != null){
            searchQuery = savedInstanceState.getString(Constants.SEARCH_QUERY);
            binding.searchView.setQuery(searchQuery,false);
            binding.searchView.setIconified(true);
            binding.searchView.clearFocus();
            searchImage(searchQuery,false);
        }
        else {
            binding.searchView.setActivated(true);
            binding.searchView.setQueryHint(getString(R.string.search_view_text));
            binding.searchView.onActionViewExpanded();
        }

        viewModel = ViewModelProviders.of(getActivity(),imageViewModelFactory).get(ImageViewModel.class);
        observersRegisters();

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String str) {
                Log.d(TAG,"onQueryTextSubmit");
                searchQuery = str;
                searchImage(str,true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observeSearchView();

    }

    /**
     * Used to make search filed observable with 250ms debounce
     */
    private void observeSearchView() {

        disposable = RxSearchViewObservable.fromView(binding.searchView)
                .debounce(250, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty() && text.length() >= 3)
                .map(text -> text.toLowerCase().trim())
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( searchQuery ->
                {
                    searchImage(searchQuery,true);
                });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.SEARCH_QUERY,searchQuery);
    }

    /**
     *
     * @param query
     * @param isNewSearch
     * search method to call search
     */
    public void searchImage(final String query, boolean isNewSearch) {
        if(isNewSearch){
            viewModel.initNetWorkCall(query);
        }
        observersRegisters();
    }

    /**
     * Adding grid view and then calling image view model and image detail view
     * model
     */

    private void observersRegisters() {

        final ImagePageListAdapter pageListAdapter = new ImagePageListAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        binding.imageRecycleView.setLayoutManager(gridLayoutManager);

        if(viewModel != null && viewModel.getImages() != null){
            viewModel.getImages().observe(this, pageListAdapter::submitList);
            binding.imageRecycleView.setAdapter(pageListAdapter);


            viewModel.getProgressLoadStatus().observe(this, status ->{

                if(Objects.requireNonNull(status).equals(ImageListViewState.LOADING_STATE)){
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
                else if(Objects.requireNonNull(status).equals(ImageListViewState.SUCCESS_STATE)){
                    binding.progressBar.setVisibility(View.GONE);
                }
            });
            detailsViewModel = ViewModelProviders.of(getActivity()).get(ImageDetailViewModel.class);
        }


    }

    @Override
    public void OnItemClick(Image image) {
        detailsViewModel.getImage().postValue(image);
        if (!detailsViewModel.getImage().hasActiveObservers()) {
            // Create fragment and give it an argument specifying the article it should show
            ImageDetailFragment detailsFragment = new ImageDetailFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null) disposable.dispose();
    }

}

package com.hafsa.hafsastore.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hafsa.hafsastore.R;
import com.hafsa.hafsastore.customview.ScalingImageView;
import com.hafsa.hafsastore.models.Product;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullScreenProductFragment extends Fragment {

    private static final String TAG = "FullScreenProductFragment";

    //widgets
    private ScalingImageView mImageView;

    //vars
    public Product mProduct;

    public FullScreenProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            mProduct = bundle.getParcelable(getString(R.string.intent_product));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_screen_product, container, false);
        mImageView = view.findViewById(R.id.image);
        setProduct();
        return view;
    }

    private void setProduct(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(mProduct.getImage())
                .into(mImageView);


    }



}

package com.hafsa.hafsastore.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hafsa.hafsastore.R;
import com.hafsa.hafsastore.models.Product;
import com.hafsa.hafsastore.util.BigDecimalUtil;


public class ViewProductFragment extends Fragment {

    private static final String TAG = "ViewProductFragment";

    //widgets
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mPrice;

    //vars
    public Product mProduct;

    public ViewProductFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);
        mImageView = view.findViewById(R.id.image);
        mTitle = view.findViewById(R.id.title);
        mPrice = view.findViewById(R.id.price);

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

        mTitle.setText(mProduct.getTitle());
        mPrice.setText(BigDecimalUtil.getValue(mProduct.getPrice()));
    }
}

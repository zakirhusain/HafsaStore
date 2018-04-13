package com.hafsa.hafsastore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hafsa.hafsastore.R;
import com.hafsa.hafsastore.models.Product;
import com.hafsa.hafsastore.util.BigDecimalUtil;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CartRecyclerViewAd";

    //vars
    private ArrayList<Product> mProducts = new ArrayList<>();
    private Context mContext;


    public CartRecyclerViewAdapter(Context context, ArrayList<Product> products) {
        mContext = context;
        mProducts = products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(mProducts.get(position).getImage())
                .into(((ViewHolder)holder).image);

        ((ViewHolder)holder).title.setText(mProducts.get(position).getTitle());
        ((ViewHolder)holder).price.setText(BigDecimalUtil.getValue(mProducts.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, price;
        RelativeLayout parentView;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            parentView = itemView.findViewById(R.id.parent);
        }
    }

}

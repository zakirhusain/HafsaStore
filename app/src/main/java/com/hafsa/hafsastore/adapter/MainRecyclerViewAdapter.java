package com.hafsa.hafsastore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hafsa.hafsastore.R;
import com.hafsa.hafsastore.ViewProductActivity;
import com.hafsa.hafsastore.models.Product;

import java.util.ArrayList;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>  {

    private static final String TAG = "MainRecyclerViewAdapter";

    //vars
    private ArrayList<Product> mProducts = new ArrayList<>();
    private Context mContext;

    public MainRecyclerViewAdapter(Context context, ArrayList<Product> products) {
        mContext = context;
        mProducts = products;
        Log.i(TAG, "MainRecyclerViewAdapter: >>>>>>> " + mProducts);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_feed_list_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(mProducts.get(position).getImage())
                .into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewProductActivity.class);
                intent.putExtra(mContext.getString(R.string.intent_product), mProducts.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts != null ? mProducts.size() : 0;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CardView cardView;

        public MainViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }


}

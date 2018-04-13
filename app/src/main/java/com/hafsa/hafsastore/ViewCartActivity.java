package com.hafsa.hafsastore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;

import com.hafsa.hafsastore.adapter.CartRecyclerViewAdapter;
import com.hafsa.hafsastore.models.Product;
import com.hafsa.hafsastore.util.CartManger;

import java.util.ArrayList;

public class ViewCartActivity extends AppCompatActivity {

    private static final String TAG = "ViewCartActivity";

    //widgets
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    //vars
    CartRecyclerViewAdapter mAdapter;
    private ArrayList<Product> mProducts = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFab = findViewById(R.id.fab);


        getProducts();
        initRecyclerView();
    }

    private void getProducts(){
        CartManger cartManger = new CartManger(this);
        mProducts = cartManger.getCartItems();
    }

    private void initRecyclerView(){
        mAdapter = new CartRecyclerViewAdapter(this, mProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new CartScrollListener());

    }

    private void setFABVisibility(boolean isVisible){
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        if(isVisible){
            mFab.setAnimation(animFadeIn);
            mFab.setVisibility(View.VISIBLE);
        }
        else{
            mFab.setAnimation(animFadeOut);
            mFab.setVisibility(View.INVISIBLE);
        }
    }

    class CartScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                Log.d(TAG, "onScrollStateChanged: stopped ");
            }

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                Log.d(TAG, "onScrollStateChanged: fling");
            }

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                Log.d(TAG, "onScrollStateChanged: touched");
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (recyclerView.canScrollVertically(1)) {
                setFABVisibility(true);
            } else {
                setFABVisibility(false);
            }
        }
    }

}

package com.hafsa.hafsastore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hafsa.hafsastore.adapter.MainRecyclerViewAdapter;
import com.hafsa.hafsastore.models.Product;
import com.hafsa.hafsastore.resources.Products;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int NUM_COLUMNS = 2;

    //vars
    MainRecyclerViewAdapter mAdapter;
    private ArrayList<Product> mProducts = new ArrayList<>();

    //widgets
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);

        getProducts();
        initRecyclerView();
    }

    private void getProducts(){
        mProducts.addAll(Arrays.asList(Products.FEATURED_PRODUCTS));
    }

    private void initRecyclerView(){
        Log.i(TAG, mProducts + "");
        mAdapter = new MainRecyclerViewAdapter(this, mProducts);
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}

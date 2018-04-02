package com.hafsa.hafsastore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hafsa.hafsastore.adapter.ProductPagerAdapter;
import com.hafsa.hafsastore.fragments.ViewProductFragment;
import com.hafsa.hafsastore.models.Product;
import com.hafsa.hafsastore.resources.Products;

import java.util.ArrayList;

public class ViewProductActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "ViewProductActivity";

    //widgets
    private ViewPager mProductContainer;
    private TabLayout mTabLayout;
    //vars
    private Product mProduct;
    private ProductPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        mProductContainer = findViewById(R.id.image_container);
        mTabLayout = findViewById(R.id.tab_layout);
        mProductContainer.setOnTouchListener(this);
        getIncomingIntent();
        initPagerAdapter();
    }

    private void getIncomingIntent(){
        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.intent_product))){
            mProduct = intent.getParcelableExtra(getString(R.string.intent_product));
        }
    }

    private void initPagerAdapter() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        Products products = new Products();
        Product[] selectedProducts = products.PRODUCT_MAP.get(mProduct.getType());

        for (Product product : selectedProducts) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.intent_product), product);
            ViewProductFragment viewProductFragment = new ViewProductFragment();
            viewProductFragment.setArguments(bundle);
            fragments.add(viewProductFragment);
        }

        mPagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), fragments);
        mProductContainer.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mProductContainer, true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouch: Action was Down.");
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouch: Action was Move.");
                return false;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouch: Action was UP");
                return false;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "onTouch: Action was Cancel");
                return false;
            case MotionEvent.ACTION_OUTSIDE:
                Log.i(TAG, "onTouch: Movement occurred outside bounds of current screen element");
                return false;
        }
        return false;
    }
}

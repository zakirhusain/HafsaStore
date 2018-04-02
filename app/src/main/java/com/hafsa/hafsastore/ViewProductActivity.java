package com.hafsa.hafsastore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hafsa.hafsastore.adapter.ProductPagerAdapter;
import com.hafsa.hafsastore.fragments.ViewProductFragment;
import com.hafsa.hafsastore.models.Product;
import com.hafsa.hafsastore.resources.Products;

import java.util.ArrayList;

public class ViewProductActivity extends AppCompatActivity {

    private static final String TAG = "ViewProductActivity";

    //widgets
    private ViewPager mProductContainer;
    //vars
    private Product mProduct;
    private ProductPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        mProductContainer = findViewById(R.id.image_container);
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
    }
}

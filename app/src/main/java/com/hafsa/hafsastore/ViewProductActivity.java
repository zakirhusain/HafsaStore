package com.hafsa.hafsastore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hafsa.hafsastore.adapter.ProductPagerAdapter;
import com.hafsa.hafsastore.fragments.ViewProductFragment;
import com.hafsa.hafsastore.models.Product;
import com.hafsa.hafsastore.resources.Products;
import com.hafsa.hafsastore.util.CartManger;

import java.util.ArrayList;

public class ViewProductActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener {

    private static final String TAG = "ViewProductActivity";

    //widgets
    private ViewPager mProductContainer;
    private TabLayout mTabLayout;
    private RelativeLayout mAddToCart, mCart;
    //vars
    private Product mProduct;
    private ProductPagerAdapter mPagerAdapter;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        mProductContainer = findViewById(R.id.product_container);
        mTabLayout = findViewById(R.id.tab_layout);
        mAddToCart = findViewById(R.id.add_to_cart);
        mCart = findViewById(R.id.cart);

        mAddToCart.setOnClickListener(this);
        mCart.setOnClickListener(this);

        mProductContainer.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
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

    private void addCurrentItemToCard() {
        Product selectedProduct = ((ViewProductFragment)mPagerAdapter.getItem(mProductContainer.getCurrentItem())).mProduct;
        CartManger cartManger = new CartManger(this);
        cartManger.addItemToCart(selectedProduct);
        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
    }

    /*
    *  OnTouch
    * */

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v.getId() == R.id.product_container) {
            mGestureDetector.onTouchEvent(event);
        }
        /*
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
        }*/
        return false;
    }

    /*
    *
    *  GestureDetector
    *
    * */

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "onDown: Called");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "onShowPress: Called");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp: Called");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG, "onScroll: Called");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG, "onLongPress: Called");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "onFling: Called");
        return false;
    }


    /*
    *  OnDoubleTapListener
    *
    */

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i(TAG, "onSingleTapConfirmed: Called");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap: Called");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(TAG, "onDoubleTapEvent: Called");
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart : {
                // open cart activity
                break;
            }
            case R.id.add_to_cart: {
                addCurrentItemToCard();
                break;
            }
        }
    }
}

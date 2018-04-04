package com.hafsa.hafsastore;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hafsa.hafsastore.adapter.ProductPagerAdapter;
import com.hafsa.hafsastore.customview.MyDragShadowBuilder;
import com.hafsa.hafsastore.fragments.FullScreenProductFragment;
import com.hafsa.hafsastore.fragments.ViewProductFragment;
import com.hafsa.hafsastore.models.Product;
import com.hafsa.hafsastore.resources.Products;
import com.hafsa.hafsastore.util.CartManger;

import java.util.ArrayList;

public class ViewProductActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        View.OnDragListener {

    private static final String TAG = "ViewProductActivity";

    //widgets
    private ViewPager mProductContainer;
    private TabLayout mTabLayout;
    private RelativeLayout mAddToCart, mCart;
    private ImageView mCartIcon, mPlusIcon;
    //vars
    private Product mProduct;
    private ProductPagerAdapter mPagerAdapter;
    private GestureDetector mGestureDetector;
    private Rect mCartPositionRectangle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        mProductContainer = findViewById(R.id.product_container);
        mTabLayout = findViewById(R.id.tab_layout);
        mAddToCart = findViewById(R.id.add_to_cart);
        mCart = findViewById(R.id.cart);
        mPlusIcon = findViewById(R.id.plus_image);
        mCartIcon = findViewById(R.id.cart_image);

        mAddToCart.setOnClickListener(this);
        mCart.setOnClickListener(this);

        mProductContainer.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        getIncomingIntent();
        initPagerAdapter();
    }

    private void getCartPosition(){
        mCartPositionRectangle = new Rect();
        mCart.getGlobalVisibleRect(mCartPositionRectangle);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        mCartPositionRectangle.left = mCartPositionRectangle.left - Math.round((int)(width * 0.18));
        mCartPositionRectangle.top = 0;
        mCartPositionRectangle.right = width;
        mCartPositionRectangle.bottom = mCartPositionRectangle.bottom - Math.round((int)(width * 0.03));
    }

    private void setDragMode(boolean isDragging){
        if(isDragging){
            mCartIcon.setVisibility(View.INVISIBLE);
            mPlusIcon.setVisibility(View.VISIBLE);
        }
        else{
            mCartIcon.setVisibility(View.VISIBLE);
            mPlusIcon.setVisibility(View.INVISIBLE);
        }
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

    private void inflateFullScreenProductFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FullScreenProductFragment fragment = new FullScreenProductFragment();

        Bundle bundle = new Bundle();
        Product selectedProduct =((ViewProductFragment)mPagerAdapter.getItem(mProductContainer.getCurrentItem())).mProduct;
        bundle.putParcelable(getString(R.string.intent_product), selectedProduct);
        fragment.setArguments(bundle);

        // Enter Transition for New Fragment
        Fade enterFade = new Fade();
        enterFade.setStartDelay(1);
        enterFade.setDuration(300);
        fragment.setEnterTransition(enterFade);

        transaction.addToBackStack(getString(R.string.fragment_full_screen_product));
        transaction.replace(R.id.full_screen_container, fragment, getString(R.string.fragment_full_screen_product));
        transaction.commit();
    }

    /*
    *  OnTouch
    * */

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        getCartPosition();

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
        ViewProductFragment productFragment = ((ViewProductFragment)mPagerAdapter.getItem(mProductContainer.getCurrentItem()));
        View.DragShadowBuilder shadow = new MyDragShadowBuilder(((ViewProductFragment)productFragment).mImageView, productFragment.mProduct.getImage());
        ((ViewProductFragment)productFragment).mImageView.startDrag(null, shadow, null, 0);
        shadow.getView().setOnDragListener(this);
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
        inflateFullScreenProductFragment();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(TAG, "onDoubleTapEvent: Called");
        return false;
    }

    /*
    * OnDragListener
    *
    */
    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch(event.getAction()) {

            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(TAG, "onDrag: drag started.");
                setDragMode(true);

                return true;

            case DragEvent.ACTION_DRAG_ENTERED:

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                Point currentPosition = new Point(Math.round(event.getX()), Math.round(event.getY()));
                if (mCartPositionRectangle.contains(currentPosition.x, currentPosition.y)) {
                    mCart.setBackgroundColor(this.getResources().getColor(R.color.blue2));
                } else {
                    mCart.setBackgroundColor(this.getResources().getColor(R.color.blue1));
                }
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                return true;

            case DragEvent.ACTION_DROP:

                Log.d(TAG, "onDrag: dropped.");

                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(TAG, "onDrag: ended.");
                Drawable background = mCart.getBackground();
                if (background instanceof ColorDrawable) {
                    if (((ColorDrawable)background).getColor() == getResources().getColor(R.color.blue2)) {
                        addCurrentItemToCard();
                    }
                }
                mCart.setBackground(this.getResources().getDrawable(R.drawable.blue_onclick_dark));
                return true;

            // An unknown action type was received.
            default:
                Log.e(TAG,"Unknown action type received by OnStartDragListener.");
                break;
        }
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

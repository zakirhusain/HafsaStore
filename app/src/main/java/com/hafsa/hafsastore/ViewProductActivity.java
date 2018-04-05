package com.hafsa.hafsastore;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        View.OnClickListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnDragListener{

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        mProductContainer = findViewById(R.id.product_container);
        mTabLayout = findViewById(R.id.tab_layout);
        mAddToCart = findViewById(R.id.add_to_cart);
        mCart = findViewById(R.id.cart);
        mPlusIcon = findViewById(R.id.plus_image);
        mCartIcon = findViewById(R.id.cart_image);

        mProductContainer.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        mCart.setOnClickListener(this);
        mAddToCart.setOnClickListener(this);

        getIncomingIntent();
        initPagerAdapter();
    }

    private void getIncomingIntent(){
        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.intent_product))){
            mProduct = intent.getParcelableExtra(getString(R.string.intent_product));
        }
    }

    private void initPagerAdapter(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        Products products = new Products();
        Product[] selectedProducts = products.PRODUCT_MAP.get(mProduct.getType());
        for(Product product: selectedProducts){
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

    private void addCurrentItemToCart(){
        Product selectedProduct = ((ViewProductFragment)mPagerAdapter.getItem(mProductContainer.getCurrentItem())).mProduct;
        CartManger cartManger = new CartManger(this);
        cartManger.addItemToCart(selectedProduct);
        Toast.makeText(this, "added to cart", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.cart:{
                //open Cart Activity
                break;
            }

            case R.id.add_to_cart:{
                addCurrentItemToCart();
                break;
            }
        }
    }


    /*
        OnTouch
     */

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        getCartPosition();

        if(view.getId() == R.id.product_container){
            mGestureDetector.onTouchEvent(motionEvent);
        }

//        int action = motionEvent.getAction();
//
//        switch(action) {
//            case (MotionEvent.ACTION_DOWN):
//                Log.d(TAG, "Action was DOWN");
//                return false;
//            case (MotionEvent.ACTION_MOVE):
//                Log.d(TAG, "Action was MOVE");
//                return false;
//            case (MotionEvent.ACTION_UP):
//                Log.d(TAG, "Action was UP");
//                return false;
//            case (MotionEvent.ACTION_CANCEL):
//                Log.d(TAG, "Action was CANCEL");
//                return false;
//            case (MotionEvent.ACTION_OUTSIDE):
//                Log.d(TAG, "Movement occurred outside bounds " +
//                        "of current screen element");
//                return false;
//        }

        return false;
    }

    /*
        GestureDetector
     */

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(TAG, "onDown: called");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(TAG, "onShowPress: called.");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapUp: called.");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent,
                            MotionEvent motionEvent1,
                            float v, float v1) {
        Log.d(TAG, "onScroll: called.");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d(TAG, "onLongPress: called.");

        ViewProductFragment fragment = ((ViewProductFragment)mPagerAdapter.getItem(mProductContainer.getCurrentItem()));
        // Instantiates the drag shadow builder.
        View.DragShadowBuilder myShadow = new MyDragShadowBuilder(
                ((ViewProductFragment)fragment).mImageView, fragment.mProduct.getImage());

        // Starts the drag
        ((ViewProductFragment)fragment).mImageView.startDrag(null,  // the data to be dragged
                myShadow,  // the drag shadow builder
                null,      // no need to use local data
                0          // flags (not currently used, set to 0)
        );

        myShadow.getView().setOnDragListener(this);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent,
                           MotionEvent motionEvent1,
                           float v, float v1) {
        Log.d(TAG, "onFling: called.");
        return false;
    }

    /*
        DoubleTap
     */

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapConfirmed: called.");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTap: called.");
        inflateFullScreenProductFragment();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTapEvent: called.");
        return false;
    }

    /*
        OnDragListener
     */
    @Override
    public boolean onDrag(View view, DragEvent event) {

        switch(event.getAction()) {

            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(TAG, "onDrag: drag started.");

                setDragMode(true);

                return true;

            case DragEvent.ACTION_DRAG_ENTERED:

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                Point currentPoint = new Point(Math.round(event.getX()), Math.round(event.getY()));
//                Log.d(TAG, "onDrag: x: " + currentPoint.x + ", y: " + currentPoint.y );

                if(mCartPositionRectangle.contains(currentPoint.x, currentPoint.y)){
                    mCart.setBackgroundColor(this.getResources().getColor(R.color.blue2));
                }
                else{
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
                    if (((ColorDrawable) background).getColor() == getResources().getColor(R.color.blue2)) {
                        addCurrentItemToCart();
                    }
                }
                mCart.setBackground(this.getResources().getDrawable(R.drawable.blue_onclick_dark));
                setDragMode(false);
                return true;

            // An unknown action type was received.
            default:
                Log.e(TAG,"Unknown action type received by OnStartDragListener.");
                break;

        }
        return false;
    }
}

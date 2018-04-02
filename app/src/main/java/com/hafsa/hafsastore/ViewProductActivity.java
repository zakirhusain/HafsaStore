package com.hafsa.hafsastore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hafsa.hafsastore.models.Product;

public class ViewProductActivity extends AppCompatActivity {

    private static final String TAG = "ViewProductActivity";

    //widgets

    //vars
    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.intent_product))){
            mProduct = intent.getParcelableExtra(getString(R.string.intent_product));
        }
    }
}

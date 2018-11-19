package com.yovanydev.appproductos;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yovanydev.appproductos.products.ProductsFragment;

public class ProductsActivity extends AppCompatActivity {

    private Fragment mProductsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mProductsFragment = getSupportFragmentManager().findFragmentById(R.id.products_container);
        setUpProductsFragment();
    }

    /**
     * Crea y Carga una nueva instancia del Fragmento ProductsFragment en la Actividad
     */
    private void setUpProductsFragment() {
        if (mProductsFragment == null) {
            mProductsFragment = ProductsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.products_container, mProductsFragment)
                    .commit();
        }
    }
}

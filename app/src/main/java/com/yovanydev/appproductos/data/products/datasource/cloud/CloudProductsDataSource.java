package com.yovanydev.appproductos.data.products.datasource.cloud;

import android.os.Handler;

import com.google.common.collect.Lists;
import com.yovanydev.appproductos.products.domain.criteria.ProductCriteria;
import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CloudProductsDataSource implements ICloudProductsDataSource {

    private static HashMap<String, Producto> API_DATA;
    private static final long LATENCY = 2000;

    /**
     * Crea un listado ejemplo de productos
     */
    static {
        API_DATA = new LinkedHashMap<>();
        for (int i = 0; i < 100; i++) addProduct(43, "Producto " + (i + 1),"file:///android_asset/mock-product.png");
    }

    private static void addProduct(float price, String name, String imageUrl) {
        Producto newProduct = new Producto(name, price, imageUrl);
        API_DATA.put(newProduct.getmCode(), newProduct);
    }

    @Override
    public void getProducts(final ProductServiceCallback callback, ProductCriteria criteria) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLoaded(Lists.newArrayList(API_DATA.values()));
            }
        }, LATENCY);
    }
}

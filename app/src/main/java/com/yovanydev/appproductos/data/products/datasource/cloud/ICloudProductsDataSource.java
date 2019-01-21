package com.yovanydev.appproductos.data.products.datasource.cloud;

import com.yovanydev.appproductos.products.domain.criteria.ProductCriteria;
import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

public interface ICloudProductsDataSource {
    interface ProductServiceCallback {
        void onLoaded(List<Producto> productos);
        void onError(String error);
    }

    void getProducts(ProductServiceCallback callback, ProductCriteria criteria);
}

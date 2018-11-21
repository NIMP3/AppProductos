package com.yovanydev.appproductos.data.products;

import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

public interface IProductsRepository {

    interface GetProductsCallback {
        void onProductsLoaded(List<Producto> productos);
        void onDataNotAvailable(String error);
    }

    void getProducts(GetProductsCallback callback);
    void refreshProducts();
}

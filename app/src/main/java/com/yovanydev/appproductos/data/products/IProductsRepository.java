package com.yovanydev.appproductos.data.products;

import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

public interface IProductsRepository {

    interface GetProductsCallback {
        /**
         * Procesa el flujo exitoso
         * @param productos : Listado de productos
         */
        void onProductsLoaded(List<Producto> productos);

        /**
         * Procesa el flujo fallido
         * @param error : mensaje de error
         */
        void onDataNotAvailable(String error);
    }

    void getProducts(GetProductsCallback callback);
    void refreshProducts();
}

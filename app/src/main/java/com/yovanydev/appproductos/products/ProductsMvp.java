package com.yovanydev.appproductos.products;

import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

public interface ProductsMvp {
    interface View {
        void showProducts(List<Producto> productos);
        void showLoadingState(boolean show);
        void showEmptyState();
        void showProductsError(String msg);
        void showProductsPage(List<Producto> productos);
        void showLoadMoreIndicator(boolean show);
        void allowMoreData(boolean show);
    }

    interface Presenter {
        void loadProducts(boolean reload);
    }
}

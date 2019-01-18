package com.yovanydev.appproductos.products;

import com.yovanydev.appproductos.data.products.ProductsRepository;
import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductsPresenter  implements ProductsMvp.Presenter{

    private final ProductsRepository mProductsRepository;
    private final ProductsMvp.View mProductsView;


    public ProductsPresenter(ProductsRepository mProductsRepository, ProductsMvp.View mProductsView) {
        this.mProductsRepository = checkNotNull(mProductsRepository);
        this.mProductsView = checkNotNull(mProductsView);
    }

    @Override
    public void loadProducts(boolean reload) {
        final boolean reallyReload = reload || isFirstLoad;

        if (reallyReload) {
            mProductsView.showLoadingState(true);
            mProductsRepository.refreshProducts();
            mCurrentPage = 1;
        }
        else {
            mProductsView.showLoadMoreIndicator(true);
            mCurrentPage++;
        }

        mProductsRepository.getProducts(new ProductsRepository.GetProductsCallback() {

            @Override
            public void onProductsLoaded(List<Producto> productos) {
                mProductsView.showLoadingState(false);
                processProducts(productos, reallyReload);
                isFirstLoad = false;
            }

            @Override
            public void onDataNotAvailable(String error) {
                mProductsView.showLoadingState(false);
                mProductsView.showLoadMoreIndicator(false);
                mProductsView.showProductsError(error);
            }

            private void processProducts(List<Producto> productos, boolean reload) {
                if (productos.isEmpty()) {
                    if (reload) mProductsView.showEmptyState();
                    else mProductsView.showLoadMoreIndicator(false);
                    mProductsView.allowMoreData(false);
                }
                else {
                    if (reload) mProductsView.showProducts(productos);
                    else {
                        mProductsView.showLoadMoreIndicator(false);
                        mProductsView.showProductsPage(productos);
                    }
                    mProductsView.allowMoreData(true);
                }
            }
        });
    }
}

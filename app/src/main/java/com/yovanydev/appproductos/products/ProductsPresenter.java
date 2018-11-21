package com.yovanydev.appproductos.products;

import com.yovanydev.appproductos.data.products.ProductsRepository;

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

        mProductsRepository.getProducts();
    }
}

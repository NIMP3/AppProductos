package com.yovanydev.appproductos.products;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yovanydev.appproductos.R;
import com.yovanydev.appproductos.di.DependencyProvider;
import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements ProductsMvp.View{

    private RecyclerView mProductsList;
    private ProductsAdapter mProductsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProductsPresenter mProductsPresenter;
    private View mEmptyView;
    private ProductsAdapter.ProductItemListener mItemListener = new ProductsAdapter.ProductItemListener() {
        @Override
        public void onProductClick(Producto clickedProduct) {

        }
    };


    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductsAdapter = new ProductsAdapter(new ArrayList<Producto>(0), mItemListener);
        mProductsPresenter = new ProductsPresenter(
                DependencyProvider.provideProductsRepository(getActivity()),
                this
        );

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        //Referencias UI
        mProductsList = view.findViewById(R.id.rvProductsList);
        mEmptyView = view.findViewById(R.id.viewNoProducts);
        mSwipeRefreshLayout = view.findViewById(R.id.refreshLayout);

        //Setup
        setUpProductsList();
        setUpRefreshLayout();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) mProductsPresenter.loadProducts(false);
    }

    /**
     * Configura el swipeRefreshLayout y determina un escuha para detectar el evento Swipe.
     */
    private void setUpRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    /**
     * Configura el adaptador en el RecyclerView.
     */
    private void setUpProductsList() {
        mProductsList.setAdapter(mProductsAdapter);
        mProductsList.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = (LinearLayoutManager) mProductsList.getLayoutManager();

        //Se agrega escucha de scroll infinito
        mProductsList.addOnScrollListener(new InfiniteScrollListener(layoutManager, mProductsAdapter) {
            @Override
            public void onLoadMore() {
                mProductsPresenter.loadProducts(false);
            }
        });
    }

    /*----------------------------------------------------------------------------------------------
    * Metodos implementados de la Vista - ProductsMvp.view*/
    @Override
    public void showProducts(List<Producto> productos) {
        mProductsAdapter.replaceData(productos);

        mProductsList.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingState(final boolean show) {
        if (getView() == null) return;
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void showEmptyState() {
        mProductsList.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProductsError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProductsPage(List<Producto> productos) {
        mProductsAdapter.addData(productos);
    }

    @Override
    public void showLoadMoreIndicator(boolean show) {

    }

    @Override
    public void allowMoreData(boolean show) {

    }
}

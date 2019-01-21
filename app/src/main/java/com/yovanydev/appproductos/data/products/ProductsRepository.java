package com.yovanydev.appproductos.data.products;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yovanydev.appproductos.data.products.datasource.cloud.ICloudProductsDataSource;
import com.yovanydev.appproductos.data.products.datasource.memory.IMemoryProductsDataSource;
import com.yovanydev.appproductos.products.domain.criteria.ProductCriteria;
import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductsRepository implements IProductsRepository {

    private final IMemoryProductsDataSource mMemoryProductsDataSource;
    private final ICloudProductsDataSource mCloudProductsDataSource;
    private final Context mContext;

    private boolean mReload;

    public ProductsRepository(IMemoryProductsDataSource mMemoryProductsDataSource, ICloudProductsDataSource mCloudProductsDataSource, Context mContext) {
        this.mMemoryProductsDataSource = checkNotNull(mMemoryProductsDataSource);
        this.mCloudProductsDataSource = checkNotNull(mCloudProductsDataSource);
        this.mContext = checkNotNull(mContext);
    }

    @Override
    public void getProducts(GetProductsCallback callback, ProductCriteria criteria) {
        //Si la memoria esta vacia y no hay recarga
        if (!mMemoryProductsDataSource.mapIsNull() && !mReload) {
            getProductsFromMemory(callback, criteria);
            return;
        }
        //Si la recarga esta activa si no es porque hay datos en memoria
        if (mReload) getProductsFromServer(callback, criteria);
        else {
            List<Producto> productos = mMemoryProductsDataSource.find(criteria);
            if (productos.size() > 0) callback.onProductsLoaded(productos);
            else getProductsFromServer(callback, criteria);
        }
    }

    /**
     * Obtiene los productos de la memoria
     * @param callback
     */
    private void getProductsFromMemory(GetProductsCallback callback, ProductCriteria criteria) {
        callback.onProductsLoaded(mMemoryProductsDataSource.find(criteria));
    }

    /**
     * Obtiene los productos del servidor
     * @param callback
     * @param criteria
     */
    private void getProductsFromServer(final GetProductsCallback callback, final ProductCriteria criteria) {
        if (!isOnline()) {
            callback.onDataNotAvailable("No hay conexión de red");
            return;
        }
        mCloudProductsDataSource.getProducts(
                new ICloudProductsDataSource.ProductServiceCallback() {
                    @Override
                    public void onLoaded(List<Producto> productos) {
                        refreshMemoryDataSource(productos); //Refresco la los datos alojados en memoria
                        getProductsFromMemory(callback, criteria);
                    }
                    @Override
                    public void onError(String error) {
                        callback.onDataNotAvailable(error);
                    }
                }, null
        );
    }

    /**
     * Determina el estado de (CONEXIÓN/DESCONEXIÓN) del dispositivo.
     * @return : Bandera que determina el estado de conexión
     */
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null &&  info.isConnectedOrConnecting();
    }

    /**
     *
     * @param productos
     */
    private void refreshMemoryDataSource(List<Producto> productos) {
        mMemoryProductsDataSource.deleteAll();
        for (Producto producto : productos) mMemoryProductsDataSource.save(producto);
        mReload = false;
    }

    @Override
    public void refreshProducts() {
        mReload = true;
    }
}

package com.yovanydev.appproductos.data.products.datasource.memory;

import com.google.common.collect.Lists;
import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MemoryProductsDataSource implements  IMemoryProductsDataSource{

    private static HashMap<String, Producto> mCachedProducts;

    @Override
    public List<Producto> find(ProductoCriteria criteria) {
        ArrayList<Producto> productos = Lists.newArrayList(mCachedProducts.values());
        return productos;
    }

    @Override
    public void save(Producto producto) {
        if (mCachedProducts == null) mCachedProducts = new LinkedHashMap<>();
        mCachedProducts.put(producto.getmCode(), producto);
    }

    @Override
    public void deleteAll() {
        if (mCachedProducts == null) mCachedProducts = new LinkedHashMap<>();
        mCachedProducts.clear();
    }

    @Override
    public boolean mapIsNull() {
        return mCachedProducts == null;
    }
}

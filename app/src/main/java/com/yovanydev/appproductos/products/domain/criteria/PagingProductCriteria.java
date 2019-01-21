package com.yovanydev.appproductos.products.domain.criteria;

import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class PagingProductCriteria implements ProductCriteria{
    private final int mPage;
    private final int mLimit;

    public PagingProductCriteria(int mPage, int mLimit) {
        this.mPage = mPage;
        this.mLimit = mLimit;
    }

    @Override
    public List<Producto> match(List<Producto> productos) {
        List<Producto> criteriaProducts = new ArrayList<>();

        if (mLimit <= 0 || mPage <= 0) return criteriaProducts;

        int size = productos.size(); //Tamaño de la lista
        int numPages = size / mLimit; // Numero de paginas
        int a,b; //Rangos (Inicial y Final) para lista a mostrar por pagina

        if (mPage > numPages) return criteriaProducts;
        a = (mPage - 1) * mLimit; //Determinamos el rango minimo para la pagina actual
        if (a == size) return criteriaProducts;
        b = a + mLimit; // Determinamos el rango superior para la pagina actual
        criteriaProducts = productos.subList(a,b);
        return  criteriaProducts;
    }
}

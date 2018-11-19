package com.yovanydev.appproductos.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yovanydev.appproductos.R;
import com.yovanydev.appproductos.products.domain.model.Producto;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Producto> productos;
    private ProductItemListener mItemListener;

    public ProductsAdapter(List<Producto> productos, ProductItemListener mItemListener) {
        setList(productos);
        this.mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_product, viewGroup, false);
        return new ProductsHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ProductsHolder) {
            Producto producto = productos.get(i);

            ProductsHolder productsHolder = (ProductsHolder) viewHolder;
            productsHolder.tvPrice.setText(producto.getFormatedPrice());
            productsHolder.tvName.setText(producto.getmName());
            productsHolder.tvUnitsInStock.setText(producto.getFormatedUnitsInStock());

            Picasso.get()
                    .load(producto.getmImageUrl())
                    .centerCrop()
                    .into(productsHolder.ivFeaturedImage);
        }
    }

    public void replaceData(List<Producto> notes) {
        setList(notes);
        notifyDataSetChanged();
    }

    private void setList(List<Producto> notes) {
        productos = checkNotNull(notes);
    }

    public void addData(List<Producto> products) {
        products.addAll(products);
    }

    @Override
    public int getItemCount() {
        return getDataItemCount();
    }

    public Producto getItem(int position) {
        return productos.get(position);
    }

    public int getDataItemCount() {
        return productos.size();
    }

    public class ProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public TextView tvPrice;
        public ImageView ivFeaturedImage;
        public TextView tvUnitsInStock;

        private ProductItemListener mItemListener;

        public ProductsHolder(@NonNull View itemView, ProductItemListener listener) {
            super(itemView);

            mItemListener = listener;

            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvUnitsInStock = itemView.findViewById(R.id.tvUnitsInStock);
            ivFeaturedImage = itemView.findViewById(R.id.ivFeaturedImage);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Producto producto = getItem(position);
            mItemListener.onProductClick(producto);
        }
    }

    public interface ProductItemListener {
        void onProductClick(Producto clickedNote);
    }
}

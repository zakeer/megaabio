package com.rainersoft.megaabio.features.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rainersoft.megaabio.BuildConfig;
import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.ProductDetail;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static com.rainersoft.megaabio.features.product.ProductsActivity.DECREMENT;
import static com.rainersoft.megaabio.features.product.ProductsActivity.INCREMENT;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MetaProductViewHolder> {

    private List<Product> productsList;
    private List<ProductDetail> productDetailList;
    private Subject<ProductClickUpdate> productClickSubject;
    private Context context;

    @Inject
    ProductsAdapter() {
        productClickSubject = PublishSubject.create();
        productsList = Collections.emptyList();
        productDetailList = Collections.emptyList();
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
        notifyDataSetChanged();
    }

    public void setProductDetailList(List<ProductDetail> productDetailList) {
        this.productDetailList = productDetailList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public MetaProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_product, parent, false);
        return new MetaProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MetaProductViewHolder holder, int position) {
        Product product = this.productsList.get(position);
        holder.onBind(product);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public Observable<ProductClickUpdate> getProductUpdate() {
        return productClickSubject;
    }

    class MetaProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productImage)
        ImageView productImage;

        @BindView(R.id.productName)
        TextView productName;

        @BindView(R.id.tvProductType)
        TextView tvProductType;

        @BindView(R.id.tvProductCase)
        TextView tvProductCase;

        @BindView(R.id.tvProductCaseValue)
        TextView tvProductCaseValue;

        @BindView(R.id.tvProductCompany)
        TextView tvProductCompany;

        @BindView(R.id.tvProductAmount)
        TextView tvProductAmount;

        @BindView(R.id.cartCount)
        TextView cartCount;

        @BindView(R.id.layoutCartAction)
        LinearLayout layoutCartAction;

        @BindView(R.id.btnAddToCard)
        Button btnAddToCard;

        private Product product;

        MetaProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void onBind(Product product) {
            this.product = product;
            productName.setText(
                    String.format(
                            "%s", product.getProductName()));

            tvProductType.setText(String.format("%s", product.getTypeName()));
            tvProductCase.setText(String.format("%s", product.getProdCase()));
          //  tvProductCaseValue.setText(String.format("%s", product.getProdCaseValue()));
            tvProductCompany.setText(String.format("%s", product.getCompanyName()));
            tvProductAmount.setText(String.format("%s /-", product.getAmount()));
            cartCount.setText(String.format("%s /-", product.getQuantity()));

            layoutCartAction.setVisibility(product.getQuantity() <= 0 ? View.GONE : View.VISIBLE);
            btnAddToCard.setVisibility(product.getQuantity() <= 0 ? View.VISIBLE : View.GONE);

            String imageUrl = String.format("%s%s", BuildConfig.IMAGE_BASE_URL, product.getImage());
            Glide.with(context).load(imageUrl).into(productImage);
        }

        @OnClick(R.id.btnAddToCard)
        void addToCard(View view) {
            productClickSubject.onNext(new ProductClickUpdate(INCREMENT, product));
        }

        @OnClick(R.id.addCartView)
        void addCartView(View view) {
            addToCard(view);
        }

        @OnClick(R.id.removeCartView)
        void removeCartView(View view) {
            productClickSubject.onNext(new ProductClickUpdate(DECREMENT, product));
        }
    }

    public class ProductClickUpdate {
        public String type;
        public Product product;

        public ProductClickUpdate(String type, Product product) {
            this.product = product;
            this.type = type;
        }
    }
}

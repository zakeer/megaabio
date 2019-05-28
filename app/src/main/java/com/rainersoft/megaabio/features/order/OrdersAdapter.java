package com.rainersoft.megaabio.features.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rainersoft.megaabio.BuildConfig;
import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.ResponseData;

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

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<ResponseData> orders;
    private Subject<ResponseData> orderClickSubject;
    private Context context;

    @Inject
    OrdersAdapter() {
        orderClickSubject = PublishSubject.create();
        orders = Collections.emptyList();
    }

    public void setOrders(List<ResponseData> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_order, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        ResponseData order = this.orders.get(position);
        holder.onBind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.orderId)
        TextView orderId;

        @BindView(R.id.tvStatus)
        TextView tvStatus;

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.tvAmount)
        TextView tvAmount;

        private ResponseData order;

        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> orderClickSubject.onNext(order));

        }

        void onBind(ResponseData order) {
            this.order = order;

            orderId.setText(String.format("# %s", order.getOrderId()));
            tvDate.setText(String.format("%s", order.getOrderDate()));
            tvStatus.setText(String.format("%s", order.getOrderStatus()));
            tvAmount.setText(String.format("Rs. %s /-", order.getAmount()));
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

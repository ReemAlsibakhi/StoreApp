package com.example.storeapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapp.R;
import com.example.storeapp.model.Item;
import com.example.storeapp.model.Purchase;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.viewHolder> {
    private List<Purchase> dataList;
    Context context;
    private static final String TAG = "PurchaseAdapter";
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClicked(Purchase purchase);
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public PurchaseAdapter(List<Purchase> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.ItemName.setText(dataList.get(position).getItem_name());
        holder.date.setText(dataList.get(position).getDate());
        holder.totalPrice.setText(String.valueOf(dataList.get(position).getTotalPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + dataList.get(position).getTotalPrice());
                if (mListener != null) {
                    mListener.onClicked(dataList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setList(List<Purchase> usersList) {
        this.dataList = usersList;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView ItemName, date, totalPrice;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.tv_ItemName);
            date = itemView.findViewById(R.id.tv_date);
            totalPrice = itemView.findViewById(R.id.tv_totalPrice);
        }
    }
}


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
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.viewHolder> {
    private List<Item> dataList;
    Context context;
    private static final String TAG = "CategoryAdapter";
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClicked(Item category);
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public ItemAdapter(List<Item> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.catName.setText(dataList.get(position).getName());
        holder.price.setText(dataList.get(position).getPrice());
        holder.payType.setText(dataList.get(position).getPaymentType());
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(dataList.get(position).getImage(), 0, dataList.get(position).getImage().length);
        holder.imgCat.setImageBitmap(bitmapImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + dataList.get(position).getName());
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

    public void setList(List<Item> usersList) {
        this.dataList = usersList;
        notifyDataSetChanged();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView catName, payType, price;
        ImageView imgCat;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.tv_catName);
            payType = itemView.findViewById(R.id.tv_payType);
            price = itemView.findViewById(R.id.tv_price);
            imgCat=itemView.findViewById(R.id.img_cat);
        }
    }
}


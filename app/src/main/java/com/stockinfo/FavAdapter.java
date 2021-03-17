package com.stockinfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>{

    private Context context;

    public static List<Stock> stockFavList = new ArrayList<>();

    private DbHelper db;


    public void addItemToFav(Stock stock) {
        stockFavList.add(stock);
        notifyDataSetChanged();
    }

    public FavAdapter(Context context, List<Stock> stockFavList) {
        this.context = context;
        this.stockFavList = stockFavList;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        Log.i("MY_TAG", "FAVOURITES BIND invoked ");
        Stock currentStock = stockFavList.get(position);
        holder.tickerTextView.setText(String.valueOf(currentStock.getTicker()));
        holder.priceOpenTextView.setText(String.valueOf(currentStock.getPriceCurrent()));
    }

    @Override
    public int getItemCount() {
        return stockFavList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tickerTextView;
        private TextView priceOpenTextView;

        private Button newsButton;
        private Button graphButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            newsButton = itemView.findViewById(R.id.btn_news);
            tickerTextView = itemView.findViewById(R.id.stock_info_text_view);
            priceOpenTextView = itemView.findViewById(R.id.stock_info_price_view);
        }
    }
}
package com.stockinfo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.StockViewHolder> {


    private onStockClickListener listener;

    public interface onStockClickListener {
        void onStockClick(int position);
    }

    public void setOnStockClickListener(onStockClickListener listener) {
        this.listener = listener;
    }


    Context context;
    private static List<Stock> stockList;


    public RecyclerViewAdapter(Context context, List<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("MY_TAG", "onCreateViewHolder invoked ");
        View v = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false);
        return new StockViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Log.i("MY_TAG", "onBindViewHolder invoked ");
        Stock currentStock = stockList.get(position);
        holder.tickerTextView.setText(String.valueOf(currentStock.getTicker()));
        holder.priceOpenTextView.setText(String.valueOf(currentStock.getPriceCurrent()));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        private TextView tickerTextView;
        private TextView priceOpenTextView;

        private Button newsButton;
        private Button graphButton;
        private ImageButton addToFavs;


        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            newsButton = itemView.findViewById(R.id.btn_news);
            tickerTextView = itemView.findViewById(R.id.stock_info_text_view);
            priceOpenTextView = itemView.findViewById(R.id.stock_info_price_view);
            addToFavs = itemView.findViewById(R.id.btn_star);


            newsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newsActivityIntent = new Intent(context, NewsActivity.class);
                    newsActivityIntent.putExtra("ticker", tickerTextView.getText().toString());
                    Log.i("TAG", "Starting NewsActivity ");
                    context.startActivity(newsActivityIntent);
                }
            });
            addToFavs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Stock stock = stockList.get(position);
                    if (stock.getIsFavourite().equals("false")) {
                        stock.setIsFavourite("true");
                        addToFavs.setBackgroundResource(R.drawable.ic_star_filled_24);
//                        MainActivity.dbManager.insertToDb(stock);
                        MainActivity.dbManager.updateData(stock);
                        FavAdapter.stockFavList.add(stock);
                        FragmentStarList.favAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}

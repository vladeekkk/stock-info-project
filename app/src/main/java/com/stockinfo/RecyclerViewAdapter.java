package com.stockinfo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.StockViewHolder> implements Filterable {

    public static final String TAG = "MY_TAG";

    Context context;
    private static List<Stock> stockList;
    private static List<Stock> stockListFull;


    public RecyclerViewAdapter(Context context, List<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
        stockListFull = new ArrayList<>(stockList);
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder invoked ");
        View v = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false);
        return new StockViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder invoked ");
        Stock currentStock = stockList.get(position);
        holder.tickerTextView.setText(String.valueOf(currentStock.getTicker()));
        holder.priceOpenTextView.setText(String.valueOf(currentStock.getPriceCurrent()));
        if (stockList.get(position).getIsFavourite().equals("true")) {
            holder.addToFavs.setBackgroundResource(R.drawable.ic_star_filled_24);
        } else {
            holder.addToFavs.setBackgroundResource(R.drawable.ic_star_empty_24);
        }
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        private TextView tickerTextView;
        private TextView priceOpenTextView;

        private Button newsButton;
        private ImageButton addToFavs;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            newsButton = itemView.findViewById(R.id.btn_news);
            tickerTextView = itemView.findViewById(R.id.stock_info_text_view);
            priceOpenTextView = itemView.findViewById(R.id.stock_info_price_view);
            addToFavs = itemView.findViewById(R.id.btn_star);

            newsButton.setOnClickListener(v -> {
                Intent newsActivityIntent = new Intent(context, NewsActivity.class);
                newsActivityIntent.putExtra("ticker", tickerTextView.getText().toString());
                Log.i(TAG, "Starting NewsActivity ");
                context.startActivity(newsActivityIntent);
            });
            addToFavs.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Stock stock = stockList.get(position);

                if (stock.getIsFavourite().equals("false")) {
                    addToFavs.setBackgroundResource(R.drawable.ic_star_filled_24);
                    FavAdapter.stockFavList.add(stock);
                } else {
                    FavAdapter.stockFavList.remove(stock);
                    addToFavs.setBackgroundResource(R.drawable.ic_star_empty_24);
                }
                MainActivity.dbManager.changeFavourites(stock);
                FragmentStarList.favAdapter.notifyDataSetChanged();
            });
        }
    }

    @Override
    public Filter getFilter() {
        return stockFilter;
    }

    private Filter stockFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Stock> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stockListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Stock stock : stockListFull) {
                    if (stock.getTicker().toLowerCase().contains(filterPattern)) {
                        filteredList.add(stock);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stockList.clear();
            stockList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

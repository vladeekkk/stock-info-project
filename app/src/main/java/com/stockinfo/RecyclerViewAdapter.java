package com.stockinfo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.StockViewHolder>
        implements Filterable  {

    public static final String TAG = "MY_TAG";

    Context context;
    private static List<Stock> stockList;
    private static List<Stock> stockListFull;


    public RecyclerViewAdapter(Context context, List<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
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
            holder.addToFavs.setBackgroundResource(R.drawable.ic_filled_star);
        } else {
            holder.addToFavs.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
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
                    addToFavs.setBackgroundResource(R.drawable.ic_filled_star);
                    FavAdapter.stockFavList.add(stock);
                } else {
                    FavAdapter.stockFavList.remove(stock);
                    addToFavs.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
                }
                MainActivity.dbManager.changeFavourites(stock);
                FragmentStarList.favAdapter.notifyDataSetChanged();
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Stock> results = new ArrayList<>();
                if (stockListFull == null) {
                    stockListFull = stockList;
                    if (constraint != null) {
                        if (stockListFull != null && stockListFull.size() > 0) {
                            for (final Stock s : stockListFull) {
                                if (s.getTicker().toLowerCase().contains(constraint.toString()) ||
                                        s.getTicker().toUpperCase().contains(constraint.toString())) {
                                    results.add(s);
                                }
                            }
                        }
                        oReturn.values = results;
                    }
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stockList = (List<Stock>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

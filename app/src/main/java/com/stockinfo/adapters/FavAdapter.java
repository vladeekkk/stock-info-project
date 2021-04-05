package com.stockinfo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stockinfo.fragments.FragmentStockList;
import com.stockinfo.activities.MainActivity;
import com.stockinfo.activities.NewsActivity;
import com.stockinfo.R;
import com.stockinfo.stockpackage.Stock;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private Context context;

    public static List<Stock> stockFavList = new ArrayList<>();

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
        private ImageButton favButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            newsButton = itemView.findViewById(R.id.btn_news);
            tickerTextView = itemView.findViewById(R.id.stock_info_text_view);
            priceOpenTextView = itemView.findViewById(R.id.stock_info_price_view);

            favButton = itemView.findViewById(R.id.btn_star);
            favButton.setBackgroundResource(R.drawable.ic_filled_star);

            newsButton.setOnClickListener(v -> {
                Intent newsActivityIntent = new Intent(context, NewsActivity.class);
                newsActivityIntent.putExtra("ticker", tickerTextView.getText().toString());
                context.startActivity(newsActivityIntent);
            });

            favButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Stock stock = stockFavList.get(position);
                MainActivity.dbManager.changeFavourites(stock);
                stockFavList.remove(stock);
                notifyItemRemoved(position);
                FragmentStockList.recyclerAdapter.notifyDataSetChanged();
            });
        }
    }
}

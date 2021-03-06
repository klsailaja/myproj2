package com.ab.telugumoviequiz.history;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ab.telugumoviequiz.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {

    private final List<GameResults> gameResults;
    private final String[] headings;
    static int screenWidth;
    static int w1, w2, w3, w4, w5;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private View.OnClickListener listener;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView snoView, dateView, tktRateView, celebrityView, viewResultsView;

        MyViewHolder(View view) {
            super(view);
            snoView = view.findViewById(R.id.col1);
            dateView = view.findViewById(R.id.col2);
            tktRateView = view.findViewById(R.id.col3);
            celebrityView = view.findViewById(R.id.col4);
            viewResultsView = view.findViewById(R.id.col5);
        }
    }

    public ViewAdapter(List<GameResults> gameResults, String[] headings, View.OnClickListener listener) {
        this.gameResults = gameResults;
        this.headings = headings;
        this.listener = listener;
        w1 = (screenWidth * 6)/100;
        w2 = (screenWidth * 25)/100;
        w3 = (screenWidth * 15)/100;
        w4 = (screenWidth * 27)/100;
        w5 = (screenWidth * 27)/100;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_table_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.snoView.getLayoutParams().width = w1;
        holder.snoView.getLayoutParams().height = 150;
        holder.dateView.getLayoutParams().width = w2;
        holder.dateView.getLayoutParams().height = 150;
        holder.tktRateView.getLayoutParams().width = w3;
        holder.tktRateView.getLayoutParams().height = 150;
        holder.celebrityView.getLayoutParams().width = w4;
        holder.celebrityView.getLayoutParams().height = 150;
        holder.viewResultsView.getLayoutParams().width = w5;
        holder.viewResultsView.getLayoutParams().height = 150;

        if (position == 0) {
            holder.snoView.setText(headings[0]);
            holder.dateView.setText(headings[1]);
            holder.tktRateView.setText(headings[2]);
            holder.celebrityView.setText(headings[3]);
            holder.viewResultsView.setText(headings[4]);

            holder.snoView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.dateView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.tktRateView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.celebrityView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.viewResultsView.setBackgroundResource(R.drawable.table_header_cell_bg);

        } else {
            GameResults gameResult = gameResults.get(position - 1);

            holder.snoView.setText(String.valueOf(gameResult.getsNo()));

            Date date = new Date(gameResult.getGamePlayedTime());
            String datePattern = "dd:MMM:yyyy:HH:mm";
            simpleDateFormat.applyPattern(datePattern);
            String timeStr = simpleDateFormat.format(date);
            holder.dateView.setText(timeStr);
            holder.tktRateView.setText(String.valueOf(gameResult.getTktRate()));
            holder.celebrityView.setText(gameResult.getCelebrityName());

            holder.snoView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.dateView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.celebrityView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.tktRateView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.viewResultsView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.viewResultsView.setTag(gameResult.getWinnersList());
            holder.viewResultsView.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return gameResults.size() + 1;
    }
}

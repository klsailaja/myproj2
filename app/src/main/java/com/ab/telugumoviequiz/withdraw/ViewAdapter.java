package com.ab.telugumoviequiz.withdraw;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ab.telugumoviequiz.R;
import com.ab.telugumoviequiz.constants.WithdrawReqState;
import com.ab.telugumoviequiz.constants.WithdrawReqType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.ab.telugumoviequiz.withdraw.WithdrawReqsView.CANCEL_BUTTON_ID;
import static com.ab.telugumoviequiz.withdraw.WithdrawReqsView.MORE_OPTIONS_BUTTON_ID;

public class ViewAdapter extends RecyclerView.Adapter {

    private final List<WithdrawRequest> data;
    private final String[] headings;
    static int screenWidth;
    static int w1, w2, w3, w4, w5, w6, w7;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private int creditColor = Color.parseColor("green");
    private int withdrawColor = Color.parseColor("red");
    private static final int HEADING_VIEW = 1;
    private static final int DATA_VIEW = 2;
    private View.OnClickListener mClickListener;

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView snoView , refIdView, amtView, statusView, openedDateView, cancelReqHeading, moreOptionsHeading;

        HeadViewHolder(View view) {
            super(view);
            snoView = view.findViewById(R.id.col1);
            refIdView = view.findViewById(R.id.col2);
            amtView = view.findViewById(R.id.col3);
            statusView = view.findViewById(R.id.col4);
            openedDateView = view.findViewById(R.id.col5);
            cancelReqHeading = view.findViewById(R.id.col6);
            moreOptionsHeading = view.findViewById(R.id.col7);
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView snoView , refIdView, amtView, statusView, openedDateView;
        Button cancelReqButton;
        ImageButton moreOptionsButton;

        DataViewHolder(View view) {
            super(view);
            snoView = view.findViewById(R.id.col1);
            refIdView = view.findViewById(R.id.col2);
            amtView = view.findViewById(R.id.col3);
            statusView = view.findViewById(R.id.col4);
            openedDateView = view.findViewById(R.id.col5);
            cancelReqButton = view.findViewById(R.id.col6);
            moreOptionsButton = view.findViewById(R.id.col7);
        }
    }

    public ViewAdapter(List<WithdrawRequest> data, String[] headings) {
        this.data = data;
        this.headings = headings;
        w1 = (screenWidth * 6)/100;
        w2 = (screenWidth * 15)/100;
        w3 = (screenWidth * 15)/100;
        w4 = (screenWidth * 15)/100;
        w5 = (screenWidth * 15)/100;
        w6 = (screenWidth * 20)/100;
        w7 = (screenWidth * 10)/100;
    }

    void setClickListener(View.OnClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public int getItemViewType (int position) {
        if (position == 0) {
            return HEADING_VIEW;
        }
        return DATA_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADING_VIEW) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.withdraw_table_item_head, parent, false);
            return new HeadViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.withdraw_table_item, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderArg, int position) {
        if (position == 0) {
            HeadViewHolder holder = (HeadViewHolder) holderArg;

            holder.snoView.getLayoutParams().width = w1;
            holder.snoView.getLayoutParams().height = 200;
            holder.refIdView.getLayoutParams().width = w2;
            holder.refIdView.getLayoutParams().height = 200;
            holder.amtView.getLayoutParams().width = w3;
            holder.amtView.getLayoutParams().height = 200;
            holder.statusView.getLayoutParams().width = w4;
            holder.statusView.getLayoutParams().height = 200;
            holder.openedDateView.getLayoutParams().width = w5;
            holder.openedDateView.getLayoutParams().height = 200;
            holder.cancelReqHeading.getLayoutParams().width = w6;
            holder.cancelReqHeading.getLayoutParams().height = 200;
            holder.moreOptionsHeading.getLayoutParams().width = w7;
            holder.moreOptionsHeading.getLayoutParams().height = 200;

            holder.snoView.setText(headings[0]);
            holder.refIdView.setText(headings[1]);
            holder.amtView.setText(headings[2]);
            holder.statusView.setText(headings[3]);
            holder.openedDateView.setText(headings[4]);
            holder.cancelReqHeading.setText(headings[5]);
            holder.moreOptionsHeading.setText(headings[6]);

            holder.snoView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.refIdView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.amtView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.statusView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.openedDateView.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.cancelReqHeading.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.moreOptionsHeading.setBackgroundResource(R.drawable.table_header_cell_bg);

        } else {
            DataViewHolder holder = (DataViewHolder) holderArg;

            holder.snoView.getLayoutParams().width = w1;
            holder.snoView.getLayoutParams().height = 200;
            holder.refIdView.getLayoutParams().width = w2;
            holder.refIdView.getLayoutParams().height = 200;
            holder.amtView.getLayoutParams().width = w3;
            holder.amtView.getLayoutParams().height = 200;
            holder.statusView.getLayoutParams().width = w4;
            holder.statusView.getLayoutParams().height = 200;
            holder.openedDateView.getLayoutParams().width = w5;
            holder.openedDateView.getLayoutParams().height = 200;
            holder.cancelReqButton.getLayoutParams().width = w6;
            holder.cancelReqButton.getLayoutParams().height = 200;
            holder.moreOptionsButton.getLayoutParams().width = w7;
            holder.moreOptionsButton.getLayoutParams().height = 200;

            WithdrawRequest wdRequest = data.get(position - 1);

            holder.snoView.setText(String.valueOf(wdRequest.getsNo()));
            holder.refIdView.setText(String.valueOf(wdRequest.getRefId()));
            holder.amtView.setText(String.valueOf(wdRequest.getAmount()));

            String wdState = "OPEN";
            boolean isCancelAllowed = false;
            if (wdRequest.getReqStatus() == WithdrawReqState.CANCELLED.getId()) {
                wdState = "CANCELLED";
            } else if (wdRequest.getReqStatus() == WithdrawReqState.CLOSED.getId()) {
                wdState = "CLOSED";
            } else if (wdRequest.getReqStatus() == WithdrawReqState.OPEN.getId()) {
                isCancelAllowed = true;
            }
            holder.statusView.setText(wdState);
            holder.cancelReqButton.setEnabled(isCancelAllowed);
            if (isCancelAllowed) {
                holder.cancelReqButton.setOnClickListener(mClickListener);
                holder.cancelReqButton.setId(CANCEL_BUTTON_ID);
                holder.cancelReqButton.setTag(wdRequest.getRefId());
            }
            holder.moreOptionsButton.setId(MORE_OPTIONS_BUTTON_ID);
            holder.moreOptionsButton.setTag(wdRequest);
            holder.moreOptionsButton.setOnClickListener(mClickListener);

            Date date = new Date(wdRequest.getOpenedTime());
            String datePattern = "dd:MMM:yyyy:HH:mm";
            simpleDateFormat.applyPattern(datePattern);
            String timeStr = simpleDateFormat.format(date);
            holder.openedDateView.setText(timeStr);


            holder.snoView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.refIdView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.amtView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.statusView.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.openedDateView.setBackgroundResource(R.drawable.table_content_cell_bg);
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    private long getReceiptContentsId(WithdrawRequest wdRequest) {
        if (wdRequest.getFromAccType() == WithdrawReqType.BY_PHONE.getId()) {
            return wdRequest.getByPhone().getId();
        }
        if (wdRequest.getRequestType() == WithdrawReqType.BY_BANK.getId()) {
            return -1;
        }
        return -1;
    }
}

package com.ab.telugumoviequiz.withdraw;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ab.telugumoviequiz.R;
import com.ab.telugumoviequiz.common.BaseFragment;
import com.ab.telugumoviequiz.common.CallbackResponse;
import com.ab.telugumoviequiz.common.DialogAction;
import com.ab.telugumoviequiz.common.GetTask;
import com.ab.telugumoviequiz.common.Request;
import com.ab.telugumoviequiz.common.Scheduler;
import com.ab.telugumoviequiz.common.UserDetails;
import com.ab.telugumoviequiz.common.Utils;
import com.ab.telugumoviequiz.constants.WithdrawReqState;
import com.ab.telugumoviequiz.constants.WithdrawReqType;
import com.ab.telugumoviequiz.games.ViewLeaderboard;
import com.ab.telugumoviequiz.main.UserProfile;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WithdrawReqsView extends BaseFragment implements PopupMenu.OnMenuItemClickListener,
        View.OnClickListener, CallbackResponse, DialogAction {

    private AlertDialog alertDialog;
    private int startPosOffset = 0;
    private ViewAdapter tableAdapter;
    private final List<WithdrawRequest> tableData = new ArrayList<>();
    private int wdStatus = -1;
    public final static int CANCEL_BUTTON_ID = 1;
    public final static int MORE_OPTIONS_BUTTON_ID = 2;

    private void handleListeners(View.OnClickListener listener) {
        View view = getView();
        if (view == null) {
            return;
        }
        Button prevButton = view.findViewById(R.id.myreferals_prev_but);
        Button nextButton = view.findViewById(R.id.myreferals_next_but);

        prevButton.setOnClickListener(listener);
        nextButton.setOnClickListener(listener);

        Button filterAccType = view.findViewById(R.id.filterStatus);
        filterAccType.setOnClickListener(listener);
    }

    private void fetchRecords() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Information");
        alertDialogBuilder.setMessage("Loading. Please Wait!").setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        UserProfile userProfile = UserDetails.getInstance().getUserProfile();
        GetTask<WithdrawRequestsHolder> request = Request.getWDReqs(userProfile.getId(), startPosOffset, wdStatus);
        request.setCallbackResponse(this);
        Scheduler.getInstance().submit(request);
    }

    private void populateTable(WithdrawRequestsHolder details) {

        View view = getView();
        if (view == null) {
            return;
        }
        Button prevButton = view.findViewById(R.id.myreferals_prev_but);
        Button nextButton = view.findViewById(R.id.myreferals_next_but);

        prevButton.setEnabled(details.isPrevEnabled());
        nextButton.setEnabled(details.isNextEnabled());
        List<WithdrawRequest> list = details.getList();
        tableData.clear();
        tableData.addAll(list);
        tableAdapter.notifyDataSetChanged();
    }

    @Override
    public void doAction(int id, Object userObject) {
        if (id == CANCEL_BUTTON_ID) {
            String wdRefId = (String) userObject;
            UserProfile userProfile = UserDetails.getInstance().getUserProfile();
            GetTask<Boolean> cancelTask = Request.getCancelReq(userProfile.getId(), wdRefId);
            cancelTask.setCallbackResponse(this);
            Scheduler.getInstance().submit(cancelTask);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        int[] points = Utils.getScreenWidth(getContext());
        ViewAdapter.screenWidth = points[0];

        String [] tableHeadings = new String[8];
        Resources resources = getResources();
        tableHeadings[0] = resources.getString(R.string.wd_col1);
        tableHeadings[1] = resources.getString(R.string.wd_col2);
        tableHeadings[2] = resources.getString(R.string.wd_col3);
        tableHeadings[3] = resources.getString(R.string.wd_col4);
        tableHeadings[4] = resources.getString(R.string.wd_col5);
        tableHeadings[5] = resources.getString(R.string.wd_col6);
        tableHeadings[6] = resources.getString(R.string.wd_col7);
        tableHeadings[7] = resources.getString(R.string.wd_col8);

        View root = inflater.inflate(R.layout.withdraw, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        tableAdapter = new ViewAdapter(tableData, tableHeadings);
        tableAdapter.setClickListener(this);
        recyclerView.setAdapter(tableAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedBundle) {
        super.onActivityCreated(savedBundle);
        fetchRecords();
    }

    @Override
    public void onResume() {
        super.onResume();
        handleListeners(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        handleListeners(null);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int maxRowCount = 5;
        if (id == R.id.myreferals_prev_but) {
            startPosOffset = startPosOffset - maxRowCount;
            fetchRecords();
        } else if (id == R.id.myreferals_next_but) {
            startPosOffset = startPosOffset + maxRowCount;
            fetchRecords();
        } else if (id == R.id.filterStatus) {
            Resources resources = Objects.requireNonNull(getActivity()).getResources();
            CharSequence[] accTypes = resources.getTextArray(R.array.wd_options);
            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
            for (CharSequence s : accTypes) {
                MenuItem item = popupMenu.getMenu().add(s);
                item.setActionView(view);
            }
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        } else if (id == CANCEL_BUTTON_ID){
            String wdRefId = (String) view.getTag();
            Utils.showMessage("Confirmation?", "Please confirm?", getContext(), this,
                    CANCEL_BUTTON_ID, wdRefId);
        } else if (id == MORE_OPTIONS_BUTTON_ID) {
            WithdrawRequest selectedWDRequest = (WithdrawRequest) view.getTag();
            Resources resources = Objects.requireNonNull(getActivity()).getResources();
            CharSequence[] accTypes = resources.getTextArray(R.array.wd_more_options);
            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
            for (CharSequence s : accTypes) {
                MenuItem item = popupMenu.getMenu().add(s);
                view.setTag(selectedWDRequest);
                item.setActionView(view);
            }
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }
    }

    @Override
    public boolean onMenuItemClick (MenuItem item) {
        String text = (String) item.getTitle();
        text = text.toLowerCase();
        int clickedOption = -1;
        int subCategory = -1;
        if (text.contains("view")) {
            clickedOption = 2;
            subCategory = 3;
        }
        else if (text.contains("all")) {
            wdStatus = -1;
            clickedOption = 1;
        } else if (text.contains("opened")) {
            wdStatus = 1;
            clickedOption = 1;
        } else if (text.contains("closed")) {
            wdStatus = 2;
            clickedOption = 1;
        } else if (text.contains("cancelled")) {
            wdStatus = 3;
            clickedOption = 1;
        } else if (text.contains("bank")) {
            clickedOption = 2;
            subCategory = 1;
        } else if (text.contains("receipt")) {
            clickedOption = 2;
            subCategory = 2;
        }
        if (clickedOption == 1) {
            startPosOffset = 0;
            fetchRecords();
        } else if (clickedOption == 2) {
            WithdrawRequest selectedWDRequest = (WithdrawRequest) item.getActionView().getTag();
            boolean isReqClosed = (selectedWDRequest.getReqStatus() == WithdrawReqState.CLOSED.getId());
            if (subCategory == 1) {
                String accDetails = getBenefeciaryAccountDetails(selectedWDRequest);
                Utils.showMessage("Details", accDetails, getContext(), null);
            } else if (subCategory == 3) {
                String closedCmts = "Closed comments updated after the withdraw request is closed";
                if (isReqClosed) {
                    closedCmts = selectedWDRequest.getClosedComents();
                }
                Utils.showMessage("Comments", closedCmts, getContext(), null);
            } else if (subCategory == 2) {
                System.out.println("In click");
                GetTask<byte[]> viewReceiptTask = Request.getReceiptTask(selectedWDRequest.getReceiptId(),
                        selectedWDRequest.getRequestType());
                viewReceiptTask.setCallbackResponse(this);
                Scheduler.getInstance().submit(viewReceiptTask);
            }
        }
        return true;
    }

    private String getBenefeciaryAccountDetails(WithdrawRequest wdRequest) {
        if (wdRequest.getRequestType() == WithdrawReqType.BY_PHONE.getId()) {
            WithdrawReqByPhone byPhone = wdRequest.getByPhone();
            String stringBuffer = "Pay to Phone Number : " +
                    byPhone.getPhNumber() +
                    "\n" +
                    "Using : " +
                    byPhone.getPaymentMethod() +
                    "\n";
            return stringBuffer;
        }
        if (wdRequest.getRequestType() == WithdrawReqType.BY_BANK.getId()) {
        }
        return null;
    }


    @Override
    public void handleResponse(int reqId, boolean exceptionThrown, boolean isAPIException, final Object response, Object helperObject) {
        Runnable run = () -> {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
        };
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(run);
        }

        if((exceptionThrown) && (!isAPIException)) {
            showErrShowHomeScreen((String) response);
            return;
        }
        if (reqId == Request.USER_WITHDRAW_LIST) {
            if (isAPIException) {
                showErrShowHomeScreen((String) response);
                return;
            }
            final WithdrawRequestsHolder result = (WithdrawRequestsHolder) response;
            run = () -> populateTable(result);
            if (activity != null) {
                activity.runOnUiThread(run);
            }
        } else if (reqId == Request.WITHDRAW_CANCEL) {
            final Boolean result = (Boolean) response;
            if (result) {
                String successMsg = getResources().getString(R.string.wd_placed_success);
                Button filterStatus = getView().findViewById(R.id.filterStatus);
                Snackbar snackbar = Snackbar.make(filterStatus, successMsg, Snackbar.LENGTH_LONG);
                snackbar.show();
                fetchRecords();
            }
        } else if (reqId == Request.WITHDRAW_RECEIPT) {
            final byte[] contents = (byte[]) response;
            if (contents == null) {
                System.out.println("Bytes is null");
                return;
            }
            System.out.println("This is in receipt view" + contents.length);
            run = () -> {
                ViewReceipt viewReceipt = new ViewReceipt((getContext()), contents, "Transferred Receipt");
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                viewReceipt.show(fragmentManager, "dialog");
            };
            if (activity != null) {
                activity.runOnUiThread(run);
            }
        }
    }
}

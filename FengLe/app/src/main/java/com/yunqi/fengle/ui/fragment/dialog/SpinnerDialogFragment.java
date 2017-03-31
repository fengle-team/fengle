package com.yunqi.fengle.ui.fragment.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.SpinnerBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 22:54
 * @Description:跟spinner差不多的dialog
 */

public class SpinnerDialogFragment extends DialogFragment {


    private ListView rvList;

    private SpinnerBean spinnerBean;

    public static SpinnerDialogFragment newInstance(SpinnerBean spinnerBean) {
        SpinnerDialogFragment dialogFragment = new SpinnerDialogFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("key",spinnerBean);
        dialogFragment.setArguments(mBundle);
        return dialogFragment;
//        return new SpinnerDialogFragment();
    }
//    public static SpinnerDialogFragment newInstance(String content, String okStr, String backStr) {
//        SpinnerDialogFragment dialogFragment = new SpinnerDialogFragment();
//        Bundle mBundle = new Bundle();
//        mBundle.putString("content", content);
//        mBundle.putString("okStr", okStr);
//        mBundle.putString("backStr", backStr);
//        dialogFragment.setArguments(mBundle);
//        return dialogFragment;
//    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spinnerBean = (SpinnerBean) getArguments().getSerializable("key");
//            type = getArguments().getInt("type", 0);
//            okStr = getArguments().getString("okStr");
//            backStr = getArguments().getString("backStr");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_spinner, null);
        initView(view);
        builder.setView(view);
        builder.setCancelable(true);
        setCancelable(true);
//        setDialogWidth(0.5f);
        return builder.create();
    }

    private void initData() {

    }

    private void initView(View view) {
        rvList = (ListView) view.findViewById(R.id.rvList);


//        Map<String, String> datavalue = new HashMap<String, String>();
//        datavalue.put("content", "活动类型");
//        Map<String, String> datavalue1 = new HashMap<String, String>();
//        datavalue1.put("content", "活动类型11");
//        Map<String, String> datavalue2 = new HashMap<String, String>();
//        datavalue2.put("content", "活动类型22");
//
        List<Map<String, String>> listems = new ArrayList<Map<String, String>>();
//        listems.add(datavalue);
//        listems.add(datavalue1);
//        listems.add(datavalue2);

        SimpleAdapter simplead = new SimpleAdapter(getActivity(), spinnerBean.getStrSpnnerMap(),
                R.layout.item_spinner, new String[]{"content"},
                new int[]{R.id.tv01});

        rvList.setAdapter(simplead);

        rvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onSpinnerDialogListener != null) {
                    onSpinnerDialogListener.onItemSelected(position,spinnerBean.getDataList().get(position));
                }
                dismiss();
            }
        });
    }



    private OnSimpleDialogListener listener;
    private OnBackDialogListener backListner;

    public void setOnSimpleDialogListener(OnSimpleDialogListener listener) {
        this.listener = listener;
    }

    public void setOnBackDialogListener(OnBackDialogListener backListner) {
        this.backListner = backListner;
    }

    public interface OnSimpleDialogListener {
        void onOk();
    }

    public interface OnBackDialogListener {
        void onBack();
    }

    public OnSpinnerDialogListener onSpinnerDialogListener;

    public void setListener(OnSpinnerDialogListener onSpinnerDialogListener) {
        this.onSpinnerDialogListener = onSpinnerDialogListener;
    }

    public interface OnSpinnerDialogListener {
        void onItemSelected(int position,SpinnerBean bean);
    }
}

package com.yunqi.fengle.ui.fragment.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.util.ToastUtil;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 22:54
 * @Description:(这里用一句话描述这个类的作用)
 */

public class SimpleDialogEditFragment extends DialogFragment implements View.OnClickListener {

    private EditText tvContent;
    private TextView tvOK;
    private TextView tvBack;

    private String content;
    private int type;
    private static final int TYPE_SINGLE = 0x01;
    private String okStr, backStr;

    public static SimpleDialogEditFragment newInstance(String content) {
       return newInstance(content,"","");
    }

    public static SimpleDialogEditFragment newInstance(String content, String okStr, String backStr) {
        SimpleDialogEditFragment dialogFragment = new SimpleDialogEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("content", content);
        mBundle.putString("okStr", okStr);
        mBundle.putString("backStr", backStr);
        dialogFragment.setArguments(mBundle);
        return dialogFragment;
    }


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
            content = getArguments().getString("content");
            type = getArguments().getInt("type", 0);
            okStr = getArguments().getString("okStr");
            backStr = getArguments().getString("backStr");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_simple_edit, null);
        initView(view);
        builder.setView(view);
        builder.setCancelable(true);
        setCancelable(true);
        Dialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
//        setDialogWidth(0.5f);
        return dialog;
    }

    private void initData() {

    }

    private void initView(View view) {
        tvContent = (EditText) view.findViewById(R.id.tvContent);
        tvOK = (TextView) view.findViewById(R.id.btOk);
        tvBack = (TextView) view.findViewById(R.id.btBack);
        tvOK.setOnClickListener(this);
        tvBack.setOnClickListener(this);
//        if (type == TYPE_SINGLE) {
//            view.findViewById(R.id.btBack).setVisibility(View.GONE);
//            view.findViewById(R.id.btOk).setBackgroundResource(R.drawable.selector_btn_orange_straight_bottom);
//        }
        if(!TextUtils.isEmpty(okStr)){
            tvOK.setText(okStr);
        }
        if(!TextUtils.isEmpty(backStr)){
            tvBack.setText(backStr);
        }
        tvContent.setHint(content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btBack://返回
                if (backListner != null) {
                    backListner.onBack();
                }
                dismiss();
                break;
            case R.id.btOk:
//                if (tvContent.getText().toString().equals("")) {
//                    ToastUtil.toast(getContext(),"内容不能为空");
//                    return;
//                }
                if (listener != null) {
                    listener.onOk(tvContent.getText().toString());
                }
                if (onSimpleSingleDialogListener != null) {
                    onSimpleSingleDialogListener.onOk(tvContent.getText().toString());
                }
                dismiss();
                break;
            default:
                break;
        }
    }


    private OnSimpleDialogEditListener listener;
    private OnBackDialogListener backListner;

    public void setOnSimpleDialogEditListener(OnSimpleDialogEditListener listener) {
        this.listener = listener;
    }
    public void setOnBackDialogListener(OnBackDialogListener backListner) {
        this.backListner = backListner;
    }

    public interface OnSimpleDialogEditListener {
        void onOk(String content);
    }
    public interface OnBackDialogListener {
        void onBack();
    }

    public OnSimpleDialogEditListener onSimpleSingleDialogListener;

    public void setOnSimpleSingleDialogListener(OnSimpleDialogEditListener onSimpleSingleDialogListener) {
        this.onSimpleSingleDialogListener = onSimpleSingleDialogListener;
    }

    public interface OnSimpleSingleDialogListener {
        void onOk();
    }
}

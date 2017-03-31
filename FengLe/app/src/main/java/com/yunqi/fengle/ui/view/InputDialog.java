package com.yunqi.fengle.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.util.ToastUtil;

/**
 * 输入框Dialog
 */

public class InputDialog extends Dialog {
    private Context context;
    private OnConfirmListener listener;
    private EditText edit;
    private TextView txtTip;
    private int maxNum;

    public InputDialog(Context context, int maxNum, OnConfirmListener listener) {
        super(context, R.style.MyDialog);
        this.listener = listener;
        this.context = context;
        this.maxNum = maxNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_input, null);
        setContentView(view);
        edit = (EditText) view.findViewById(R.id.edit);
        txtTip = (TextView) view.findViewById(R.id.txt_tip);
        String strTip = String.format(context.getString(R.string.tip_max_goods_num), maxNum);
        txtTip.setText(strTip);
        TextView btnConfirm = (TextView) view.findViewById(R.id.btn_confirm);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = edit.getText().toString();
                if (TextUtils.isEmpty(txt)) {
                    ToastUtil.showErrorToast(context, "发货数量不能为空!");
                    return;
                }
                int num = 0;
                try {
                    num=Integer.parseInt(txt);
                } catch (NumberFormatException e) {
                    ToastUtil.showErrorToast(context, "发货数量输入不正确!");
                    e.printStackTrace();
                    return;
                }
                if (num > maxNum) {
                    ToastUtil.showErrorToast(context, "不可超过最大发货数量!");
                    return;
                }
                listener.onText(num);
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        setCanceledOnTouchOutside(false);
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = d.widthPixels; // 高度设置为屏幕的
        dialogWindow.setAttributes(lp);
    }

    public interface OnConfirmListener {
        void onText(int num);
    }
}

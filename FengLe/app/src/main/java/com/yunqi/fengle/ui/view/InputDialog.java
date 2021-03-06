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
import com.yunqi.fengle.model.bean.Goods;
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
    private String tip;
    private String hint;
    private Goods goods;
    private int module;//0：表示退货 、开票等 1：发货 2：表示促销

    public InputDialog(Context context, int maxNum, String tip, String hint, Goods goods, OnConfirmListener listener) {
        super(context, R.style.MyDialog);
        this.listener = listener;
        this.context = context;
        this.maxNum = maxNum;
        this.goods = goods;
        this.tip = tip;
        this.hint = hint;
    }

    public void setModule(int module){
        this.module=module;
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
        String strHint = String.format(tip, maxNum);
        String strTip;
        if (!TextUtils.isEmpty(goods.goods_standard)) {
            strTip = goods.goods_name + "--" + goods.goods_standard;
        } else {
            strTip = goods.goods_name + "";
        }
        txtTip.setText(strTip);
        edit.setHint(strHint);
        TextView btnConfirm = (TextView) view.findViewById(R.id.btn_confirm);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = edit.getText().toString();
                if (TextUtils.isEmpty(txt)) {
                    ToastUtil.showErrorToast(context, "数量不能为空!");
                    return;
                }
                int num = 0;
                try {
                    num = Integer.parseInt(txt);
                } catch (NumberFormatException e) {
                    ToastUtil.showErrorToast(context, "数量输入不正确!");
                    e.printStackTrace();
                    return;
                }
                if (num > maxNum) {
                    ToastUtil.showErrorToast(context, "不可超过最大数量!");
                    return;
                }
                if (module==1&&num > goods.goods_plan) {
                    ToastUtil.showErrorToast(context, "不可超过计划数量!");
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

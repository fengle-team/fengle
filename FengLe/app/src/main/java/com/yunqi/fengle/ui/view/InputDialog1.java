package com.yunqi.fengle.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.util.ToastUtil;

/**
 * 输入框Dialog
 */

public class InputDialog1 extends Dialog {
    private Context context;
    private OnConfirmListener listener;
    private EditText edit;
    private TextView txtTip;
    private String tip;
    private String hint;

    public InputDialog1(Context context, String tip, String hint, OnConfirmListener listener) {
        super(context, R.style.MyDialog);
        this.listener = listener;
        this.context = context;
        this.tip = tip;
        this.hint = hint;

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
        txtTip.setText(tip);
        edit.setHint(hint);
        TextView btnConfirm = (TextView) view.findViewById(R.id.btn_confirm);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = edit.getText().toString();
                if (TextUtils.isEmpty(txt)) {
                    ToastUtil.showErrorToast(context, "输入内容不能为空!");
                    return;
                }
                listener.onText(txt);
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
        void onText(String txt);
    }
}

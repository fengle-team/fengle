package com.yunqi.fengle.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqi.fengle.R;

/**
 * 往来查询选择Dialog
 */

public class ContactSelectDialog extends Dialog {
    private Context context;
    private OnSelectListener listener;
    //打开详细类型 0：表示客户往来表 1：表示货物销售详情表
    private int type = 0;
    private RelativeLayout rlayoutCustomerContact;
    private RelativeLayout rlayoutSalesDetailTable;
    private TextView txtCustomerContact;
    private TextView txtSalesDetailTable;
    private ImageView checkCustomerContact;
    private ImageView checkSalesDetailTable;

    public ContactSelectDialog(Context context, OnSelectListener listener) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_contact_select, null);
        setContentView(view);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        rlayoutCustomerContact = (RelativeLayout) view.findViewById(R.id.rlayout_customer_contact);
        rlayoutSalesDetailTable = (RelativeLayout) view.findViewById(R.id.rlayout_sales_detail);
        txtCustomerContact = (TextView) view.findViewById(R.id.txt_customer_contact);
        txtSalesDetailTable = (TextView) view.findViewById(R.id.txt_sales_detail);
        checkCustomerContact = (ImageView) view.findViewById(R.id.check_customer_contact);
        checkSalesDetailTable = (ImageView) view.findViewById(R.id.check_sales_detail);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactSelectDialog.this.dismiss();
                listener.onSelect(type);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactSelectDialog.this.dismiss();
            }
        });
        MyOnClickListener myListener = new MyOnClickListener();
        rlayoutCustomerContact.setOnClickListener(myListener);
        rlayoutSalesDetailTable.setOnClickListener(myListener);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        setCanceledOnTouchOutside(true);
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = d.widthPixels; // 高度设置为屏幕的
        dialogWindow.setAttributes(lp);
    }

    class MyOnClickListener implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rlayout_customer_contact:
                    type = 0;
                    setCustomerContactSelect(true);
                    setSalesDetailSelect(false);
                    break;
                case R.id.rlayout_sales_detail:
                    type = 1;
                    setCustomerContactSelect(false);
                    setSalesDetailSelect(true);
                    break;
            }
        }
    }

    private void setCustomerContactSelect(boolean isSelect){
        if(isSelect){
            rlayoutCustomerContact.setBackgroundColor(context.getResources().getColor(R.color.dialog_bg2));
            txtCustomerContact.setTextColor(context.getResources().getColor(R.color.white));
            checkCustomerContact.setImageResource(R.drawable.checked);
        }
        else{
            rlayoutCustomerContact.setBackgroundResource(0);
            txtCustomerContact.setTextColor(context.getResources().getColor(R.color.color_selector2));
            checkCustomerContact.setImageResource(R.drawable.uncheck);
        }
    }
    private void setSalesDetailSelect(boolean isSelect){
        if(isSelect){
            rlayoutSalesDetailTable.setBackgroundColor(context.getResources().getColor(R.color.dialog_bg2));
            txtSalesDetailTable.setTextColor(context.getResources().getColor(R.color.white));
            checkSalesDetailTable.setImageResource(R.drawable.checked);
        }
        else{
            rlayoutSalesDetailTable.setBackgroundResource(0);
            txtSalesDetailTable.setTextColor(context.getResources().getColor(R.color.color_selector2));
            checkSalesDetailTable.setImageResource(R.drawable.uncheck);
        }
    }

    public interface OnSelectListener {
        void onSelect(int type);
    }


}

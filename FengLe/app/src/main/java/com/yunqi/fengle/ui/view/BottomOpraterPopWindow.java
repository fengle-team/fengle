package com.yunqi.fengle.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.yunqi.fengle.R;


/**
 */
public class BottomOpraterPopWindow extends PopupWindow {

    /**
     */
    private Button btnCommit;
    /**
     */
    private Button btnTemporary;
    /**
     */
    private Button btnCancel;
    private View mMenuView;




    public void setPopWindowTexts(String ...strs){
        btnCommit.setText(strs[0]);
        btnTemporary.setText(strs[1]);
        btnCancel.setText(strs[2]);
    }
    public void setOpraterType(int type){
        if(type==1){
            btnTemporary.setVisibility(View.GONE);
        }
    }

    @SuppressLint("InflateParams")
    public BottomOpraterPopWindow(Context context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_popwindow_bottom_oprater, null);
        btnCommit = (Button) mMenuView.findViewById(R.id.btn_commit);
        btnTemporary = (Button) mMenuView.findViewById(R.id.btn_temporary);
        btnCancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(itemsOnClick);
        btnTemporary.setOnClickListener(itemsOnClick);
        btnCommit.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnimation);
        ColorDrawable dw = new ColorDrawable(0x80000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

}

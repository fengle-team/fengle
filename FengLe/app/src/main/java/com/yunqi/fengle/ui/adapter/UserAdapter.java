package com.yunqi.fengle.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.UserBean;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:55
 * @Description: 我的客户 adapter
 */

public class UserAdapter extends BaseQuickAdapter<UserBean,BaseViewHolder> {
    public UserAdapter(List<UserBean> data) {
        super(R.layout.item_under,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        TextView txtName=helper.getView(R.id.txt_name);
        txtName.setText(item.real_name);
        TextView txtPhone=helper.getView(R.id.txt_phone);
        txtPhone.setText(item.mobile);

    }
}

package com.yunqi.fengle.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.MessageResponse;
import com.yunqi.fengle.model.response.NoticeResponse;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 12:11
 * @Description: {@link com.yunqi.fengle.ui.activity.NoticeActivity}
 */

public class MessageAdapter extends BaseQuickAdapter<MessageResponse,BaseViewHolder>{

    public MessageAdapter(List<MessageResponse> data) {
        super(R.layout.item_message,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageResponse item) {
        TextView tvContent = helper.getView(R.id.tvContent);
        TextView tvTime = helper.getView(R.id.tvTime);
        TextView tvPer = helper.getView(R.id.tvPer);


        tvContent.setText(item.getMsg_content());
        tvTime.setText(item.getCreate_time());
        tvPer.setText(item.getCreator_id());
    }
}

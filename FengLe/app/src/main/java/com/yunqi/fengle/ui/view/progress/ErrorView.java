package com.yunqi.fengle.ui.view.progress;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yunqi.fengle.R;


/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:19
 * @Description:
 */
public class ErrorView extends FrameLayout implements OnClickListener {

	/**
	 * 重试接口
	 * 
	 * @author wu
	 * 
	 */
	public interface OnRetryListener {
		void onRetry();
	}

	private OnRetryListener listener;

	public void setRetryListener(OnRetryListener listener) {
		this.listener = listener;
	}

	// private ImageView ivErrorIcon;
	private TextView tvError;
	private TextView tvRetry;
	private TextView tvErrorSub;

	public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	public ErrorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ErrorView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		View errorView = LayoutInflater.from(getContext()).inflate(R.layout.layout_error, this, false);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(errorView, params);
		// ivErrorIcon = (ImageView) errorView.findViewById(R.id.ivErrorIcon);
		tvError = (TextView) errorView.findViewById(R.id.tvError);
		tvRetry = (TextView) errorView.findViewById(R.id.tvRetry);
		tvErrorSub = (TextView) errorView.findViewById(R.id.tvErrorSub);
		tvRetry.setOnClickListener(this);
		tvError.setOnClickListener(this);
	}

	/**
	 * 设置错误内容
	 * 
	 * @param error
	 */
	public void setError(String error) {
		if (!TextUtils.isEmpty(error)) {
			tvError.setText(error);
		}
	}

	/**
	 * 设置子错误内容
	 * 
	 * @param subError
	 */
	public void setSubError(String subError) {
		if (TextUtils.isEmpty(subError) == false) {
			tvErrorSub.setText(subError);
			tvErrorSub.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 显示重试按钮
	 */
	public void showRetry() {
		tvRetry.setVisibility(View.VISIBLE);
	}

	public void hideRetry() {
		tvRetry.setVisibility(View.GONE);
	}

	/**
	 * 设置重试提示文本
	 * 
	 * @param retryText
	 */
	public void setRetryText(String retryText) {
		tvRetry.setText(retryText);
	}

	@Override
	public void onClick(View v) {
		if ((v.getId() == R.id.tvRetry || v.getId() == R.id.tvError) && listener != null) {
			listener.onRetry();
		}
	}

}

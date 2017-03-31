package com.yunqi.fengle.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.component.ImageLoader;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.load(context,url,imageView);
		return imageView;
	}
}

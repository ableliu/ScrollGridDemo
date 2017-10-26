package win.aspring.classifydemo;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import win.aspring.library.interf.ImageLoaderListener;

/**
 * ============================================
 * 文件名：ImageLoader.java
 * 作者：Admin
 * 日期：2017-10-26 16:08
 * 更新：2017-10-26 16:08
 * 描述：图片加载器
 * 版本：1.0
 * ============================================
 */
public class ImageLoader implements ImageLoaderListener {
    @Override
    public void displayView(Context context, String imagePath, ImageView imageView) {
        Glide.with(context).load(imagePath).into(imageView);
    }
}

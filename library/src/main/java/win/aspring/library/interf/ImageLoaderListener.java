package win.aspring.library.interf;

import android.content.Context;
import android.widget.ImageView;

/**
 * ============================================
 * 文件名：ImageLoaderListener.java
 * 作者：Admin
 * 日期：2017-10-26 15:12
 * 更新：2017-10-26 15:12
 * 描述：必须实现图片加载方法，为了减少第三方库的引用
 * 版本：1.0
 * ============================================
 */
public interface ImageLoaderListener {

    void displayView(Context context, String imagePath, ImageView imageView);
}

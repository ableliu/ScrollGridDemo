package win.aspring.library.interf;

import android.view.View;

/**
 * ============================================
 * 文件名：OnClassifyClickListener.java
 * 作者：Admin
 * 日期：2017/10/26 14:48
 * 更新：2017/10/26 14:48
 * 描述：每个分类点击事件接口
 * 版本：1.0
 * ============================================
 */
public interface OnClassifyClickListener {

    /**
     * 分类的点击事件
     *
     * @param view     布局文件
     * @param position 分类的真实位置
     */
    void onClassifyClick(View view, int position);
}

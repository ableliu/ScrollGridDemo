package win.aspring.library.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * ============================================
 * 文件名：ViewPagerAdapter.java
 * 作者：Admin
 * 日期：2017/10/26 14:40
 * 更新：2017/10/26 14:40
 * 描述：ViewPager的适配器
 * 版本：1.0
 * 版权：Copyright （C） 2017 HISOFT HENAN CO. LTD.
 * ============================================
 */
public class ViewPagerAdapter extends PagerAdapter {
    private SparseArray<View> mList;

    public ViewPagerAdapter(SparseArray<View> list) {
        this.mList = list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

package win.aspring.library.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import win.aspring.library.R;
import win.aspring.library.interf.ClassifyBean;
import win.aspring.library.interf.ImageLoaderListener;

/**
 * ============================================
 * 文件名：ClassifyGridAdapter.java
 * 作者：Admin
 * 日期：2017/10/26 15:03
 * 更新：2017/10/26 15:03
 * 描述：分类适配器
 * 版本：1.0
 * 版权：Copyright （C） 2017 HISOFT HENAN CO. LTD.
 * ============================================
 */
public class ClassifyGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<? extends ClassifyBean> mList;
    private LayoutInflater inflater;
    private ImageLoaderListener mImageLoaderListener;

    public void setImageLoaderListener(ImageLoaderListener imageLoaderListener) {
        mImageLoaderListener = imageLoaderListener;
    }

    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int mCurIndex;
    /**
     * 每一页显示的个数
     */
    private int mPageSize;

    public ClassifyGridAdapter(Context context, List<? extends ClassifyBean> modelList, int curIndex, int pageSize) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.mList = modelList;
        this.mCurIndex = curIndex;
        this.mPageSize = pageSize;
    }

    @Override
    public int getCount() {
        return mList.size() > (mCurIndex + 1) * mPageSize ? mPageSize : (mList.size() - mCurIndex * mPageSize);
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position + mCurIndex * mPageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mCurIndex * mPageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_classify, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = convertView.findViewById(R.id.item_name);
            viewHolder.iv = convertView.findViewById(R.id.item_logo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mCurIndex * mPageSize
         */
        int pos = position + mCurIndex * mPageSize;
        ClassifyBean model = mList.get(pos);
        viewHolder.tv.setText(model.getName());

        String url = model.getImagePath();
        Log.e("ClassifyGridAdapter", "getView: url = " + url);
        mImageLoaderListener.displayView(mContext, url, viewHolder.iv);

        return convertView;
    }

    private class ViewHolder {
        private TextView tv;
        private ImageView iv;
    }
}

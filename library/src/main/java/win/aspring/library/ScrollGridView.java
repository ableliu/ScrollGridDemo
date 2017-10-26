package win.aspring.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import win.aspring.library.adapter.ClassifyGridAdapter;
import win.aspring.library.adapter.ViewPagerAdapter;
import win.aspring.library.interf.ClassifyBean;
import win.aspring.library.interf.ImageLoaderListener;
import win.aspring.library.interf.OnClassifyClickListener;

/**
 * ============================================
 * 文件名：ScrollGridView.java
 * 作者：Abel
 * 日期：2017-09-20 11:03
 * 描述：仿美团首页滚动视图
 * 版本：1.0
 * ============================================
 */
public class ScrollGridView extends FrameLayout implements ViewPager.OnPageChangeListener {
    public static final String TAG = ScrollGridView.class.getSimpleName();
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#666666");
    private static final int DEFAULT_INDICATOR_DIAMETER = 8;
    private static final int DEFAULT_SELECTED_DRAWABLE = R.drawable.selected_radius;
    private static final int DEFAULT_UNSELECT_DRAWABLE = R.drawable.gray_radius;
    private static final int DEFAULT_INDICATOR_LEFT_MARGIN = 5;
    private static final int DEFAULT_INDICATOR_RIGHT_MARGIN = 5;
    private static final int DEFAULT_INDICATOR_POSITION = Gravity.CENTER;
    private static final boolean DEFAULT_IS_SHOW_INDICATOR = true;

    private int mTextColor;
    private int mIndicatorDiameter;
    private int mSelectedDrawable;
    private int mUnSelectDrawable;
    private int mIndicatorLeftMargin;
    private int mIndicatorRightMargin;
    private int mIndicatorPosition;
    private boolean mIsShowIndicator;

    /**
     * GirdView的容器
     */
    private ViewPager mViewPager;
    /**
     * 下边的圆点容器
     */
    private LinearLayout mIndicator;
    /**
     * 总的页数
     */
    private int mPageCount;

    /**
     * GirdView列表
     */
    private SparseArray<View> mPagerList;

    /**
     * 每行显示的个数
     */
    private int mColumnSize;

    /**
     * 每页显示的行数
     */
    private int mRowSize;

    /**
     * 每页显示数量
     */
    private int mPageSize;

    /**
     * 当前显示的是第几页
     */
    private int mCurrentIndex;

    /**
     * 分类数据
     */
    private List<? extends ClassifyBean> mList;

    /**
     * 分类点击事件
     */
    private OnClassifyClickListener mOnClassifyClickListener;

    public void setOnClassifyClickListener(OnClassifyClickListener onClassifyClickListener) {
        mOnClassifyClickListener = onClassifyClickListener;
    }

    /**
     * 图片加载器
     */
    private ImageLoaderListener mImageLoaderListener;

    public void setImageLoaderListener(ImageLoaderListener imageLoaderListener) {
        mImageLoaderListener = imageLoaderListener;
    }

    public ScrollGridView(Context context) {
        super(context);

        initView();
    }

    public ScrollGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollGridView);
        mTextColor = a.getColor(R.styleable.ScrollGridView_text_color, DEFAULT_TEXT_COLOR);
        mIndicatorDiameter = a.getDimensionPixelOffset(R.styleable.ScrollGridView_indicator_diameter, DEFAULT_INDICATOR_DIAMETER);

        a.recycle();

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_library, this);
        mViewPager = rootView.findViewById(R.id.scroll_viewpager);
        mIndicator = rootView.findViewById(R.id.scroll_dot);
    }

    /**
     * 设置数据
     *
     * @param list 分类列表
     */
    public void setData(List<? extends ClassifyBean> list) {
        setData(list, mRowSize, mColumnSize);
    }

    /**
     * 设置数据
     *
     * @param list       分类列表
     * @param rowSize    显示行数
     * @param columnSize 每行显示数量（列数）
     */
    public void setData(List<? extends ClassifyBean> list, int rowSize, int columnSize) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("the classify list is not null or empty!");
        }
        if (rowSize <= 0) {
            throw new RuntimeException("The number of row is not less than zero！");
        }
        if (rowSize <= 0) {
            throw new RuntimeException("The number of columns is not less than zero！");
        }

        this.mList = list;
        this.mRowSize = rowSize;
        this.mColumnSize = columnSize;

        mPageSize = mRowSize * mColumnSize;

        initClassify();
    }

    /**
     * 初始化控件
     */
    private void initClassify() {
        //总的页数=总数/每页数量，并取整
        mPageCount = (int) Math.ceil(mList.size() * 1.0 / mPageSize);
        mPagerList = new SparseArray<>(mPageCount);
        for (int i = 0; i < mPageCount; i++) {
            // 每个页面都是inflate出一个新实例
            GridView gridView = new GridView(getContext());
            gridView.setNumColumns(mColumnSize);
            ClassifyGridAdapter gridAdapter = new ClassifyGridAdapter(getContext(), mList, i, mPageSize);
            if (mImageLoaderListener != null) {
                gridAdapter.setImageLoaderListener(mImageLoaderListener);
            } else {
                throw new RuntimeException("must be implements ImageLoaderListener!");
            }
            gridView.setAdapter(gridAdapter);
            mPagerList.put(i, gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //计算当前分类在所有分类的位置
                    int pos = position + mCurrentIndex * mPageSize;
                    if (mOnClassifyClickListener != null) {
                        mOnClassifyClickListener.onClassifyClick(view, pos);
                    }
                }
            });
        }
        mViewPager.addOnPageChangeListener(this);
        //设置适配器
        mViewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        mViewPager.setFocusable(true);
        //设置圆点
        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    private void setOvalLayout() {
        mIndicator.removeAllViews();
        mIndicator.setGravity(Gravity.CENTER);
        for (int i = 0; i < mPageCount; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px(8), dp2px(8));
            params.leftMargin = dp2px(5);
            params.rightMargin = dp2px(5);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageResource(R.drawable.selected_radius);
            } else {
                imageView.setImageResource(R.drawable.gray_radius);
            }
            mIndicator.addView(imageView);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 取消圆点选中
        ((ImageView) mIndicator.getChildAt(mCurrentIndex)).setImageResource(R.drawable.gray_radius);
        // 圆点选中
        ((ImageView) mIndicator.getChildAt(position)).setImageResource(R.drawable.selected_radius);

        mCurrentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    private int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

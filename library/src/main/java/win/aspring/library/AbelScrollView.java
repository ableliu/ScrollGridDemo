package win.aspring.library;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * ============================================
 * 文件名：AbelScrollView.java
 * 作者：Abel
 * 日期：2017-09-20 11:03
 * 描述：仿美团首页滚动视图
 * 版本：1.0
 * ============================================
 */
public class AbelScrollView extends FrameLayout {

    /**
     * GirdView的容器
     */
    private ViewPager mViewPager;
    /**
     * 下边的圆点容器
     */
    private LinearLayout mIndicator;


    public AbelScrollView(Context context) {
        this(context, null);
    }

    public AbelScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbelScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_library, this);
        mViewPager = rootView.findViewById(R.id.abel_viewpager);
        mIndicator = rootView.findViewById(R.id.able_dot);
    }
}

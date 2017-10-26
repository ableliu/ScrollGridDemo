# ScrollGridDemo
项目首页有个分类，UI设计给的是八种分类，每排四个，刚好两排；但是产品说客户只有六种分类，这个需要改。因此，UI设计建议说，那就做成两屏，这页显示四个，滑动到另外一页显示两个。因此以前写的效果不能用了，只好重新写了；记得美团的首页也有这样的效果，想着在网上搜一个就不用造轮子了，但是没有发现中意的，因此决定自己造一个轮子。

说是造轮子，也只是把布局放在一个View中，懒省事继承了FrameLayout,没有测量子部局和摆放子部局，使用的时候还是多有不便。

主要摆放了一个ViewPager和一个LinearLayout，直接上代码：

```

    private void initClassify() {
        //总的页数=总数/每页数量，并取整
        mPageCount = (int) Math.ceil(mList.size() * 1.0 / mPageSize);
        mPagerList = new SparseArray<>(mPageCount);
        for (int i = 0; i < mPageCount; i++) {
            // 每个页面都是inflate出一个新实例
            GridView gridView = new GridView(getContext());
            gridView.setNumColumns(mColumnSize);
            ClassifyGridAdapter gridAdapter = new ClassifyGridAdapter(getContext(), mList, i, mPageSize);
            gridAdapter.setTextColor(mTextColor);
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

        if (mIsShowIndicator) {
            mIndicator.setVisibility(VISIBLE);
            //设置圆点
            setOvalLayout();
        } else {
            mIndicator.setVisibility(GONE);
        }
    }
    
```

这是初始化ViewPager的主要代码，为每个页面创建一个GridView，存放在mPageList中，然后ViewPagerAdapter中管理，添加或者移除页面。

```

    private void setOvalLayout() {
        mIndicator.removeAllViews();
        mIndicator.setGravity(mIndicatorPosition);
        for (int i = 0; i < mPageCount; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorDiameter, mIndicatorDiameter);
            params.leftMargin = mIndicatorLeftMargin;
            params.rightMargin = mIndicatorRightMargin;
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageDrawable(mSelectedDrawable);
            } else {
                imageView.setImageDrawable(mUnSelectDrawable);
            }
            mIndicator.addView(imageView);
        }
    }

```

这是设置下边的圆点，可以自定义选中图标和未选中图标，也可以控制导航圆点是否显示。

使用方法，请看demo。

`最后请注意：分类的实体类一定要实现ClassifyBean接口和实现加载图片接口ImageLoaderListener,否则会报异常。`

```
mScrollGridView.setImageLoaderListener(new ImageLoader());
```

##### License

Copyright 2017 Abel

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
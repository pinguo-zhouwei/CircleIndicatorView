package com.zhouwei.circleindicatorview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zhouwei.indicatorview.CircleIndicatorView;
import com.zhouwei.indicatorview.DisplayUtils;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;
    private CircleIndicatorView mIndicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        // 初始化ViewPager 相关
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);

        mIndicatorView = (CircleIndicatorView) findViewById(R.id.indicator_view);
        // 关联ViewPager
        mIndicatorView.setUpWithViewPager(mViewPager);

        CircleIndicatorView indicatorView1 = (CircleIndicatorView) findViewById(R.id.indicator_view1);
        CircleIndicatorView indicatorView2 = (CircleIndicatorView) findViewById(R.id.indicator_view2);

        indicatorView1.setUpWithViewPager(mViewPager);
        indicatorView2.setUpWithViewPager(mViewPager);

        // 在代码中设置相关属性
        CircleIndicatorView indicatorView = (CircleIndicatorView) findViewById(R.id.indicator_view3);
        // 设置半径
        indicatorView.setRadius(DisplayUtils.dpToPx(15));
        // 设置Border
        indicatorView.setBorderWidth(DisplayUtils.dpToPx(2));

        // 设置文字颜色
        indicatorView.setTextColor(Color.WHITE);
        // 设置选中颜色
        indicatorView.setSelectColor(Color.parseColor("#FEBB50"));
        //
        indicatorView.setDotNormalColor(Color.parseColor("#E38A7C"));
        // 设置指示器间距
        indicatorView.setSpace(DisplayUtils.dpToPx(10));
        // 设置模式
        indicatorView.setFillMode(CircleIndicatorView.FillMode.LETTER);
        // 设置点击Indicator可以切换ViewPager
        indicatorView.setEnableClickSwitch(true);

        // 最重要的一步：关联ViewPager
        indicatorView.setUpWithViewPager(mViewPager);

        indicatorView.setOnIndicatorClickListener(new CircleIndicatorView.OnIndicatorClickListener() {
            @Override
            public void onSelected(int position) {

            }
        });


    }
}

package com.zhouwei.indicatorview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/5/22.
 */

public class CircleIndicatorView extends View implements ViewPager.OnPageChangeListener{
    private static final String LETTER[] = new String[]{"A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
   // private int mSelectColor = Color.parseColor("#E38A7C");
    private int mSelectColor = Color.parseColor("#FFFFFF");
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private int mCount; // indicator 的数量
    private int mRadius;//半径
    private int mStrokeWidth;//border
    private int mTextColor;// 小圆点中文字的颜色
    private int mDotNormalColor;// 小圆点默认颜色
    private int mSpace = 0;// 圆点之间的间距
    private List<Indicator> mIndicators;
    private int mSelectPosition = 0; // 选中的位置
    private FillMode mFillMode = FillMode.NONE;// 默认只有小圆点
    private ViewPager mViewPager;
    private OnIndicatorClickListener mOnIndicatorClickListener;
    public CircleIndicatorView(Context context) {
        super(context);
        init();
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttr(context,attrs);
        init();
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context,attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttr(context,attrs);
        init();
    }

    private void init(){

        mCirclePaint = new Paint();
        mCirclePaint.setDither(true);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new Paint();
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        // 默认值
        mIndicators = new ArrayList<>();

        initValue();

    }

    private void initValue(){
        mCirclePaint.setColor(mDotNormalColor);
        mCirclePaint.setStrokeWidth(mStrokeWidth);

        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mRadius);
    }

    /**
     * 获取自定义属性
     * @param context
     * @param attrs
     */
    private void getAttr(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CircleIndicatorView);
        mRadius = (int) typedArray.getDimensionPixelSize(R.styleable.CircleIndicatorView_indicatorRadius,DisplayUtils.dpToPx(6));
        mStrokeWidth = (int) typedArray.getDimensionPixelSize(R.styleable.CircleIndicatorView_indicatorBorderWidth,DisplayUtils.dpToPx(2));
        mSpace = typedArray.getDimensionPixelSize(R.styleable.CircleIndicatorView_indicatorSpace,DisplayUtils.dpToPx(5));
        // color
        mTextColor = typedArray.getColor(R.styleable.CircleIndicatorView_indicatorTextColor,Color.BLACK);
        mSelectColor = typedArray.getColor(R.styleable.CircleIndicatorView_indicatorSelectColor,Color.WHITE);
        mDotNormalColor = typedArray.getColor(R.styleable.CircleIndicatorView_indicatorColor,Color.GRAY);

        int fillMode = typedArray.getInt(R.styleable.CircleIndicatorView_fill_mode,2);
        if(fillMode == 0){
            mFillMode = FillMode.LETTER;
        }else if(fillMode == 1){
            mFillMode = FillMode.NUMBER;
        }else{
            mFillMode = FillMode.NONE;
        }
        typedArray.recycle();
    }

    /**
     * 测量每个圆点的位置
     */
    private void measureIndicator(){
        mIndicators.clear();
        float cx = 0;
        for(int i=0;i<mCount;i++){
            Indicator indicator = new Indicator();
            if( i== 0){
                cx = mRadius + mStrokeWidth;
            }else{
                cx += (mRadius + mStrokeWidth) * 2 +mSpace;
            }

            indicator.cx = cx;
            indicator.cy = getMeasuredHeight() / 2;

            mIndicators.add(indicator);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = (mRadius+mStrokeWidth)* 2 * mCount + mSpace *(mCount - 1);
        int height = mRadius * 2 + mSpace * 2;

        setMeasuredDimension(width,height);

        measureIndicator();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for(int i=0;i<mIndicators.size();i++){

            Indicator indicator = mIndicators.get(i);
            float x = indicator.cx;

            float y = indicator.cy;

            if(mSelectPosition == i){
                mCirclePaint.setStyle(Paint.Style.FILL);
                mCirclePaint.setColor(mSelectColor);
            }else{
                mCirclePaint.setColor(mDotNormalColor);
                if(mFillMode != FillMode.NONE){
                    mCirclePaint.setStyle(Paint.Style.STROKE);
                }else{
                    mCirclePaint.setStyle(Paint.Style.FILL);

                }
            }
            canvas.drawCircle(x,y, mRadius, mCirclePaint);

            // 绘制小圆点中的内容
            if(mFillMode != FillMode.NONE){
                String text = "";
                if(mFillMode == FillMode.LETTER){
                    if(i >= 0 && i<LETTER.length){
                        text = LETTER[i];
                    }
                }else{
                    text = String.valueOf(i+1);
                }
                Rect bound = new Rect();
                mTextPaint.getTextBounds(text,0,text.length(),bound);
                int textWidth = bound.width();
                int textHeight = bound.height();

                float textStartX = x - textWidth / 2;
                float textStartY = y + textHeight / 2;
                canvas.drawText(text,textStartX,textStartY, mTextPaint);
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPoint = 0;
        float yPoint = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xPoint = event.getX();
                yPoint = event.getY();
                handleActionDown(xPoint,yPoint);
                break;

        }

        return super.onTouchEvent(event);
    }

    private void handleActionDown(float xDis,float yDis){
        for(int i=0;i<mIndicators.size();i++){
            Indicator indicator = mIndicators.get(i);
            if(xDis < (indicator.cx + mRadius+mStrokeWidth)
                    && xDis >=(indicator.cx - (mRadius + mStrokeWidth))
                    && yDis >= (yDis - (indicator.cy+mStrokeWidth))
                    && yDis <(indicator.cy+mRadius+mStrokeWidth)){
                 // 找到了点击的Indicator
                 mViewPager.setCurrentItem(i,false);
                 // 回调
                if(mOnIndicatorClickListener!=null){
                    mOnIndicatorClickListener.onSelected(i);
                }
                break;
            }
        }
    }

    public void setOnIndicatorClickListener(OnIndicatorClickListener onIndicatorClickListener) {
        mOnIndicatorClickListener = onIndicatorClickListener;
    }

    private void setCount(int count) {
        mCount = count;
        invalidate();
    }

    /**
     * 设置 border
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth){
        mStrokeWidth = borderWidth;
        initValue();
    }

    /**
     * 设置文字的颜色
     * @param textColor
     */
    public void setTextColor(int textColor) {
        mTextColor = textColor;
        initValue();
    }

    /**
     * 设置选中指示器的颜色
     * @param selectColor
     */
    public void setSelectColor(int selectColor) {
        mSelectColor = selectColor;
    }

    /**
     *  设置指示器默认颜色
     * @param dotNormalColor
     */
    public void setDotNormalColor(int dotNormalColor) {
        mDotNormalColor = dotNormalColor;
        initValue();
    }

    /**
     * 设置选中的位置
     * @param selectPosition
     */
    public void setSelectPosition(int selectPosition) {
        mSelectPosition = selectPosition;
    }

    /**
     * 设置Indicator 模式
     * @param fillMode
     */
    public void setFillMode(FillMode fillMode) {
        mFillMode = fillMode;
    }

    /**
     * 设置Indicator 半径
     * @param radius
     */
    public void setRadius(int radius) {
        mRadius = radius;
        initValue();
    }

    public void setSpace(int space) {
        mSpace = space;
    }

    /**
     *  与ViewPager 关联
     * @param viewPager
     */
    public void setUpWithViewPager(ViewPager viewPager){
        releaseViewPager();
        if(viewPager == null){
            return;
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        int count = mViewPager.getAdapter().getCount();
        setCount(count);
    }

    /**
     * 重置ViewPager
     */
    private void releaseViewPager(){
        if(mViewPager!=null){
            mViewPager.removeOnPageChangeListener(this);
            mViewPager = null;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mSelectPosition = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * Indicator 点击回调
     */
    public interface OnIndicatorClickListener{
        public void onSelected(int position);
    }


    public static class Indicator{
        public float cx; // 圆心x坐标
        public float cy; // 圆心y 坐标
    }

    public enum FillMode{
        LETTER,
        NUMBER,
        NONE
    }


}

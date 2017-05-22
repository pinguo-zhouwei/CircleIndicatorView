[![Release](https://jitpack.io/v/pinguo-zhouwei/CircleIndicatorView.svg)](https://jitpack.io/#pinguo-zhouwei/CircleIndicatorView)
### An IndicatorView For Android 

一个轻量级的配合ViewPager 使用的IndicatorView(指示器)。

效果图如下：

![IndicatorView](indicatorView.gif)

### 特性
它有一下特性：

**1, 指示器只展示成一个圆点**

**2, 指示器展示一个圆点，圆点中间用字母标出顺序(A,B,C,D....)**

**3, 指示器展示一个圆点，圆点中间用数字标顺 （1，2，3，4 ...）**

**4 ,配置灵活，简单**

**5 ,支持点击Indicator 来切换View Pager**

### Dependency

**1, 最外层build.gradle添加如下代码：**
```java
   allprojects {
       repositories {
	...
	maven { url 'https://jitpack.io' }
   }
}
```

**2, app 层buid.gradle dependencies 中 添加如下代码：**

```java
compile 'com.github.pinguo-zhouwei:CircleIndicatorView:v1.0.0'
	
```


### 自定义属性

| 属性名      | 属性意义   |  取值  |
| --------   | -----:   | :----: |
| indicatorRadius       | 设置指示器圆点的半径     |   单位为 dp 的值   |
| indicatorBorderWidth        | 设置指示器的border      |   单位为 dp 的值     |
| indicatorSpace        | 设置指示器之间的距离      |   单位为 dp 的值     |
| indicatorTextColor        | 设置指示器中间的文字颜色      |   颜色值，如：＃FFFFFF    |
| indicatorColor        | 设置指示器圆点的颜色      |   颜色值    |
| indicatorSelectColor        | 设置指示器选中的颜色      |   颜色值    |
| fill_mode        |   设置指示器的模式    |   枚举值：有三种，分别是letter,number和none|

### 使用方法

** 1, xml 添加CircleIndicatorView,配置相关属性 **

  ```java
   <com.zhouwei.indicatorview.CircleIndicatorView
           android:id="@+id/indicator_view"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_alignParentBottom="true"
           android:layout_marginBottom="50dp"
           android:layout_centerHorizontal="true"
           app:indicatorSelectColor="#00A882"
           app:fill_mode="letter"
           app:indicatorBorderWidth="2dp"
           app:indicatorRadius="8dp"
           app:indicatorColor="@color/colorAccent"
           app:indicatorTextColor="@android:color/white"
           />
  ```
  
  **2, 在代码中与ViewPager 关联**
  
  ```java
         // 初始化ViewPager 相关
          mViewPager = (ViewPager) findViewById(R.id.viewpager);
          mPagerAdapter = new ViewPagerAdapter();
          mViewPager.setAdapter(mPagerAdapter);
  
          mIndicatorView = (CircleIndicatorView) findViewById(R.id.indicator_view);
          // 关联ViewPager 
          mIndicatorView.setUpWithViewPager(mViewPager);
  ```
  
  
  **上面所有列举的属性，出了在xml 中配置外，也可以在代码中设置,如下：**
  
  ```java
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
  
          // 最重要的一步：关联ViewPager
          indicatorView.setUpWithViewPager(mViewPager);
  ```
  
### License
  
  ```
     Copyright (C) 2017 zhouwei
  
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
  
         http://www.apache.org/licenses/LICENSE-2.0
  
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
  ```
  
  

package xiaochen.you.meituan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import xiaochen.you.meituan.widget.NestedScrollView;

public class MainActivity extends AppCompatActivity {
    /**
     * 移除并放大的模块, 悬浮模块(标题),　内容中的标题
     */
    private View rl_top, ll_float, ll_title;
    /**
     * 有反弹的滑动控件
     */
    private NestedScrollView sv_root;
    /**
     * 滑动组中的内容
     */
    private LinearLayout ll_content;
    /**
     * fragment 模块单选组
     */
    private RadioGroup rg_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigUtils.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("游小陈");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        rl_top = findViewById(R.id.rl_top);
        ll_float = findViewById(R.id.ll_float);
        ll_title = findViewById(R.id.ll_title);
        sv_root = (NestedScrollView) findViewById(R.id.sv_root);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        rg_group = (RadioGroup) findViewById(R.id.rg_group);
        initView();
        initFragmentGroup();
    }

    private void initView() {
        sv_root.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 0) {//往上滑动
                    int height = rl_top.getHeight();
                    if (height != ll_content.getPaddingTop()) {//如果滑动时高度有误先矫正高度
                        ViewGroup.LayoutParams layoutParams = rl_top.getLayoutParams();
                        layoutParams.height = ll_content.getPaddingTop();
                        rl_top.setLayoutParams(layoutParams);
                    }
                    boolean overTitle = scrollY >= height;
                    ll_float.setVisibility(overTitle ? View.VISIBLE : View.GONE);
                    ll_title.setVisibility(overTitle ? View.INVISIBLE : View.VISIBLE);
                    rl_top.setVisibility(overTitle ? View.GONE : View.VISIBLE);
                    rl_top.scrollTo(0, scrollY / 3);
                } else {//下拉滑动
                    rl_top.scrollTo(0, 0);//不能有滑动偏移
                    ViewGroup.LayoutParams layoutParams = rl_top.getLayoutParams();
                    layoutParams.height = ll_content.getPaddingTop() - scrollY;
                    rl_top.setLayoutParams(layoutParams);
                }
            }
        });
    }

    private void initFragmentGroup() {
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.f1:
                        replaceFragment(TestFragment.newInstance(0));
                        break;
                    case R.id.f2:
                        replaceFragment(TestFragment.newInstance(1));
                        break;
                }
            }
        });
        replaceFragment(TestFragment.newInstance(0));
    }

    private void replaceFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fl_fragment, f).commit();
    }

}

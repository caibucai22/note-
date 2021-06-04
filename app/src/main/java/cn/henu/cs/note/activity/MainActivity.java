package cn.henu.cs.note.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import cn.henu.cs.note.adapter.MyFragmentPagerAdapter;
import cn.henu.cs.note.R;
import cn.henu.cs.note.fragment.favorites_Fragment;
import cn.henu.cs.note.fragment.home_fragment;
import cn.henu.cs.note.fragment.my_Fragment;

public class MainActivity extends BaseActivity implements View.OnClickListener, AbsListView.RecyclerListener {

    private ViewPager2 myViewPager2;
    //底部导航栏的组件
    private LinearLayout homeLLayout, favoritesLLayout, myLLayout;
    private ImageView iv_home, iv_favorites, iv_my, iv_Current;
    private TextView t_home, t_favorites, t_my, t_Current;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private MyFragmentPagerAdapter pagerAdapter;

    private SharedPreferences sharedPreferences;


    @Override
    protected int initLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {

        homeLLayout = findViewById(R.id.id_bottom_home);
        favoritesLLayout = findViewById(R.id.id_bottom_favorites);
        myLLayout = findViewById(R.id.id_bottom_my);

        iv_home = findViewById(R.id.iv_bottom_home);
        iv_favorites = findViewById(R.id.iv_bottom_favorites);
        iv_my = findViewById(R.id.iv_bottom_my);

        t_home = findViewById(R.id.text_bottom_home);
        t_favorites = findViewById(R.id.text_bottom_favorites);
        t_my = findViewById(R.id.text_bottom_my);

        //给三个LinearLayout注册监听器
        homeLLayout.setOnClickListener(this);
        favoritesLLayout.setOnClickListener(this);
        myLLayout.setOnClickListener(this);

        //设置当前位置为home
        iv_Current = iv_home;
        t_Current = t_home;
        //给当前位置上色
        iv_Current.setSelected(true);
        t_Current.setTextColor(ContextCompat.getColor(this, R.color.blue));

        myViewPager2 = findViewById(R.id.viewPager);
    }

    @Override
    protected void initData() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        fragments.add(new home_fragment());
        fragments.add(new favorites_Fragment());
        fragments.add(new my_Fragment());
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        myViewPager2.setAdapter(pagerAdapter);
        myViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

        });
    }


    private void changeTab(int id) {
        //先把之前的颜色修改掉
        iv_Current.setSelected(false);
        t_Current.setTextColor(ContextCompat.getColor(this, R.color.text_gray));

        switch (id) {
            case R.id.id_bottom_home:
                myViewPager2.setCurrentItem(0);
            case 0:
                iv_Current = iv_home;
                t_Current = t_home;
                break;
            case R.id.id_bottom_favorites:
                myViewPager2.setCurrentItem(1);
            case 1:
                iv_Current = iv_favorites;
                t_Current = t_favorites;
                break;
            case R.id.id_bottom_my:
                myViewPager2.setCurrentItem(2);
            case 2:
                iv_Current = iv_my;
                t_Current = t_my;
                break;
        }
        //设置当前被选择的那一项的颜色
        iv_Current.setSelected(true);
        t_Current.setTextColor(ContextCompat.getColor(this, R.color.blue));
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }


    @Override
    public void onMovedToScrapHeap(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        String TAG = "e";
        boolean isNightTheme = sharedPreferences.getBoolean("nightMode", false);
        if(isNightTheme){
            this.setTheme(R.style.NightTheme);
            Log.e(TAG, "onResume: 现在是黑夜模式" );
        }else {
            setTheme(R.style.DayTheme);
            Log.e(TAG, "onResume: 现在是白天模式" );
        }
    }
    protected void needRefresh() {
        Log.d("TAG", "needRefresh: Main");
        setNightMode();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

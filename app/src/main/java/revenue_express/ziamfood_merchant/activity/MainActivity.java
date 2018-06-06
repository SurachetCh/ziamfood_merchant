package revenue_express.ziamfood_merchant.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onesignal.OneSignal;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import revenue_express.ziamfood_merchant.R;
import revenue_express.ziamfood_merchant.adapter.ViewPagerAdapter;
import revenue_express.ziamfood_merchant.fragment.MoreFragment;
import revenue_express.ziamfood_merchant.fragment.OrderListFragment;
import revenue_express.ziamfood_merchant.fragment.SummaryFragment;
import revenue_express.ziamfood_merchant.model.User;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    //This is our viewPager
    private ViewPager viewPager;
    //Fragments
    OrderListFragment orderListFragment;
    SummaryFragment summaryFragment;
    MoreFragment moreFragment;
    TextView txt_toolbar,tv_logout,tv_shop_name,tv_shop_address1,tv_shop_address2;
    ImageView img_shop_logo;
    LinearLayout shop_bar_layout;

    MenuItem prevMenuItem;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);

        realm.beginTransaction();
        RealmResults<User> result = realm.where(User.class).findAll();
        realm.commitTransaction();

        txt_toolbar = (TextView) findViewById(R.id.txt_toolbar);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_shop_address1 = (TextView) findViewById(R.id.tv_shop_address1);
        tv_shop_address2 = (TextView) findViewById(R.id.tv_shop_address2);
        img_shop_logo = (ImageView) findViewById(R.id.img_shop_logo);
        shop_bar_layout = (LinearLayout)findViewById(R.id.shop_bar_layout);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        txt_toolbar.setText(getResources().getString(R.string.order_list));

        tv_shop_name.setText(result.get(0).getShop_name());
        tv_shop_address1.setText(result.get(0).getShop_address1());
        tv_shop_address2.setText(result.get(0).getShop_address2());

        Glide.with(this)
                .load(result.get(0).getShop_logo())
                .into(img_shop_logo);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_order_list:
                                viewPager.setCurrentItem(0);
                                return true;
                            case R.id.navigation_summary:
                                viewPager.setCurrentItem(1);
                                return true;
                            case R.id.navigation_more:
                                viewPager.setCurrentItem(2);
                                return true;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                if (position == 0){
                    txt_toolbar.setText(getResources().getString(R.string.order_list));
                    shop_bar_layout.setVisibility(View.VISIBLE);
                }else if (position == 1){
                    txt_toolbar.setText(getResources().getString(R.string.summary));
                    shop_bar_layout.setVisibility(View.VISIBLE);
                }else {
                    txt_toolbar.setText(getResources().getString(R.string.more));
                    shop_bar_layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /*  //Disable ViewPager Swipe

       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        */

        setupViewPager(viewPager);
    }

    @Override
    public void onResume() {
        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        orderListFragment=new OrderListFragment();
        summaryFragment=new SummaryFragment();
        moreFragment=new MoreFragment();
        adapter.addFragment(orderListFragment);
        adapter.addFragment(summaryFragment);
        adapter.addFragment(moreFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}

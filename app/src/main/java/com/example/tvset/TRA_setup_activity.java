package com.example.tvset;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TRA_setup_activity extends AppCompatActivity {


    private ViewPager mViewPager;
    ImageButton mNextBtn;
    Button mSkipBtn, mFinishBtn;

    ImageView zero, one, two, three, four;
    ImageView[] indicators;

    int lastLeftValue = 0;

    CoordinatorLayout mCoordinator;

    SectionsPagerAdapter mSectionsPagerAdapter;

    static final String TAG = "PagerActivity";

    int page = 0;   //  to track page

    final static int pageLength = 5;
    static int[] colorList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_setup_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        }
        setContentView(R.layout.activity_tra_setup_activity);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            mNextBtn.setImageDrawable(
                    tintMyDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_24dp), Color.WHITE)
            );

        mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);

        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        three = (ImageView) findViewById(R.id.intro_indicator_3);
        four = (ImageView) findViewById(R.id.intro_indicator_4);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);

        indicators = new ImageView[]{zero, one, two, three, four};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);


        //color
        colorList = colors(this, R.color.cyan, R.color.orange, R.color.green, R.color.blue, R.color.brown);

        final ArgbEvaluator evaluator = new ArgbEvaluator();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                /*
                color update
                 */
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == colorList.length-1 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);

            }

            @Override
            public void onPageSelected(int position) {

                page = position;

                updateIndicators(page);

                mViewPager.setBackgroundColor(colorList[position]);


                mNextBtn.setVisibility(position == colorList.length-1 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == colorList.length-1 ? View.VISIBLE : View.GONE);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                mViewPager.setCurrentItem(page, true);
            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(TRA_setup_activity.this, MainActivity.class);
                startActivity(toMain);
                finish();

            }
        });


    }

    //util tool
    private int[] colors(Context context, int ...cs){
        int[] colorArray = new int[cs.length];

        for (int i = 0;i< cs.length; i++){
            colorArray[i] = (ContextCompat.getColor(context, cs[i]));
        }
        return colorArray;
    }


    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }


    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        int[] bgs = new int[]{R.drawable.ic_android_black_24dp, R.drawable.ic_android_black_24dp, R.drawable.ic_local_shipping_black_24dp};

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)-1 ){
                case 0:
                    textView.setText("選擇起站縣市");
                    //處理縣市 (地區改成市)
                    //http://twtraffic.tra.gov.tw/twrail/Services/BaseDataServ.ashx [POST]  datatype=city&language=tw.

                    System.out.println("------------------------------------------------------------");
                    View.OnClickListener onclick =  new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("Clicked!");
                        }
                    };

                    try {
                        new AsyncHTTPPost(
                                (GridLayout)rootView.findViewById(R.id.content_grid),
                                onclick,
                                MainActivity.mainContext
                        ).execute(
                                "http://twtraffic.tra.gov.tw/twrail/Services/BaseDataServ.ashx",
                                "datatype=city"
                        );

                    }catch (Exception e){
                        System.out.println(e);
                    }
                    //TRA 搜尋站牌 ID
                    //https://ptx.transportdata.tw/MOTC/v2/Rail/TRA/Station?$format=JSON&$filter=contains(StationName/Zh_tw, '正義')

                    //https://ptx.transportdata.tw
                    break;
                case 1:
                    textView.setText("選擇起站車站");
                    break;
                case 2:
                    textView.setText("選擇迄站縣市");
                    break;
                case 3:
                    textView.setText("選擇迄站車站");
                    break;
                case 4:
                    textView.setText("最終確認");
                    break;
            }

            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return TRA_setup_activity.pageLength;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < TRA_setup_activity.colorList.length -1 ){
                return "SECTION " + position;
            }else{
                return null;
            }
        }

    }
}

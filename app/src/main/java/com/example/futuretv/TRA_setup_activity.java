package com.example.futuretv;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.element.CardView_TV;
import com.gris.tw.HttpsPostProxyClient;
import com.gris.tw.ptx.train.NationalStationInfo;
import com.http_tool.AsyncHTTPPost;
import com.util.unitc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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

    public static Activity activity;

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

        //share activity state
        activity = this;

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
                changeToNextPager();
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

    private void changeToNextPager(){
        page += 1;
        mViewPager.setCurrentItem(page, true);
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

        private View rootView; //app root view layout shared

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_pager, container, false);
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
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                HttpsPostProxyClient.ProxyURL = "http://192.168.1.2/ptx-service";
                                NationalStationInfo nsi = new NationalStationInfo();
                                JSONArray cities = nsi.getCities("TC");
                                for (int i = 0; i < cities.length(); i++) {
                                    final JSONObject c = cities.getJSONObject(i);
                                    TRA_setup_activity.activity.runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            // Stuff that updates the UI
                                            try {
                                                addCardToGridLayout(c.getString("City_Name"), c.getString("City_Code"));
                                            } catch (JSONException e) {
                                                System.out.println("JSON Parse Failed");
                                            }
                                        }
                                    });

                                }

                                addCardToGridLayout("銀川市", "cool");
                            }catch (Exception e){
                                System.out.println("deadedadeadadadadadada");
                                System.out.println(e);
                            }
                        }
                    }).start();

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

        public void addCardToGridLayout(String cityName, String app_content){
            unitc conv = new unitc(rootView.getContext());

            GridLayout gv = (GridLayout) rootView.findViewById(R.id.content_grid);

            CardView_TV card = new CardView_TV(rootView.getContext());
            card.setClickable(true);
            card.setCardBackgroundColor(getResources().getColor(R.color.cyan_50));


            //android:foreground
            int[] attrs = new int[]{R.attr.selectableItemBackground};
            TypedArray typedArray = getActivity().obtainStyledAttributes(attrs);
            card.setForeground(typedArray.getDrawable(0));
            typedArray.recycle();


            card.setRadius(conv.dpToPx(8));
            card.setElevation(conv.dpToPx(8));
            ViewGroup.LayoutParams l = new BaseCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((BaseCardView.LayoutParams) l).setMargins(conv.dpToPx(16),0,conv.dpToPx(16),conv.dpToPx(16));
            card.setLayoutParams(l);

            card.app_content = app_content;


            TextView tv = new TextView(rootView.getContext());
            tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
            tv.setText(cityName);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) conv.dpToPx(150),ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setPadding((int) conv.dpToPx(20),(int) conv.dpToPx(20),(int) conv.dpToPx(20),(int) conv.dpToPx(20));
            tv.setTypeface(Typeface.create("@font/noto_sans_bold", Typeface.BOLD));
            tv.setLayoutParams(params);
            tv.setTextColor(getResources().getColor(R.color.cyan));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(25);

            card.addView(tv);


            gv.addView(card);
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

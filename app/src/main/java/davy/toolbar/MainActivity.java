package davy.toolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

//        // 半透明/透明化状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar 半透明
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // Translucent navigation bar 半透明
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        final View view = findViewById(R.id.action_container);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppCompatSeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(255);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                view.setBackgroundColor(Color.parseColor("#7F" + (Integer.toHexString(progress).length() == 1 ? "0" + Integer.toHexString(progress): Integer.toHexString(progress)) +  "7777"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        NestedScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println(scrollY);
                int alpha = scrollY * 255 / 500 + 34;
                alpha = Math.min(255, alpha);
                if (view.getBackground() != null && view.getBackground() instanceof ColorDrawable) {
                    ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
                    int color = colorDrawable.getColor();
                    int finalColor = (color & 0x00FFFFFF) | (alpha << 24);
                    view.setBackgroundColor(finalColor);
                }
            }
        });

        int alpha = scrollView.getScrollY() * 255 / 500 + 34;
        alpha = Math.min(255, alpha);
        if (view.getBackground() != null && view.getBackground() instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
            int color = colorDrawable.getColor();
            int finalColor = (color & 0x00FFFFFF) | (alpha << 24);
            view.setBackgroundColor(finalColor);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            final Toolbar toolbar = findViewById(R.id.toolbar);
            Toast.makeText(this, toolbar.getWidth() + ":" + toolbar.getHeight() + ":" + toolbar.getPaddingTop()
            , Toast.LENGTH_LONG).show();
        }
    }
}

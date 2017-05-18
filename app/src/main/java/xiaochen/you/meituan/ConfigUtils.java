package xiaochen.you.meituan;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout.LayoutParams;

/**
 * Created by you on 16/8/31.
 * 配置辅助类  Resources.getSystem()
 */
public class ConfigUtils {

    private ConfigUtils() {
    }

    /**
     * 4.4以上版本
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 5.0版本
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 获取状态栏高度
     */
    public static final int getStatusHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    /**
     * 设置activity statusbar颜色, 此方法必须在activitysetContentView之后才可以见效果
     *
     * @param activity
     * @param statusColor
     */
    public static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        ViewGroup root = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        if (hasKitKat()) {//4.4以上才支持
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            Object tag = decorView.getTag();
            if (tag instanceof Boolean && (Boolean) tag) {
                View mStatusBarView = decorView.getChildAt(0);
                if (mStatusBarView != null) {
                    mStatusBarView.setBackgroundColor(statusColor);
                }
            } else {
                int statusBarHeight = getStatusHeight(activity);
                View contentChild = root.getChildAt(0);
                if (contentChild != null) {
                    ViewCompat.setFitsSystemWindows(contentChild, false);
                    LayoutParams lp = (LayoutParams) contentChild.getLayoutParams();
                    lp.topMargin += statusBarHeight;
                    contentChild.setLayoutParams(lp);
                }
                View mStatusBarView = new View(activity);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, statusBarHeight);
                layoutParams.gravity = Gravity.TOP;
                mStatusBarView.setLayoutParams(layoutParams);
                mStatusBarView.setBackgroundColor(statusColor);
                decorView.addView(mStatusBarView, 0);
                decorView.setTag(true);
            }
        }
    }

}

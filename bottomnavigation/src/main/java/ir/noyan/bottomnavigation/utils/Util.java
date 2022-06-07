package ir.noyan.bottomnavigation.utils;

import android.content.res.Resources;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author S.Shahini
 * @since 11/14/16
 */

public class Util {
    /**
     * this function get dp based value and convert it to px
     *
     * @param dp value based on dp metric
     * @return return value based on px metric
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(double dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    public static LinearLayout.LayoutParams createLinearParam(int w, int h, int weight, int margin, int gravity) {


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, h);
        lp.weight = weight;
        lp.setMargins(margin, margin, margin, margin);
        lp.gravity = gravity;

        return lp;
    }
    public static LinearLayout.LayoutParams createLinearParam(int w, int h, int weight, int marginLeft,int marginTop,int marginRight,int marginBottom, int gravity) {


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, h);
        lp.weight = weight;
        //int left, int top, int right, int bottom
        lp.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        lp.gravity = gravity;

        return lp;
    }
    public static RelativeLayout.LayoutParams createRelativeParam(int w, int h, int margin, int verb,int id) {


        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, h);
        lp.setMargins(margin, margin, margin, margin);
        lp.addRule(verb,id);


        return lp;
    }
    public static RelativeLayout.LayoutParams createRelativeParam(int w, int h, int margin, int verb) {


        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, h);
        lp.setMargins(margin, margin, margin, margin);
        lp.addRule(verb);


        return lp;
    }

}

package ir.noyan.bottomnavigation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import ir.noyan.bottomnavigation.events.OnTabItemClickListener;
import ir.noyan.bottomnavigation.events.Status;
import ir.noyan.bottomnavigation.utils.AnimationHelper;
import ir.noyan.bottomnavigation.utils.LayoutParamsHelper;
import ir.noyan.bottomnavigation.utils.Util;

/**
 * @author S.Shahini
 * @since 11/13/16
 */

public class TabItem extends RelativeLayout implements View.OnClickListener {
    private OnTabItemClickListener onTabItemClickListener;
    private int position;

    //Attributes
    private String text;
    private Drawable selectedTabIcon;
    private int selectedTabTextColor;

    private Drawable unselectedTabIcon;
    private int unselectedTabTextColor;

    //Views
    private TextView textView;
    private ImageView iconImageView;
    private CardView cardView;
    private ImageView enableImageView;
    private ImageView transparentImage;
    private ImageView lineImage;
    private LinearLayout layout = new LinearLayout(getContext());


    private Status status;
    private int type = BottomNavigation.TYPE_FIXED;
    private AnimationHelper animationHelper;
    private BottomNavigation bottomNavigation;


    public TabItem(Context context) {
        super(context);
        parseCustomAttributes(null);
    }

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseCustomAttributes(attrs);
    }

    public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseCustomAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseCustomAttributes(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        checkParent();
    }

    private void checkParent() {
        post(new Runnable() {
            @Override
            public void run() {
                if (getParent() instanceof BottomNavigation) {
                    bottomNavigation = (BottomNavigation) getParent();
                    type = bottomNavigation.getType();
                    setupView();
                } else {
                    throw new RuntimeException("TabItem parent must be BottomNavigation!");
                }
            }
        });
    }

    private void setupView() {


        this.setBackgroundColor(Color.TRANSPARENT);
        this.status = Status.NONE;
        setOnClickListener(this);
        if (bottomNavigation.getMode() == BottomNavigation.MODE_PHONE) {
            setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        } else {
            setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, Util.dpToPx(56)));
        }


        animationHelper = new AnimationHelper(type);

        LayoutParams pp = Util.createRelativeParam(ViewGroup.LayoutParams.MATCH_PARENT, Util.dpToPx(58), 0, ALIGN_PARENT_BOTTOM);


        layout.setLayoutParams(pp);
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);


        textView = new TextView(getContext());
        textView.setTextColor(selectedTabTextColor);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(Util.createLinearParam(LayoutParams.MATCH_PARENT, Util.dpToPx(17), 0, 0, Util.dpToPx(0), 0, Util.dpToPx(5), Gravity.CENTER));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);


        iconImageView = new ImageView(getContext());
        textView.setGravity(Gravity.CENTER);

        iconImageView.setImageDrawable(unselectedTabIcon);
        iconImageView.setLayoutParams(Util.createLinearParam(Util.dpToPx(24), Util.dpToPx(24), 0, Util.dpToPx(7), Gravity.CENTER));


        ViewGroup.LayoutParams lpIv = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dpToPx(0.5));

//        lpIv.addRule(BELOW, transparentImage.getId());
//        lpIv.addRule(CENTER_HORIZONTAL);
//        lpIv.bottomMargin = (-23);
        lineImage = new ImageView(getContext());
        lineImage.setBackgroundColor(Color.BLACK);
        lineImage.setVisibility(VISIBLE);
        lineImage.setLayoutParams(lpIv);


        layout.addView(lineImage);
        layout.addView(iconImageView);
        layout.addView(textView);

        enableImageView = new ImageView(getContext());
        enableImageView.setImageDrawable(selectedTabIcon);

        CardView.LayoutParams cl = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dpToPx(23));
        cl.gravity = Gravity.CENTER;
        enableImageView.setLayoutParams(cl);


        transparentImage = new ImageView(getContext());
        transparentImage.setBackgroundColor(Color.TRANSPARENT);
        transparentImage.setVisibility(INVISIBLE);

        transparentImage.setLayoutParams(Util.createRelativeParam(Util.dpToPx(23), Util.dpToPx(23), 0, ABOVE, layout.getId()));



        LayoutParams lp = new LayoutParams(Util.dpToPx(46), Util.dpToPx(46));

        lp.addRule(BELOW, transparentImage.getId());
        lp.addRule(CENTER_HORIZONTAL);
        lp.bottomMargin = (-23);


        cardView = new CardView(getContext());
        cardView.addView(enableImageView);
        cardView.setVisibility(VISIBLE);
        cardView.setLayoutParams(lp);
        cardView.setRadius((this.getWidth() >> 3));
        cardView.setBackgroundResource(R.drawable.card_background);








        Log.e("width", this.getWidth() + "");


        addView(transparentImage);
        addView(layout);
        addView(cardView);
        


        try {

            ((BottomNavigation) this.getParent()).onSelectedItemChanged();
        } catch (Exception e) {

        }

    }

    private void parseCustomAttributes(AttributeSet attributeSet) {

        if (attributeSet != null) {
            //get xml attributes
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.MyTabItem, 0, 0);
            try {
                text = typedArray.getString(R.styleable.MyTabItem_tab_text);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    selectedTabTextColor = typedArray.getColor(R.styleable.MyTabItem_tab_text_color, getResources().getColor(R.color.default_text_color, null));
                    unselectedTabTextColor = typedArray.getColor(R.styleable.MyTabItem_unselected_tab_text_color, getResources().getColor(R.color.default_unselected_text_color));
                } else {
                    selectedTabTextColor = typedArray.getColor(R.styleable.MyTabItem_tab_text_color, getResources().getColor(R.color.default_text_color));
                    unselectedTabTextColor = typedArray.getColor(R.styleable.MyTabItem_unselected_tab_text_color, getResources().getColor(R.color.default_unselected_text_color));
                }
                selectedTabIcon = typedArray.getDrawable(R.styleable.MyTabItem_tab_icon);
                unselectedTabIcon = typedArray.getDrawable(R.styleable.MyTabItem_unselected_tab_icon);
            } finally {
                typedArray.recycle();
            }
        }
    }

    public void setSelected(Status status) {
        Log.e("set selected", "" + status);


        if (this.status == status){
            return;
        }
//
        switch (status) {
            case SELECTED:
                changeVisibility(true);
                break;
            case UNSELECTED:
                changeVisibility(false);
                break;

            case NONE:

                break;
        }

        this.status = status;
    }

    public void setSelected() {
        Log.e("set selected", "" + status);
////        if (isActive)
//        if (this.isActive != isActive) {
//            changeVisibility(isActive);
//            this.isActive = isActive;
//        }
//
        switch (status) {
            case SELECTED:
                changeVisibility(true);
                break;
            case UNSELECTED:
                changeVisibility(false);
                break;

            case NONE:

                break;
        }


    }

    public void setOnTabItemClickListener(OnTabItemClickListener onTabItemClickListener) {
        this.onTabItemClickListener = onTabItemClickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public void onClick(View view) {
        if (onTabItemClickListener != null) {
            onTabItemClickListener.onTabItemClick(position);
        }
    }

//    private void notifyChange() {
//        changeVisibility(!isActive);
//
//
//    }

    public void setTypeface(Typeface typeface) {
        if (textView != null) {
            textView.setTypeface(typeface);
        }
    }


    public void changeVisibility(boolean showItem) {

        if (showItem) {



            if (cardView!=null)

                cardView.setVisibility(VISIBLE);
//            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.fade);
//            set.setTarget(cardView); // set the view you want to animate
//            set.start();

            if (iconImageView!=null)
            iconImageView.setVisibility(INVISIBLE);
//            AnimatorSet set2 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.fade_visible);
//            set2.setTarget(iconImageView); // set the view you want to animate
//            set2.start();



            if (textView!=null)
            textView.setTextColor(unselectedTabTextColor);

        } else {
            if (iconImageView!=null)
                iconImageView.setVisibility(VISIBLE);
//            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.fade);
//            set.setTarget(iconImageView); // set the view you want to animate
//            set.start();


            if (cardView!=null)
                cardView.setVisibility(INVISIBLE);
//            AnimatorSet set2 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.fade_visible);
//            set2.setTarget(cardView); // set the view you want to animate
//            set2.start();

            if (textView!=null)
                textView.setTextColor(selectedTabTextColor);
        }
    }

}

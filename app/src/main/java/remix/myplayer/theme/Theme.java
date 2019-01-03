package remix.myplayer.theme;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import remix.myplayer.App;
import remix.myplayer.R;
import remix.myplayer.util.ColorUtil;
import remix.myplayer.util.DensityUtil;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;


/**
 * @ClassName
 * @Description
 * @Author Xiaoborui
 * @Date 2016/8/9 14:55
 */
public class Theme {
    public static Drawable getVectorDrawable(@NonNull Context context, @DrawableRes int id) {
        return getVectorDrawable(context.getResources(), id, context.getTheme());
    }

    public static Drawable getVectorDrawable(@NonNull Resources res, @DrawableRes int resId, @Nullable Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= 21) {
            return res.getDrawable(resId, theme);
        }
        return VectorDrawableCompat.create(res, resId, theme);
    }

    public static Drawable tintVectorDrawable(@NonNull Context context, @DrawableRes int id, @ColorInt int color) {
        return tintDrawable(getVectorDrawable(context.getResources(), id, context.getTheme()), color);
    }

    /**
     * thumb加深色边框并着色
     */
    public static GradientDrawable getTintThumb(Context context) {
        GradientDrawable thumbDrawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.bg_circleseekbar_thumb);
        thumbDrawable.setStroke(DensityUtil.dip2px(context, 1), ColorUtil.shiftColor(ThemeStore.getAccentColor(), 0.8f));
        thumbDrawable.setColor(ThemeStore.getAccentColor());
        return thumbDrawable;
    }

    public static Drawable tintDrawable(@DrawableRes int DrawRes, @ColorInt int color) {
        return tintDrawable(App.getContext().getResources().getDrawable(DrawRes), color, 1.0f);
    }

    /**
     * 为drawable着色
     *
     * @param oriDrawable
     * @param color
     * @return
     */
    public static Drawable tintDrawable(Drawable oriDrawable, @ColorInt int color, @FloatRange(from = 0.0D, to = 1.0D) float alpha) {

        final Drawable wrappedDrawable = DrawableCompat.wrap(oriDrawable.mutate());
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(ColorUtil.adjustAlpha(color, alpha)));
        return wrappedDrawable;
    }

    /**
     * 为drawable着色
     *
     * @param oriDrawable
     * @param color
     * @return
     */
    public static Drawable tintDrawable(Drawable oriDrawable, @ColorInt int color) {
        return tintDrawable(oriDrawable, color, 1.0f);
    }

    /**
     * 为drawale着色
     *
     * @param view
     * @param color
     */
    public static void tintDrawable(View view, Drawable drawable, @ColorInt int color) {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(tintDrawable(drawable, color));
        } else {
            view.setBackground(tintDrawable(drawable, color));
        }
    }

    /**
     * 着色v背景
     *
     * @param view
     * @param res
     * @param color
     */
    public static void tintDrawable(View view, @DrawableRes int res, @ColorInt int color) {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(tintDrawable(App.getContext().getResources().getDrawable(res), color));
        } else {
            view.setBackground(tintDrawable(App.getContext().getResources().getDrawable(res), color));
        }
    }

    /**
     * 根据当前主题获得popupmenu风格
     *
     * @return
     */
    @StyleRes
    public static int getPopupMenuStyle() {
        return ThemeStore.isLightTheme() ? R.style.PopupMenuLightStyle : R.style.PopupMenuDarkStyle;
    }


    /**
     * 修改edittext光标与下划线颜色
     *
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id);
        } else {
            return context.getDrawable(id);
        }
    }

    /**
     * @param context
     * @param resId
     * @return
     */
    public static StateListDrawable getPressAndSelectedStateListDrawalbe(Context context, @DrawableRes int resId) {
        return getPressAndSelectedStateListDrawalbe(context, resId, ThemeStore.getMaterialPrimaryColor());
    }

    /**
     * @param context
     * @param resId
     * @param color
     * @return
     */
    public static StateListDrawable getPressAndSelectedStateListDrawalbe(Context context, @DrawableRes int resId, @ColorInt int color) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, tintDrawable(context.getResources().getDrawable(resId), color));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, tintDrawable(context.getResources().getDrawable(resId), color));
        stateListDrawable.addState(new int[]{}, getDrawable(context, resId));

        return stateListDrawable;
    }

    /**
     * 按下与选中触摸效果
     *
     * @param context
     * @param selectDrawable
     * @param defaultDrawable
     * @return
     */
    public static StateListDrawable getPressAndSelectedStateListRippleDrawable(Context context,
                                                                               Drawable selectDrawable,
                                                                               Drawable defaultDrawable,
                                                                               @ColorInt int rippleColor) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), defaultDrawable, null);
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectDrawable);
            stateListDrawable.addState(new int[]{}, rippleDrawable);
            return stateListDrawable;
        } else {
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectDrawable);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, selectDrawable);
            stateListDrawable.addState(new int[]{}, defaultDrawable);
            return stateListDrawable;
        }
    }

//    /**
//     * 按下与选中触摸效果
//     *
//     * @param context
//     * @param selectDrawable
//     * @param defaultDrawable
//     * @return
//     */
//    public static StateListDrawable getPressAndSelectedStateListRippleDrawable(Context context,
//                                                                               Drawable selectDrawable,
//                                                                               Drawable defaultDrawable) {
//        return getPressAndSelectedStateListRippleDrawable(context, selectDrawable, defaultDrawable, ThemeStore.getRippleColor());
//    }

//    /**
//     * @param context
//     * @return
//     */
//    public static StateListDrawable getPressAndSelectedStateListRippleDrawable(int model, Context context) {
//        int defaultColor = ThemeStore.getBackgroundColorMain(context);
//
//        return getPressAndSelectedStateListRippleDrawable(context,
//                model == HeaderAdapter.GRID_MODE ? getCorner(1, DensityUtil.dip2px(context, 2), 0, ThemeStore.getSelectColor()) : getShape(GradientDrawable.RECTANGLE, ThemeStore.getSelectColor()),
//                model == HeaderAdapter.GRID_MODE ? getCorner(1, DensityUtil.dip2px(context, 2), 0, defaultColor) : getShape(GradientDrawable.RECTANGLE, defaultColor));
//    }

    /**
     * @param color
     * @param contentDrawable
     * @param maskDrawable
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static RippleDrawable getRippleDrawable(@ColorInt int color, Drawable contentDrawable, Drawable maskDrawable) {
        return new RippleDrawable(ColorStateList.valueOf(color), contentDrawable, maskDrawable);
    }

    /**
     * 按下触摸效果
     *
     * @param defaultDrawable
     * @param effectDrawable
     * @param rippleColor
     * @param contentDrawable
     * @param maskDrawable
     * @return
     */
    public static Drawable getPressDrawable(Drawable defaultDrawable, Drawable effectDrawable, @ColorInt int rippleColor, Drawable contentDrawable, Drawable maskDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(rippleColor),
                    contentDrawable,
                    maskDrawable);
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, effectDrawable);
            stateListDrawable.addState(new int[]{}, defaultDrawable);
            return stateListDrawable;
        }
    }

    public static MaterialDialog.Builder getBaseDialog(Context context) {
        return new MaterialDialog.Builder(context)
                .contentColorAttr(R.attr.text_color_primary)
                .titleColorAttr(R.attr.text_color_primary)
                .positiveColorAttr(R.attr.text_color_primary)
                .negativeColorAttr(R.attr.text_color_primary)
                .neutralColorAttr(R.attr.text_color_primary)
                .buttonRippleColorAttr(R.attr.ripple_color)
                .backgroundColorAttr(R.attr.background_color_dialog)
                .itemsColorAttr(R.attr.text_color_primary)
                .widgetColor(ThemeStore.getAccentColor());
//                .theme(ThemeStore.getMDDialogTheme());

    }

    public static void setLightNavigationbarAuto(Activity activity, boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final View decorView = activity.getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (enabled) {
                systemUiVisibility |= SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            } else {
                systemUiVisibility &= ~SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            decorView.setSystemUiVisibility(systemUiVisibility);
        }

    }

    public static int resolveColor(Context context, @AttrRes int attr){
        return resolveColor(context,attr,0);
    }

    public static int resolveColor(Context context, @AttrRes int attr,int fallback){
        TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{attr});
        int color;
        try {
            color = ta.getColor(0,fallback);
        } finally {
            ta.recycle();
        }
        return color;
    }

    public static boolean isWindowBackgroundDark(Context context){
        return !ColorUtil.isColorLight(resolveColor(context,android.R.attr.windowBackground));
    }

}

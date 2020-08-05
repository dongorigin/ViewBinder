package cn.dong.viewbinder;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import cn.dong.viewbinder.provider.ActivityProvider;
import cn.dong.viewbinder.provider.Provider;
import cn.dong.viewbinder.provider.ViewProvider;

/**
 * @author dong on 2018/07/28.
 */
public class ViewBinder {
    private static final String SUFFIX = "$$Binder";
    private static final Provider ACTIVITY_PROVIDER = new ActivityProvider();
    private static final Provider VIEW_PROVIDER = new ViewProvider();

    public static void bind(Activity activity) {
        bind(activity, activity, ACTIVITY_PROVIDER);
    }

    public static void bind(View view) {
        bind(view, view, VIEW_PROVIDER);
    }

    /**
     * @param target 绑定目标，注解 View 字段所在的对象
     * @param source 绑定来源，查找 View 的地方，Activity & View 在自身找，Fragment 需要在 itemView 中找
     */
    private static void bind(@NonNull Object target, @NonNull Object source, @NonNull Provider provider) {
        String targetClassName = target.getClass().getName();
        String binderClassName = targetClassName + SUFFIX;
        try {
            Class<?> binderClass = Class.forName(binderClassName);
            Binder binder = (Binder) binderClass.newInstance();
            binder.bind(target, source, provider);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}

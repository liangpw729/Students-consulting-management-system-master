package com.example.hin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.widget.Toast;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * Created by WWF on 2017/1/1.
 */

public class CommonUtils {

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][35874]\\d{9}";// "[1]"代表第1位为数字1，"[3587]"代表第二位可以为3、4、5、8、7中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 错误信息提示
     */
    public static void errorMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 提示信息
     */
    public static void commonMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void countDownTask(final int time, Subscriber<Integer> subscriber) {
        Flowable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).map(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long aLong) throws Exception {
                return time - aLong.intValue();
            }
        }).take(time).subscribe(subscriber);
    }

    public static Activity getActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }
}

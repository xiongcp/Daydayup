package com.xiongcp.commonlibrary.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;


public class ActivityCollector {

    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }


    public static boolean containsActivity(Class clz) {
        if (activities != null && activities.size() > 0) {
            for (Activity activity : activities) {
                String simpleName = activity.getClass().getSimpleName();
                if (simpleName.equals(clz.getSimpleName())) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void removeAll() {

        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void removeAllButThis(String activityName) {
        for (Activity activity : activities) {
            if (!activity.getClass().getName().equals(activityName)) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

}

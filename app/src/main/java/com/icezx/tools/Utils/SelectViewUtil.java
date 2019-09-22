package com.icezx.tools.Utils;

import android.view.View;

import com.icezx.tools.R;

/**
 * the package name com.icezx.tools.Utils
 * create by yili
 * create on 2019-09-03
 * description
 */
public class SelectViewUtil {

    public static void SelectView(View view){
        View MainActivityView=view.findViewById(R.id.default_include_mainactivity);
        View CalculatorView=view.findViewById(R.id.default_include_calculator);
        View HomeWorkView=view.findViewById(R.id.default_include_homework);
        View LogicalView=view.findViewById(R.id.default_include_logical);
        View RadixView=view.findViewById(R.id.default_include_radix);
        View ScheduleView=view.findViewById(R.id.default_include_schedule);
        View ConsoleLoginView=view.findViewById(R.id.default_include_console_login);
        View ConsoleMainView=view.findViewById(R.id.default_include_console_main);
        View TranslateView=view.findViewById(R.id.default_include_translate_main);

        MainActivityView.setVisibility(View.VISIBLE);
        CalculatorView.setVisibility(View.GONE);
        HomeWorkView.setVisibility(View.GONE);
        LogicalView.setVisibility(View.GONE);
        RadixView.setVisibility(View.GONE);
        ScheduleView.setVisibility(View.GONE);
        ConsoleLoginView.setVisibility(View.GONE);
        ConsoleMainView.setVisibility(View.GONE);
        TranslateView.setVisibility(View.GONE);

    }

    public String returnActivity(){



        return "0";
    }

}

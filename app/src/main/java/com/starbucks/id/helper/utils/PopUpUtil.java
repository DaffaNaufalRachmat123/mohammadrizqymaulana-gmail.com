package com.starbucks.id.helper.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.starbucks.id.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Angga N P on 12/20/2018.
 */
public class PopUpUtil {

    private static Toast toast;


    public static void sShortToast(Context ctx, String s) {
        try {
            toast.getView().isShown();
            toast.setText(s);
        } catch (Exception e) {
            toast = Toast.makeText(ctx, s, Toast.LENGTH_SHORT);
            toast.show();
        }

        toast.show();
    }

    public static void sLongToast(Context ctx, String s) {
        try {
            toast.getView().isShown();
            toast.setText(s);
        } catch (Exception e) {
            toast = Toast.makeText(ctx, s, Toast.LENGTH_LONG);
            toast.show();
        }

        toast.show();
    }
    public static void getCalendar(final Context context, final EditText editText, final boolean signUp) {
        final Calendar c = Calendar.getInstance(Locale.US);
        int mYear;
        if(signUp){
            mYear = c.get(Calendar.YEAR);
        } else {
            mYear = c.get(Calendar.YEAR);
        }
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.MyDatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        Date date = calendar.getTime();
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE, dd MMM yyyy");
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                        if(signUp) {
                            String dateConvert = simpleDateFormat2.format(date);
                            editText.setText(dateConvert);
                        } else {
                            String dateConvert = simpleDateFormat2.format(date);
                            editText.setText(dateConvert);
                        }


                    }
                }, mYear, mMonth, mDay);
        if (signUp) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.DAY_OF_YEAR, 25);
//            datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        } else {
            String dateString = "01/01/1945";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long startDate = date.getTime();

            datePickerDialog.getDatePicker().setMinDate(startDate);
        }
        datePickerDialog.show();
    }

    public static String convertDateRegister(String dateString){
        String convertedDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat convertDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(dateString);
            convertedDate = convertDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

}

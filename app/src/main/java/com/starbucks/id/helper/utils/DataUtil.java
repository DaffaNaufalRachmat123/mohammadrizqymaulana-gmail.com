package com.starbucks.id.helper.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.starbucks.id.helper.StarbucksID;
import com.starbucks.id.model.user.UserIdentifierModel;
import com.starbucks.id.rest.RestSecurityGen;

import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Angga N P on 5/9/2018.
 */

public class DataUtil {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private static final String TAG = "TAG";

    public static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String genSBUX(String s) {
        if (StarbucksID.Companion.getDebug()) {
            Log.i(TAG, "genSBUXXX: " + s);
            Log.i(TAG, "genSBUXXX: " + RestSecurityGen.encrypt(s));
        }
        return RestSecurityGen.encrypt(s);
    }

    public static String encrypt(String s) {
        try {
            byte[] data = s.getBytes(StandardCharsets.UTF_8);
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String maskCard(String str) {
        String _CardNo14 = "";
        String _CardNo1216 = "";

        try {
            _CardNo14 = str.substring(0, 4);
            _CardNo1216 = str.substring(12, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return _CardNo14 + " **** **** " + _CardNo1216;
    }

    public static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        if (contents == null) return null;

        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contents, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public static String currencyFormat(String number) {
        int data = 0;
        try {
            data = Integer.parseInt(number);
        } catch (Exception e) {
            return "Error";
        }
        return "Rp. " + ((NumberFormat.getNumberInstance(Locale.US).
                format(data)).replace(",", "."));
    }

    public static String getCurTS() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date());
    }

    public static String getDate(String s, int opid) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Date not valid";
        }

        if (newDate != null) {
            switch (opid) {
                case 0:{
                    format = new SimpleDateFormat("dd MMM yyyy");
                    return format.format(newDate);
                }
                case 1: {
                    format = new SimpleDateFormat("HH:mm");
                    return format.format(newDate);
                }
                case 2: {
                    format = new SimpleDateFormat("HH:MM, dd MMM yyyy");
                    return format.format(newDate);
                }
                case 3: {
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    return format.format(newDate);
                }
            }
        }
        return "Date not valid";
    }

    public static Long getExpTime(String start, String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startDate, endDate;

        try {
            startDate = format.parse(start);
            endDate = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }

        if (startDate != null && endDate != null){
            long startTime = startDate.getTime();
            long endTime = endDate.getTime();
            return (endTime - startTime);
        }

        return 0L;
    }

    public static List<UserIdentifierModel> mapCard(List<UserIdentifierModel> im, String card) {
        if (im.size() > 1) {
            for (int i = 1; i < im.size(); i++) {
                UserIdentifierModel id = im.get(i);
                if (id.getExternalId().equals(card)) {
                    im.set(i, im.get(0));
                    im.set(0, id);

                    return im;
                }
            }
        }
        return im;
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = 300;
            height = 350;
        } else {
            width = 300;
            height = 350;
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}

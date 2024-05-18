package com.gifgif.androidio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaDrm;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.security.MessageDigest;
import java.util.UUID;

public class DeviceUtils {

    private static long WIDEWINE_UUID_MOST_SIG_BITS = -0x121074568629b532L;
    private static long WIDEWINE_UUID_LEAST_SIG_BITS = -0x5c37d8232ae2de13L;

    private static String URI_GSF_CONTENT_PROVIDER = "content://com.google.android.gsf.gservices";
    private static String ID_KEY = "android_id";


    public static String mediaDrmId() {
        String drmId = "";
        try {
            UUID widevineUUID = new UUID(WIDEWINE_UUID_MOST_SIG_BITS, WIDEWINE_UUID_LEAST_SIG_BITS);
            MediaDrm wvDrm = new MediaDrm(widevineUUID);
            byte[] mivevineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);
            releaseMediaDRM(wvDrm);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(mivevineId);
            drmId = toHexString(md.digest());
        } catch (Exception e) {

        }
        return drmId;
    }

    public static String getGsfId(Context context) {
        try {
            Uri uri = Uri.parse(URI_GSF_CONTENT_PROVIDER);
            Cursor cursor = context.getContentResolver().query(uri, null, null, new String[]{ID_KEY}, null);
            if (!cursor.moveToFirst() || cursor.getColumnCount() < 2) {
                cursor.close();
                return "";
            }
            String result = Long.toHexString(Long.parseLong(cursor.getString(1)));
            cursor.close();
            return result;
        } catch (Exception e) {
            return "";
        }
    }


    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return "";
        }
    }

    private static void releaseMediaDRM(MediaDrm drmObject) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            drmObject.close();
        } else {
            drmObject.release();
        }
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }
}

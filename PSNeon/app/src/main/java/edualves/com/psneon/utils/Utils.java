package edualves.com.psneon.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import edualves.com.psneon.R;

/**
 * Created by edualves on 01/07/17.
 */

public class Utils {

    public static String readJson(Context context) {

        String json;

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.contacts);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }
}

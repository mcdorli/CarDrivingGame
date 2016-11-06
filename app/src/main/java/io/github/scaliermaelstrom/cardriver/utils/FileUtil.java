package io.github.scaliermaelstrom.cardriver.utils;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.github.scaliermaelstrom.cardriver.MainActivity;

public class FileUtil {

    public static String readAsString(String name) {
        AssetManager assetManager = MainActivity.assetManager;

        StringBuilder builder = new StringBuilder();

        try {
            InputStream stream = assetManager.open(name);
            InputStreamReader isr = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return builder.toString();
    }

}

package com.karic.sdk.utils;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class Assets {
    public static String loadText(AssetManager assetManager, String path) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(path)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }
}

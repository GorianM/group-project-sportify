package edu.dipae.sportify.utils;

import android.content.Context;

import edu.dipae.sportify.dialogs.DialogFactory;

public class ExceptionHandler {
    public static void handleException(Context context, Exception e) {
        DialogFactory.showInfoDialog(context, "Error", e.getMessage());
        System.err.println(e.getMessage());
        e.printStackTrace();
    }
}

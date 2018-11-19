package com.yovanydev.appproductos.utilities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yovanydev.appproductos.R;

public class ToolbarUtility {

    /**
     * Determina el comportamiento del toolbar con algunas caracteristicas
     * @param title : titulo del toolbar
     * @param upButton : Boton para retornar al padre
     * @param activity : actividad en la que se presenta el View
     */
    public static void showToolbar(String title, String subtitle, Boolean upButton, Activity activity) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        ((AppCompatActivity) activity).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) activity).getSupportActionBar().setSubtitle(" " + subtitle);
        ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        ((AppCompatActivity) activity).getSupportActionBar().setIcon(R.drawable.img_logo_appbar);
    }

}

package com.demo.secondarydisplay;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

public class SecondaryPresentation extends Presentation {
    public SecondaryPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
    }
}

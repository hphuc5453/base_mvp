package com.example.test.core.app.util;


import androidx.annotation.NonNull;

public class DoubleTouchPreventProvider {
    private static DoubleTouchPrevent doubleTouchPrevent = new DoubleTouchPrevent();

    @NonNull
    public static DoubleTouchPrevent createDoubleTouchPrevent() {
        return doubleTouchPrevent;
    }
}

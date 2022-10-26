package com.example.yoyoiq.Fragment;

import static java.lang.Thread.currentThread;

public class Admin {
    public static int getLineNumber() {
        return currentThread().getStackTrace()[3].getLineNumber();
    }
}

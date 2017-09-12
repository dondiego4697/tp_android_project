package com.koala.infinitum.android_project.mainFragments.list;

import java.util.ArrayList;

public class Factory {
    private ArrayList<String> Main1 = new ArrayList<>(), Main2 = new ArrayList<>();

    ArrayList<String> getMain1() {
        Main1.add("asasd111");
        Main1.add("asasd222");
        Main1.add("asasd333");
        Main1.add("asasd444");
        return Main1;
    }

    ArrayList<String> getMain2() {
        return Main2;
    }
}

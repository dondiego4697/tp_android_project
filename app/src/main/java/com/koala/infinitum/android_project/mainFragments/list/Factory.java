package com.koala.infinitum.android_project.mainFragments.list;

import java.util.ArrayList;

public class Factory {
    private ArrayList<String> Main1 = new ArrayList<>(), Main2 = new ArrayList<>();

    ArrayList<String> getMain1() {
        Main1.add("asasd11");
        Main1.add("asasd22");
        Main1.add("asasd33");
        Main1.add("asasd44");
        return Main1;
    }

    ArrayList<String> getMain2() {
        return Main2;
    }
}

package com.koala.infinitum.android_project.List;

import java.util.ArrayList;

public interface Adapter<T> {
    void updateData(ArrayList<T> items);

    void updateItem(T item, Integer index);

    void clear();

    ArrayList<T> getData();
}

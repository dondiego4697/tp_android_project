package com.koala.infinitum.android_project.httpApi;

import android.support.annotation.Nullable;

/**
 * Created by andrey on 12.11.17.
 */

public class ListenerHandler<T> {
    private T listener;

    public ListenerHandler(final T listener) {
        this.listener = listener;
    }

    @Nullable
    public T getListener() {
        return listener;
    }

    public void unregister() {
        listener = null;
    }
}

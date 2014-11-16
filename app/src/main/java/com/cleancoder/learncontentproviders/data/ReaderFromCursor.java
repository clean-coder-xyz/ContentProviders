package com.cleancoder.learncontentproviders.data;

import android.database.Cursor;

/**
 * Created by Leonid Semyonov (clean-coder-xyz) on 26.10.2014.
 */
public interface ReaderFromCursor<T> {
    T read(Cursor cursor);
}

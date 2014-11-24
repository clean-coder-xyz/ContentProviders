package com.cleancoder.learncontentproviders;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import com.cleancoder.learncontentproviders.content.StudentsContentProvider;
import com.cleancoder.learncontentproviders.data.StudentsContract;
import com.cleancoder.learncontentproviders.data.StudentsContract.StudentsEntry;

/**
 * Created by Leonid Semyonov (clean-coder-xyz) on 23.11.2014.
 */
public class StudentsDisplayFragment extends android.support.v4.app.ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_STUDENTS = 1;

    private SimpleCursorAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        setEmptyText(getString(R.string.students_display_no_students));


        String[] columnsToDisplay = { StudentsContract.StudentsEntry.COLUMN_NAME };
        int[] viewIds = { R.id.name_text_view};
        adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item_students,
                null,
                columnsToDisplay,
                viewIds,
                0
        );
        setListAdapter(adapter);

        getLoaderManager().initLoader(LOADER_STUDENTS, null, this);
    }
    @Override

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.CursorLoader(
                getActivity(),
                StudentsContentProvider.CONTENT_URI,
                new String[] { StudentsEntry._ID, StudentsEntry.COLUMN_NAME },
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }

}

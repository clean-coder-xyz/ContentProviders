package contentproviders.learning.cleancoder.com.contentproviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.provider.UserDictionary.Words;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;

import com.cleancoder.base.android.data.Query;

/**
 * Created by Leonid on 04.11.2014.
 */
public class UserDictionaryDisplayViaCursorLoaderFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String KEY_QUERY = "query";
    private static final String KEY_COLUMNS_TO_DISPLAY = "columns_to_display";
    private static final String KEY_VIEW_IDS = "view_ids";

    private static final int LOADER_USER_DICTIONARY = 1;

    private View contentView;
    private SimpleCursorAdapter adapter;

    public static UserDictionaryDisplayViaCursorLoaderFragment newInstance(
                            Query query, String[] columnsToDisplay, int[] viewIds) {
        UserDictionaryDisplayViaCursorLoaderFragment fragment = new UserDictionaryDisplayViaCursorLoaderFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_QUERY, query);
        args.putStringArray(KEY_COLUMNS_TO_DISPLAY, columnsToDisplay);
        args.putIntArray(KEY_VIEW_IDS, viewIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        setEmptyText(getString(R.string.user_dictionary_is_empty));

        String[] columnsToDisplay = getArguments().getStringArray(KEY_COLUMNS_TO_DISPLAY);
        int[] viewIds = getArguments().getIntArray(KEY_VIEW_IDS);
        adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item_user_dictionary,
                null,
                columnsToDisplay,
                viewIds,
                0
        );
        setListAdapter(adapter);

        getLoaderManager().initLoader(LOADER_USER_DICTIONARY, null, this);
    }

    private void updateFrequencyById(int id) {
        Cursor cursor = getActivity().getContentResolver().query(
                Words.CONTENT_URI,
                new String[] { Words.FREQUENCY },
                Words._ID + " = ?",
                new String[] { String.valueOf(id) },
                null
        );
        if (cursor.getCount() < 1) {
            throw new IllegalArgumentException("Word with id=" + id + " not found");
        }
        cursor.moveToFirst();
        int indexFrequency = cursor.getColumnIndexOrThrow(Words.FREQUENCY);
        int frequency = cursor.getInt(indexFrequency);
        cursor.close();
        updateFrequency(id, frequency + 1);
    }

    private int updateFrequency(int rowId, int updatedFrequency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDictionary.Words.FREQUENCY, updatedFrequency);
        String selectionClause = UserDictionary.Words._ID + " = ?";
        String[] selectionArgs = { String.valueOf(rowId) };
        return getActivity().getContentResolver().update(
                UserDictionary.Words.CONTENT_URI,
                contentValues,
                selectionClause,
                selectionArgs
        );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Query query = getArguments().getParcelable(KEY_QUERY);
        return query.createSupportCursorLoader(getActivity());
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

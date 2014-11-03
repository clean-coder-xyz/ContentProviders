package contentproviders.learning.cleancoder.com.contentproviders;

import android.database.Cursor;
import android.provider.UserDictionary.Words;

import com.cleancoder.base.android.ui.ActivityHelper;
import com.cleancoder.base.android.ui.TaskFragment;
import com.cleancoder.base.android.util.TaggedLogger;
import com.cleancoder.base.common.data.TableRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 28.10.2014.
 */
public class UserDictionaryLoaderFragment extends TaskFragment {

    private static final TaggedLogger logger = TaggedLogger.forInstance(UserDictionaryLoaderFragment.class);

    public static interface Callbacks {
        void onUserDictionaryLoaded(List<TableRow> userDictionary);
        void onCannotGetUserDictionary(Throwable exception);
    }

    public static UserDictionaryLoaderFragment newInstance() {
        return new UserDictionaryLoaderFragment();
    }

    @Override
    protected void startTask() {
        try {
            final List<TableRow> userDictionary = getUserDictionary();
            returnResult(new ResultReturner() {
                @Override
                public void returnResult(Callbacks callbacks) {
                    callbacks.onUserDictionaryLoaded(userDictionary);
                }
            });
        } catch (final Throwable exception) {
            returnResult(new ResultReturner() {
                @Override
                public void returnResult(Callbacks callbacks) {
                    callbacks.onCannotGetUserDictionary(exception);
                }
            });
        }
    }

    private static interface ResultReturner {
        void returnResult(Callbacks callbacks);
    }

    private List<TableRow> getUserDictionary() {
        String[] projection = { Words.WORD, Words.LOCALE, Words.FREQUENCY };
        Cursor cursor = getActivity().getContentResolver().query(
                Words.CONTENT_URI,
                projection,
                null,
                null,
                Words.FREQUENCY);
        List<TableRow> userDictionary = new ArrayList<TableRow>(cursor.getCount());
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                userDictionary.add(readWord(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return userDictionary;
    }

    private TableRow readWord(Cursor cursor) {
        int indexWord = cursor.getColumnIndexOrThrow(Words.WORD);
        int indexLocale = cursor.getColumnIndexOrThrow(Words.LOCALE);
        int indexFrequency = cursor.getColumnIndexOrThrow(Words.FREQUENCY);
        TableRow tableRow = new TableRow(WordAttributes.FREQUENCY, WordAttributes.LOCALE, WordAttributes.WORD);
        String word = cursor.getString(indexWord);
        String locale = cursor.getString(indexLocale);
        int frequency = cursor.getInt(indexFrequency);
        logger.debug("==========================");
        logger.debug("  word = " + word);
        logger.debug("  locale = " + locale);
        logger.debug("  frequency = " + frequency);
        logger.debug("--------------------------");
        tableRow.set(WordAttributes.WORD, word);
        tableRow.set(WordAttributes.LOCALE, locale);
        tableRow.set(WordAttributes.FREQUENCY, frequency);
        return tableRow;
    }

    private void returnResult(ResultReturner resultReturner) {
        ActivityHelper activity = (ActivityHelper) getActivity();
        if (activity == null || activity.isDestroyed()) {
            return;
        }
        resultReturner.returnResult((Callbacks) activity);
    }

}

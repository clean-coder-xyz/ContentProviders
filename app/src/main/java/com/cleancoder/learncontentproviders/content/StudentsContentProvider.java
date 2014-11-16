package com.cleancoder.learncontentproviders.content;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.cleancoder.learncontentproviders.data.StudentsContract.StudentsEntry;
import com.cleancoder.learncontentproviders.data.StudentsDbHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Leonid on 15.11.2014.
 */
public class StudentsContentProvider extends ContentProvider {


    private static final int STUDENTS = 10;
    private static final int STUDENT_ID = 20;

    private static final String AUTHORITY = "com.cleancoder.learncontentproviders.content.students";

    private static final String BASE_PATH = "students";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/students";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/student";

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, STUDENTS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", STUDENT_ID);
    }

    private static final Set<String> AVAILABLE_COLUMNS = Collections.unmodifiableSet(
            new HashSet(Arrays.asList(
                    StudentsEntry._ID,
                    StudentsEntry.COLUMN_NAME,
                    StudentsEntry.COLUMN_AGE,
                    StudentsEntry.COLUMN_ACADEMIC_YEAR
            ))
    );

    private static void checkColumns(String[] projection) {
        if (projection != null) {
            Set<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            if (!AVAILABLE_COLUMNS.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    private StudentsDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new StudentsDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);
        queryBuilder.setTables(StudentsEntry.TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                break;
            case STUDENT_ID:
                queryBuilder.appendWhere(StudentsEntry._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                return CONTENT_DIR_TYPE;
            case STUDENT_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = 0;
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                id = db.insert(StudentsEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                rowsDeleted = db.delete(StudentsEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case STUDENT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(StudentsEntry.TABLE_NAME,
                            StudentsEntry._ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = db.delete(StudentsEntry.TABLE_NAME,
                            "( " + StudentsEntry._ID + "=" + id + " ) AND ( " + selection + " )",
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case STUDENTS:
                rowsUpdated = sqlDB.update(StudentsEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case STUDENT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(StudentsEntry.TABLE_NAME,
                            values,
                            StudentsEntry._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(StudentsEntry.TABLE_NAME,
                            values,
                            "( " + StudentsEntry._ID + "=" + id + " ) AND ( " + selection + " )",
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

}

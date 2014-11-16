package com.cleancoder.learncontentproviders.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cleancoder.learncontentproviders.Student;


/**
 * Created by Leonid Semyonov (clean-coder-xyz) on 26.10.2014.
 */
public class StudentsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    public StudentsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE_STUDENTS =
                "CREATE TABLE " + StudentsContract.StudentsEntry.TABLE_NAME + " (" +
                        StudentsContract.StudentsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        StudentsContract.StudentsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        StudentsContract.StudentsEntry.COLUMN_AGE + " INTEGER NOT NULL, " +
                        StudentsContract.StudentsEntry.COLUMN_ACADEMIC_YEAR + " INTEGER NOT NULL " +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StudentsContract.StudentsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public ReaderFromCursor<Student> getReaderFromCursor() {
        return READER_FROM_CURSOR;
    }

    private static final ReaderFromCursor<Student> READER_FROM_CURSOR = new ReaderFromCursor<Student>() {
        @Override
        public Student read(Cursor cursor) {
            int indexName = cursor.getColumnIndexOrThrow(StudentsContract.StudentsEntry.COLUMN_NAME);
            int indexAge = cursor.getColumnIndexOrThrow(StudentsContract.StudentsEntry.COLUMN_AGE);
            int indexAcademicYear = cursor.getColumnIndexOrThrow(StudentsContract.StudentsEntry.COLUMN_ACADEMIC_YEAR);
            return new Student()
                    .withName(cursor.getString(indexName))
                    .withAge(cursor.getInt(indexAge))
                    .withAcademicYear(cursor.getInt(indexAcademicYear));
        }
    };

}

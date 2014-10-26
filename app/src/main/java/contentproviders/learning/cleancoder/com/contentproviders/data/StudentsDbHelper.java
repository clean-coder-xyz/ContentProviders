package contentproviders.learning.cleancoder.com.contentproviders.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import contentproviders.learning.cleancoder.com.contentproviders.data.StudentsContract.*;


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
                "CREATE TABLE " + StudentsEntry.TABLE_NAME + " (" +
                        StudentsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        StudentsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        StudentsEntry.COLUMN_AGE + " INTEGER NOT NULL, " +
                        StudentsEntry.COLUMN_ACADEMIC_YEAR + " INTEGER NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StudentsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public ReaderFromCursor<Student> getReaderFromCursor() {
        return READER_FROM_CURSOR;
    }

    private static final ReaderFromCursor<Student> READER_FROM_CURSOR = new ReaderFromCursor<Student>() {
        @Override
        public Student read(Cursor cursor) {
            int indexName = cursor.getColumnIndexOrThrow(StudentsEntry.COLUMN_NAME);
            int indexAge = cursor.getColumnIndexOrThrow(StudentsEntry.COLUMN_AGE);
            int indexAcademicYear = cursor.getColumnIndexOrThrow(StudentsEntry.COLUMN_ACADEMIC_YEAR);
            return new Student()
                    .withName(cursor.getString(indexName))
                    .withAge(cursor.getInt(indexAge))
                    .withAcademicYear(cursor.getInt(indexAcademicYear));
        }
    };

}

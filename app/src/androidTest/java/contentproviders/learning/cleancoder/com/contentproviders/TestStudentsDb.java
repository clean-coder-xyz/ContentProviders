package contentproviders.learning.cleancoder.com.contentproviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import contentproviders.learning.cleancoder.com.contentproviders.data.Student;
import contentproviders.learning.cleancoder.com.contentproviders.data.StudentsContract.StudentsEntry;
import contentproviders.learning.cleancoder.com.contentproviders.data.StudentsDbHelper;

/**
 * Created by Leonid Semyonov (clean-coder-xyz) on 26.10.2014.
 */
public class TestStudentsDb extends AndroidTestCase {
    private static final String LOG_TAG = TestStudentsDb.class.getSimpleName();


    public void testCreateDb() {
        mContext.deleteDatabase(StudentsDbHelper.DATABASE_NAME);
        StudentsDbHelper dbHelper = new StudentsDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
        dbHelper.close();
    }


    public void testInsertReadDb() {
        mContext.deleteDatabase(StudentsDbHelper.DATABASE_NAME);

        StudentsDbHelper dbHelper = new StudentsDbHelper(mContext);

        final Student studentIn = new Student().withName("Emmanuel").withAge(18).withAcademicYear(1);

        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StudentsEntry.COLUMN_NAME, studentIn.getName());
        values.put(StudentsEntry.COLUMN_AGE, studentIn.getAge());
        values.put(StudentsEntry.COLUMN_ACADEMIC_YEAR, studentIn.getAcademicYear());
        writableDatabase.insertOrThrow(StudentsEntry.TABLE_NAME, null, values);
        writableDatabase.close();

        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + StudentsEntry.TABLE_NAME + ";", null);
        boolean valuesReturned = cursor.moveToFirst();
        if (!valuesReturned) {
            fail("No values returned");
        }

        Student studentOut = dbHelper.getReaderFromCursor().read(cursor);
        assertEquals(studentIn, studentOut);

        cursor.close();
        readableDatabase.close();

        dbHelper.close();
    }


}

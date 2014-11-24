package com.cleancoder.learncontentproviders;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.cleancoder.learncontentproviders.content.StudentsContentProvider;
import com.cleancoder.learncontentproviders.data.Student;
import com.cleancoder.learncontentproviders.data.StudentsContract.*;

public class StudentsActivity extends ActionBarActivity implements InputStudentsFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_input_students, new InputStudentsFragment())
                    .add(R.id.container_students_display, new StudentsDisplayFragment())
                    .commit();
        }
    }

    @Override
    public void onAddStudent(Student student) {
        ContentValues row = new ContentValues();
        row.put(StudentsEntry.COLUMN_NAME, student.getName());
        row.put(StudentsEntry.COLUMN_AGE, student.getAge());
        row.put(StudentsEntry.COLUMN_ACADEMIC_YEAR, student.getAcademicYear());
        getContentResolver().insert(StudentsContentProvider.CONTENT_URI, row);
    }

}

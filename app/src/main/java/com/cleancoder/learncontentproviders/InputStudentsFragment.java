package com.cleancoder.learncontentproviders;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cleancoder.base.android.ui.ToastUtils;
import com.cleancoder.base.common.util.Result;
import com.cleancoder.learncontentproviders.data.Student;

/**
 * Created by Leonid on 16.11.2014.
 */
public class InputStudentsFragment extends android.support.v4.app.Fragment {

    public static interface Callbacks {
        void onAddStudent(Student student);
    }

    private static final Callbacks DUMMY_CALLBACKS = new Callbacks() {
        @Override
        public void onAddStudent(Student student) {
            // do nothing
        }
    };

    private Callbacks callbacks;
    private EditText inputName;
    private EditText inputAge;
    private EditText inputAcademicYear;
    private View buttonAddStudent;
    private View contentView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        callbacks = DUMMY_CALLBACKS;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_input_students, null);
        initContentView();
        return contentView;
    }

    private void initContentView() {
        inputName = (EditText) contentView.findViewById(R.id.input_name);
        inputAge = (EditText) contentView.findViewById(R.id.input_age);
        inputAcademicYear = (EditText) contentView.findViewById(R.id.input_academic_year);
        buttonAddStudent = contentView.findViewById(R.id.button_add_student);
        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonAddStudentClicked();
            }
        });
    }

    private void onButtonAddStudentClicked() {
        Result<?> student = readStudent();
        if (student.isPresent()) {
            callbacks.onAddStudent((Student) student.get());
        } else {
            showIncorrectDetails(student.getException());
        }
    }

    private Result<?> readStudent() {
        Result<String> name = readName();
        if (!name.isPresent()) {
            return name;
        }
        Result<Integer> age = readAge();
        if (!age.isPresent()) {
            return age;
        }
        Result<Integer> academicYear = readAcademicYear();
        if (!academicYear.isPresent()) {
            return academicYear;
        }
        Student student = new Student()
                .withName(name.get())
                .withAge(age.get())
                .withAcademicYear(academicYear.get());
        return Result.get(student);
    }

    private void showIncorrectDetails(Throwable exception) {
        ToastUtils.SHORT.show(getActivity(), exception.getMessage());
    }

    private Result<String> readName() {
        String name = readText(inputName);
        Throwable details = new Throwable(getString(R.string.input_student_name_cant_be_empty));
        return TextUtils.isEmpty(name) ? Result.<String>absent(details) : Result.get(name);
    }

    private Result<Integer> readAge() {
        int age;
        try {
            age = Integer.parseInt(readText(inputAge));
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            Throwable details = new Throwable(getString(R.string.invalid_input_student_age), exception);
            return Result.absent(details);
        }
        Throwable details = new Throwable(getString(R.string.negative_input_student_age));
        return (age >= 0) ? Result.get(age) : Result.<Integer>absent(details);
    }

    private Result<Integer> readAcademicYear() {
        int academicYear;
        try {
            academicYear = Integer.parseInt(readText(inputAcademicYear));
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            Throwable details = new Throwable(getString(R.string.invalid_input_student_academic_year), exception);
            return Result.absent(details);
        }
        Throwable details = new Throwable(getString(R.string.input_student_academic_year_starts_at_first_year));
        return (academicYear >= 1) ? Result.get(academicYear) : Result.<Integer>absent(details);
    }

    private static String readText(EditText editText) {
        return editText.getText().toString();
    }

}

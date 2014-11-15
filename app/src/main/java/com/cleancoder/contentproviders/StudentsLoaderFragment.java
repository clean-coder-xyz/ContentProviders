package com.cleancoder.contentproviders;

import com.cleancoder.base.android.ui.TaskFragment;

import java.util.List;

/**
 * Created by Leonid on 28.10.2014.
 */
public class StudentsLoaderFragment extends TaskFragment {

    public static interface Callbacks {
        void onStudentsLoaded(List<Student> students);
        void onException(Throwable exception);
    }

    public static StudentsLoaderFragment newInstance() {
        return new StudentsLoaderFragment();
    }

    @Override
    protected void startTask() {
        // TODO
    }

}
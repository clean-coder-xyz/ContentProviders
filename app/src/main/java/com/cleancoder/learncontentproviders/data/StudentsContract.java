package com.cleancoder.learncontentproviders.data;

import android.provider.BaseColumns;

/**
 * Created by Leonid Semyonov (clean-coder-xyz) on 26.10.2014.
 */
public class StudentsContract {


    public static final class StudentsEntry implements BaseColumns {
        public static final String TABLE_NAME = "students";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_ACADEMIC_YEAR = "academic_year";

    }

}

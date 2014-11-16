package com.cleancoder.learncontentproviders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Leonid on 29.10.2014.
 */
public class ExceptionDisplayFragment extends android.support.v4.app.Fragment {

    private static final String KEY_EXCEPTION = "exception";

    public static ExceptionDisplayFragment newInstance(Throwable exception) {
        ExceptionDisplayFragment fragment = new ExceptionDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_EXCEPTION, exception);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_exception_display, null);
        TextView descriptionTextView = (TextView) contentView.findViewById(R.id.description_text_view);
        Throwable exception = (Throwable) getArguments().getSerializable(KEY_EXCEPTION);
        descriptionTextView.setText(exception.getMessage());
        return contentView;
    }
}

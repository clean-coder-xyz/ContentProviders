package com.cleancoder.contentproviders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cleancoder.base.common.data.TableRow;

import java.io.Serializable;
import java.util.List;

import contentproviders.learning.cleancoder.com.contentproviders.R;

/**
 * Created by Leonid on 29.10.2014.
 */
public class UserDictionaryDisplayFragment extends android.support.v4.app.Fragment {

    private static final String KEY_USER_DICTIONARY = "user_dictionary";
    private View contentView;

    public static UserDictionaryDisplayFragment newInstance(List<TableRow> userDictionary) {
        UserDictionaryDisplayFragment fragment = new UserDictionaryDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_USER_DICTIONARY, (Serializable) userDictionary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_user_dictionary_display, null);
        initContentView();
        return contentView;
    }

    private void initContentView() {
        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        List<TableRow> userDictionary = (List<TableRow>) getArguments().getSerializable(KEY_USER_DICTIONARY);
        UserDictionaryArrayAdapter adapter = new UserDictionaryArrayAdapter(getActivity(), userDictionary);
        listView.setAdapter(adapter);
    }

}

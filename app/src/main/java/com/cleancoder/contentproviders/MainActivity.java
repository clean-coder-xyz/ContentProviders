package com.cleancoder.contentproviders;

import android.os.Bundle;
import android.provider.UserDictionary.Words;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.cleancoder.base.android.data.Query;
import com.cleancoder.base.android.ui.ActivityHelper;
import com.cleancoder.base.common.data.TableRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import contentproviders.learning.cleancoder.com.contentproviders.R;


public class MainActivity extends ActivityHelper implements UserDictionaryLoaderFragment.Callbacks {

    private static final MenuItemState DEFAULT_MENU_ITEM_STATE = MenuItemState.ENABLED;

    private final Object LOCK_MENU = new Object();

    private Map<Integer, MenuItemState> menuItemStates;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuItemStates = new HashMap<Integer, MenuItemState>();
        setMenuItemState(R.id.action_reload, MenuItemState.HIDDEN);
        if (savedInstanceState == null) {
            loadUserDictionary();
        }
    }

    private void loadUserDictionary() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, createUserDictionaryDisplayViaCursorLoaderFragment())
                .commit();
    }

    private Fragment createUserDictionaryDisplayViaCursorLoaderFragment() {
        Query query = new Query();
        query.setUri(Words.CONTENT_URI);
        query.setProjection(new String[]{
                Words._ID, Words.WORD, Words.LOCALE, Words.FREQUENCY
        });
        query.setSortOrder(Words.WORD);
        String[] columnsToDisplay = {
                Words.WORD, Words.LOCALE, Words.FREQUENCY
        };
        int[] viewIds = { R.id.word_text_view, R.id.locale_text_view, R.id.frequency_text_view };
        return UserDictionaryDisplayViaCursorLoaderFragment.newInstance(query, columnsToDisplay, viewIds);
    }

    private Fragment createUserDictionaryLoaderFragment() {
        Query query = new Query();
        query.setUri(Words.CONTENT_URI);
        query.setProjection(new String[] {
                Words.WORD, Words.LOCALE, Words.FREQUENCY
        });
        query.setSortOrder(Words.WORD);
        return UserDictionaryLoaderFragment.newInstance(query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        synchronized (LOCK_MENU) {
            this.menu = menu;
            updateMenuItemStates();
        }
        return true;
    }

    private void updateMenuItemStates() {
        int numberOfMenuItems = menu.size();
        for (int i = 0; i < numberOfMenuItems; ++i) {
            MenuItem menuItem = menu.getItem(i);
            Integer id = menuItem.getItemId();
            MenuItemState state = NullUtils.getFirstNotNull(menuItemStates.get(id), DEFAULT_MENU_ITEM_STATE);
            state.setMenuItemState(menuItem);
        }
    }

    public void setMenuItemState(int menuItemId, MenuItemState state) {
        if (state == null) {
            throw new IllegalArgumentException("Menu item state cannot be <null>");
        }
        synchronized (LOCK_MENU) {
            menuItemStates.put(menuItemId, state);
            if (menu != null) {
                MenuItem menuItem = menu.findItem(menuItemId);
                if (menuItem != null) {
                    state.setMenuItemState(menuItem);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                loadUserDictionary();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserDictionaryLoaded(List<TableRow> userDictionary) {
        setMenuItemState(R.id.action_reload, MenuItemState.ENABLED);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, UserDictionaryDisplayFragment.newInstance(userDictionary))
                .commit();
    }

    @Override
    public void onCannotGetUserDictionary(Throwable exception) {
        setMenuItemState(R.id.action_reload, MenuItemState.ENABLED);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ExceptionDisplayFragment.newInstance(exception))
                .commit();
    }

}

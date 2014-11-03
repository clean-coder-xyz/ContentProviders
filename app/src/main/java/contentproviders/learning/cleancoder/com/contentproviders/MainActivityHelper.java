package contentproviders.learning.cleancoder.com.contentproviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cleancoder.base.android.ui.ActivityHelper;
import com.cleancoder.base.common.data.TableRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivityHelper extends ActivityHelper implements UserDictionaryLoaderFragment.Callbacks {

    private static final MenuItemState DEFAULT_MENU_ITEM_STATE = MenuItemState.ENABLED;

    private final Object LOCK_MENU = new Object();

    private Map<Integer, MenuItemState> menuItemStates;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuItemStates = new HashMap<Integer, MenuItemState>();
        loadUserDictionary();
    }

    private void loadUserDictionary() {
        setMenuItemState(R.id.action_reload, MenuItemState.HIDDEN);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, UserDictionaryLoaderFragment.newInstance())
                .commit();
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

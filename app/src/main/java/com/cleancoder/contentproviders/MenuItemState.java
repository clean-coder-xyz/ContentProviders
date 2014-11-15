package com.cleancoder.contentproviders;

import android.view.MenuItem;

/**
 * Created by Leonid on 29.10.2014.
 */
public enum MenuItemState {
    HIDDEN(new MenuItemStateChanger() {
        @Override
        public void changeMenuItemState(MenuItem menuItem) {
            menuItem.setVisible(false);
        }
    }),
    DISABLED(new MenuItemStateChanger() {
        @Override
        public void changeMenuItemState(MenuItem menuItem) {
            menuItem.setVisible(true);
            menuItem.setEnabled(false);
        }
    }),
    ENABLED(new MenuItemStateChanger() {
        @Override
        public void changeMenuItemState(MenuItem menuItem) {
            menuItem.setVisible(true);
            menuItem.setEnabled(true);
        }
    });

    private static interface MenuItemStateChanger {
        void changeMenuItemState(MenuItem menuItem);
    }

    private final MenuItemStateChanger menuItemStateChanger;

    private MenuItemState(MenuItemStateChanger menuItemStateChanger) {
        this.menuItemStateChanger = menuItemStateChanger;
    }

    public void setMenuItemState(MenuItem menuItem) {
        menuItemStateChanger.changeMenuItemState(menuItem);
    }

}

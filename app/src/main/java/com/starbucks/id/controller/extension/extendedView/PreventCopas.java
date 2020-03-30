package com.starbucks.id.controller.extension.extendedView;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Angga N P on 2/19/2019.
 */

public class PreventCopas implements ActionMode.Callback {
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public void onDestroyActionMode(ActionMode mode) {
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }
}

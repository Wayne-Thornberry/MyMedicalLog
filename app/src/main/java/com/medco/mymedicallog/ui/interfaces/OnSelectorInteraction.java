package com.medco.mymedicallog.ui.interfaces;


import android.view.View;
import com.medco.mymedicallog.entities.SelectorItem;

public interface OnSelectorInteraction{
    void onItemSelected(View viewParent, SelectorItem item);
}
package com.medco.mymedicallog.ui.adapters.holders;


import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.entities.SelectorItem;

public class SelectorHolder extends RecyclerView.ViewHolder {

    public SelectorItem item;
    public View viewParent;
    public TextView viewName;

    public SelectorHolder(View view) {
        super(view);
        viewParent = view;
        viewName = view.findViewById(R.id.selector_title);
    }

    @Override
    public String toString() {
        return super.toString() + " ";
    }
}

package com.medco.mymedicallog.ui.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medco.mymedicallog.R;
import com.medco.mymedicallog.ui.adapters.holders.SelectorHolder;

import com.medco.mymedicallog.entities.SelectorItem;
import com.medco.mymedicallog.ui.creation.entry.EntryTouchInputFragment;
import com.medco.mymedicallog.ui.interfaces.OnCreationInteractionListener;
import com.medco.mymedicallog.ui.interfaces.OnSelectorInteraction;

import java.util.List;

public class SelectorAdapter extends RecyclerView.Adapter<SelectorHolder> {

    private final List<SelectorItem> mValues;
    private final OnSelectorInteraction mListener;

    public SelectorAdapter(List<SelectorItem> items, OnSelectorInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public SelectorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_selector_item, parent, false);
        return new SelectorHolder(view);
    }

    @Override
    public void onBindViewHolder(final SelectorHolder holder, int position) {
        holder.item = mValues.get(position);
        holder.viewName.setText(holder.item.title);
        if(holder.item.selected)
            holder.viewParent.setBackgroundColor(holder.viewName.getResources().getColor(R.color.color_button_selected));
        holder.viewParent.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onItemSelected(holder.viewParent, holder.item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}

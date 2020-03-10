package com.medco.mymedicallog.adapters.holders;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.database.entities.ProfileLog;

public class ProfileLogHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public ProfileLog mItem;

    public ProfileLogHolder(View view) {
        super(view);
        mView = view;
    }
}
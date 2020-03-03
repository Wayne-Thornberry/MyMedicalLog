package com.medco.mymedicallog.adapters.holders;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.database.entities.Profile;

public class ProfileHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mIdView;
    public final TextView mContentView;
    public Profile mItem;

    public ProfileHolder(View view) {
        super(view);
        mView = view;
        mIdView = (TextView) view.findViewById(R.id.profile_name);
        mContentView = (TextView) view.findViewById(R.id.last_modified_date);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }
}
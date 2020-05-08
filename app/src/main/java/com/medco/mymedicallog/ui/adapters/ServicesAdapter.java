package com.medco.mymedicallog.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.data.ServiceData;

public class ServicesAdapter extends BaseAdapter {

    private final ServiceData[] services;
    private final Context mContext;

    public ServicesAdapter(Context context, ServiceData[] services) {
        this.mContext = context;
        this.services = services;
    }

    @Override
    public int getCount() {
        return services.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dummyTextView = new TextView(mContext);
        // 1
        final ServiceData book = services[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.view_service_item, null);
        }
        return convertView;
    }
}

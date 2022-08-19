package com.smartclassroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SubjectListItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<String> list;

    public SubjectListItemAdapter(Context context, int layout, List<String> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // View Holder Pattern (optimización)
        SubjectViewHolder viewHolder;

        if (view == null) {
            // Inflamos la vista con nuestro layout (item)
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(layout, null);

            viewHolder = new SubjectViewHolder();

            // obtenemos la referencia de los componentes del layout
            viewHolder.textViewSubjectName = view.findViewById(R.id.textViewSubjectName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (SubjectViewHolder) view.getTag();
        }

        // Cambiamos sus valores a la vista (layout)
        String current = list.get(i);
        viewHolder.textViewSubjectName.setText(current);

        return view;
    }

    static class SubjectViewHolder {
        private TextView textViewSubjectName;
    }
}

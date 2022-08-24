package com.smartclassroom.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.smartclassroom.Views.AttendancesActivity;
import com.smartclassroom.Views.StudentsActivity;

import java.util.List;
import java.util.Map;

public class SubjectExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> subjectListGroup;
    Map<String, List<String>> subjectListGroupChild;
    OnItemClickListener onItemClickListener;

    public SubjectExpandableListAdapter(Context context, List<String> subjectListGroup, Map<String, List<String>> subjectListGroupChild) {
        this.context = context;
        this.subjectListGroup = subjectListGroup;
        this.subjectListGroupChild = subjectListGroupChild;
    }

    @Override
    public int getGroupCount() {
        return subjectListGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return subjectListGroupChild.get(subjectListGroup.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return subjectListGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return subjectListGroupChild.get(subjectListGroup.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater
                .from(context)
                .inflate(android.R.layout.simple_expandable_list_item_1, viewGroup, false);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getGroup(i).toString());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater
                .from(context)
                .inflate(android.R.layout.simple_selectable_list_item, viewGroup, false);

        String childName = getChild(i, i1).toString();
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(childName);

        // set on click listener
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (childName.equals("Students")) {
                    intent = new Intent(context, StudentsActivity.class);
                } else if (childName.equals("Attendance")) {
                    intent = new Intent(context, AttendancesActivity.class);
                } else {
                    intent = null;
                }
                context.startActivity(intent);

                //Toast.makeText(view.getContext(), textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public interface OnItemClickListener {
        void onItemClick(String childName, int position);
    }
}

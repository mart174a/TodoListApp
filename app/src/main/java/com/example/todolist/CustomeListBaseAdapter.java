package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class CustomeListBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<ToDoListItem> sortedList;
    ArrayList<ToDoListItem> originalList;

    LayoutInflater inflater;

    int sortId = 0;

    public CustomeListBaseAdapter(Context ctx, ArrayList<ToDoListItem> list){
        this.context = ctx;

        this.originalList = list;
        inflater = LayoutInflater.from(ctx);

        SortList();
    }

    @Override
    public int getCount() {
        return sortedList.toArray().length;
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
        convertView = inflater.inflate(R.layout.activity_list_item, null);
        ToDoListItem currentItem = sortedList.get(position);

        TextView titleView = (TextView) convertView.findViewById(R.id.listItem_Title);
        TextView textView = (TextView) convertView.findViewById(R.id.listItemText);
        TextView deadlineText = (TextView) convertView.findViewById(R.id.listItem_Deadline);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.listItemCheckBox);

        titleView.setText(currentItem.title);
        textView.setText(currentItem.text);

        checkBox.setChecked(currentItem.completed);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem.completed = checkBox.isChecked();
                if(sortId != 0){
                    SortList();
                }
                notifyDataSetChanged();
                DataManager.saveTodoList(context, originalList);
            }
        });

        Button deleteBtn = (Button) convertView.findViewById((R.id.listItemDeleteButton));
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalList.remove(currentItem);
                sortedList.remove(position);
                notifyDataSetChanged();
                DataManager.saveTodoList(context, originalList);
            }
        });

        if(currentItem.hasDeadline) {

            String formatedDate = currentItem.deadlineDate.getDate() +"/"+(currentItem.deadlineDate.getMonth() + 1)+"-"+currentItem.deadlineDate.getYear();
            deadlineText.setText(formatedDate);
        }
        else{
            deadlineText.setText("");
        }




        return convertView;
    }

    public void AddItem(ToDoListItem toDoListItem){
        originalList.add(toDoListItem);
        SortList();
        DataManager.saveTodoList(context, originalList);
    }

    public void SetSortType(int sortInt){
        sortId = sortInt;
        SortList();
    }

    private void SortList(){
        if(sortId == 0){
            SortAllDateAdded();
        } else if (sortId == 1) {
            SortViewOnlyUncompleted();
        } else if (sortId == 2) {
            SortByDeadlineDate();
        }
        else {
            SortAllDateAdded();
        }
    }

    private void SortAllDateAdded(){
        sortedList = (ArrayList<ToDoListItem>) originalList.clone();
        Collections.sort(sortedList, Comparator.comparing(o -> o.creationDate));
        notifyDataSetChanged();
    }

    private void SortViewOnlyUncompleted(){
        sortedList = (ArrayList<ToDoListItem>) originalList.clone();
        sortedList.removeIf(item -> item.completed);
        notifyDataSetChanged();
    }

    private void SortByDeadlineDate(){
        sortedList = (ArrayList<ToDoListItem>) originalList.clone();
        sortedList.removeIf(item -> !item.hasDeadline);
        Collections.sort(sortedList, Comparator.comparing(o -> o.deadlineDate));
        notifyDataSetChanged();
    }
}

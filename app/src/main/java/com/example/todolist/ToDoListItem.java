package com.example.todolist;

import java.util.Date;

public class ToDoListItem {
    public String title;
    public String text;
    public boolean completed;
    public boolean hasDeadline;
    public Date deadlineDate;
    public Date creationDate;


    public ToDoListItem(String title, String text,boolean completed, boolean hasDeadline, Date deadlineDate, Date creationDate){
        this.title = title;
        this.text = text;
        this.completed = completed;
        this.hasDeadline = hasDeadline;
        this.deadlineDate = deadlineDate;
        this.creationDate = creationDate;
    }
}

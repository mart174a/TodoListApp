package com.example.todolist;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DataManager {
    public static void saveTodoList(Context ctx, ArrayList<ToDoListItem> todoList) {
        SharedPreferences sharedPref = ctx.getSharedPreferences("my_todo_list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String jsonString = gson.toJson(todoList);

        editor.putString("todo_list_json", jsonString);
        editor.apply();
    }

    public static ArrayList<ToDoListItem> loadTodoList(Context ctx) {
        SharedPreferences sharedPref = ctx.getSharedPreferences("my_todo_list", Context.MODE_PRIVATE);
        String savedTodoListString = sharedPref.getString("todo_list_json", "");

        if (savedTodoListString.isEmpty()) {
            // Handle empty list (return empty list or default values)
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        TypeToken<ArrayList<ToDoListItem>> typeToken = new TypeToken<ArrayList<ToDoListItem>>() {};
        ArrayList<ToDoListItem> loadedTodoList = gson.fromJson(savedTodoListString, typeToken.getType());

        return loadedTodoList;
    }
}

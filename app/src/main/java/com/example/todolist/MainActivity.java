package com.example.todolist;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    CustomeListBaseAdapter customeListBaseAdapter;

    DatePickerDialog dialog;

    ArrayList<ToDoListItem> Titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dialog = new DatePickerDialog(this);

        Titles = DataManager.loadTodoList(getApplicationContext());

        listView = (ListView) findViewById(R.id.customListView);
        customeListBaseAdapter = new CustomeListBaseAdapter(getApplicationContext(), Titles);
        listView.setAdapter(customeListBaseAdapter);



        SetupTabs();

        SetupAddButton();
        SetupToggleAddButton();
        SetupOpenCalenderButton();
    }

    void SetupAddButton(){
        Button btn = findViewById(R.id.btn_AddItem);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = findViewById(R.id.editText_Title);
                EditText text = findViewById(R.id.editText_Text);
                CheckBox deadlineCheckBox = (CheckBox) findViewById(R.id.deadlineCheckbox);
                DatePicker datePicker = dialog.getDatePicker();
                Date currentDate = new Date();

                Date date = new Date(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                customeListBaseAdapter.AddItem(new ToDoListItem(title.getText().toString(),text.getText().toString(),false,deadlineCheckBox.isChecked(),date,currentDate));
                text.setText("");
                title.setText("");
                deadlineCheckBox.setChecked(true);
                hideKeyboard(v);
            }
        });
    }

    void SetupToggleAddButton(){
        Button btn = findViewById(R.id.btn_ToggleAddItem);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout layout = findViewById(R.id.AddItemLayout);

                if(layout.getVisibility() == View.VISIBLE){
                    layout.setVisibility(View.GONE);
                    btn.setText("+");
                    hideKeyboard(v);
                }
                else{
                    layout.setVisibility(View.VISIBLE);
                    btn.setText("v");
                }
            }
        });
    }

    void SetupOpenCalenderButton(){
        Button btn = findViewById(R.id.btn_OpenCalender);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog();
            }
        });
    }

    void SetupTabs(){
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                customeListBaseAdapter.SetSortType(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    void OpenDialog(){

        dialog = new DatePickerDialog(this);
        dialog.show();
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}
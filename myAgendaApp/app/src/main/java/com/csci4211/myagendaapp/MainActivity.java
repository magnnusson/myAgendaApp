package com.csci4211.myagendaapp;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csci4211.myagendaapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> todoList;
    private RecyclerView recyclerView;
    private TodoListAdapter todoListAdapter;
    private int position;
    private DataManager dataManager;
    private String itemNameToUpdate;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // connect the floating action button to the add item dialog
                AddTodoItemDialog addTodoItemDialog = new AddTodoItemDialog();
                addTodoItemDialog.show(getSupportFragmentManager(), "");
            }
        });

        // connect the todolist adapter to the main activity using recyclerView
        todoList = new ArrayList<Item>();
        recyclerView = findViewById(R.id.recyclerView);
        todoListAdapter = new TodoListAdapter(this, todoList);
        dataManager = new DataManager(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(todoListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNewTodoItem(Item item)
    {
        int priorityNum;

        String name = item.getName();
        String date =  item.getDate();
        String time = item.getTime();
        String description = item.getDescription();
        boolean priority = item.getPriority();

        if(priority)
        {
            priorityNum = 1;
        }
        else
            {
                priorityNum = 0;
            }

        dataManager.insert(name, date, time, description, priorityNum);
        loadData();
    }

    public void showTodoItem(int index)
    {
        position = index;
        ViewTodoItemDialog viewTodoItemDialog = new ViewTodoItemDialog();
        viewTodoItemDialog.sendSelectedItem(todoList.get(index));
        viewTodoItemDialog.show(getSupportFragmentManager(), "");
    }

    public void completeItem(Item item)
    {
        todoList.remove(item);
        dataManager.delete(item.getName());
        todoListAdapter.notifyDataSetChanged();
    }

    public void editItem(Item itemToEdit)
    {
        EditTodoItemDialog editTodoItemDialog = new EditTodoItemDialog();
        editTodoItemDialog.sendSelectedItem(itemToEdit);
        itemNameToUpdate = itemToEdit.getName();
        editTodoItemDialog.show(getSupportFragmentManager(), "");
    }

    public void updateTodoItem(Item item)
    {
        todoList.set(position, item);
        dataManager.update(itemNameToUpdate, item);
        todoListAdapter.notifyDataSetChanged();
    }

    public void loadData()
    {
        Cursor cursor = dataManager.selectAll();
        boolean priority;
        int itemCount = cursor.getCount();
        if(itemCount > 0)
        {
            todoList.clear(); // prevent duplicate data

            while(cursor.moveToNext()) {
                priority = false;
                String name = cursor.getString(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                String description = cursor.getString(4);
                int priorityNum = cursor.getInt(5);

                if (priorityNum == 1) {
                    priority = true;
                }

                Item item = new Item(0, name, date, time, description, priority);
                todoList.add(item);
            }
        }

        todoListAdapter.notifyDataSetChanged();

    }




}
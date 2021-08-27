package com.csci4211.myagendaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ListItemHolder> {
    private MainActivity mainActivity;
    private ArrayList<Item> todoList;

    // constructor using the main activity
    public TodoListAdapter(MainActivity mainActivity, ArrayList<Item> todoList)
    {
        this.mainActivity = mainActivity;
        this.todoList = todoList;
    }

    // add necessary methods and ListItemHolder class which will show the item name and date on the RecyclerView

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        Item item = todoList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewDate.setText(item.getDate());
        if(item.getPriority())
        {
            holder.textViewPriority.setText("***"); // let the user know item is a high priority from the home screen
        }
        else
            {
                holder.textViewPriority.setText("");
            }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView textViewName;
        private TextView textViewDate;
        private TextView textViewPriority;

        public ListItemHolder(View view)
        {
            super(view);

            textViewName = view.findViewById(R.id.textViewName);
            textViewDate = view.findViewById(R.id.textViewDate);
            textViewPriority = view.findViewById(R.id.textViewPriority);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        // click function is connected to viewing item fragment
        public void onClick(View view)
        {
            mainActivity.showTodoItem(getAdapterPosition());
        }

    }
}

package com.csci4211.myagendaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class AddTodoItemDialog extends DialogFragment {

    // reference variables for buttons and textedits
    private Button buttonAdd;
    private Button buttonClear;
    private Button buttonBack;

    private EditText nameEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText descriptionEditText;
    private CheckBox priorityCheckbox;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       super.onCreateDialog(savedInstanceState);

        // builder to display the activity fragment
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

       // inflate the layout defined by the add item dialog xml file
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_item_todo_add, null);

        builder.setView(dialogView);

        // obtain references for buttons
        buttonAdd = dialogView.findViewById(R.id.buttonAdd);
        buttonClear = dialogView.findViewById(R.id.buttonClear);
        buttonBack = dialogView.findViewById(R.id.buttonBack);

        nameEditText = dialogView.findViewById(R.id.textEditName);
        dateEditText = dialogView.findViewById(R.id.textEditDate);
        timeEditText = dialogView.findViewById(R.id.textEditTime);
        descriptionEditText = dialogView.findViewById(R.id.textEditDescription);
        priorityCheckbox = dialogView.findViewById(R.id.checkBoxHighPriority);

        // button functionalities:
        // just dismiss the fragment when user clicks back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                dismiss();
            }
          }
        );

        // clear all the textEdit views when user clicks clear button
        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                nameEditText.setText("");
                dateEditText.setText("");
                timeEditText.setText("");
                descriptionEditText.setText("");
                priorityCheckbox.setChecked(false);
                nameEditText.requestFocus();
            }
        });

        // add the to-do item to the list on the main activity using the help of the Item class, include error handling!
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                String name = nameEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String time = timeEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                boolean priority;

                if(priorityCheckbox.isChecked())
                {
                    priority = true;
                }
                else
                    {
                        priority = false;
                    }

                Item item = new Item(0, name, date, time, description, priority);
                if(name.isEmpty())
                {
                    Toast.makeText(getActivity(), "ERROR: A name for your list item is required.", Toast.LENGTH_LONG).show();
                }
                else
                    {
                        MainActivity callingActivity = (MainActivity) getActivity();
                        callingActivity.addNewTodoItem(item);
                        dismiss();
                    }
            }
          }
        );




       // return the builder to the main activity
       return builder.create();
    }

}

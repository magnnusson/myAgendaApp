package com.csci4211.myagendaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class ViewTodoItemDialog extends DialogFragment {

    // same process as Add dialog, but with different buttons.

    private Item item;

    private TextView textViewName;
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewDescription;
    private TextView textViewPriority;
    private Button buttonComplete;
    private Button buttonEdit;
    private Button buttonBack;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        // build and inflate the layout
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_item_todo_view, null);

        // find references to textview vars
        textViewName = dialogView.findViewById(R.id.textViewName);
        textViewDate = dialogView.findViewById(R.id.textViewDate);
        textViewTime = dialogView.findViewById(R.id.textViewTime);
        textViewDescription = dialogView.findViewById(R.id.textViewDescription);
        textViewPriority = dialogView.findViewById(R.id.textViewPriority);
        buttonComplete = dialogView.findViewById(R.id.buttonComplete);
        buttonEdit = dialogView.findViewById(R.id.buttonEdit);
        buttonBack = dialogView.findViewById(R.id.buttonViewBack);

        // set text so the user can see the displayed information
        textViewName.setText(item.getName());
        textViewDate.setText(item.getDate());
        textViewTime.setText(item.getTime());
        textViewDescription.setText(item.getDescription());

        if(item.getPriority())
        {
            textViewPriority.setText("Yes");
        }
        else
            {
                textViewPriority.setText("No");
            }

        builder.setView(dialogView);

        // button functionalities for back, edit, and completion of an item.
        buttonBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                dismiss();
            }

        });

        buttonComplete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.completeItem(item); // IMPLEMENT COMPLETEITEM FUNCTION
                dismiss();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.editItem(item); // IMPLEMENT EDITITEMFUNCTION
                dismiss();
            }
        });

        return builder.create();
    }


    // need a function to show a specified contact.
    public void sendSelectedItem(Item item)
    {
        this.item = item;
    }
}

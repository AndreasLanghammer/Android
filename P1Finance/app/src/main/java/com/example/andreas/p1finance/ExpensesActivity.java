package com.example.andreas.p1finance;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExpensesActivity extends AppCompatActivity {

    String choseCategoryArray [] ={"Category", "Food", "Household", "Travel", "Other"};

    private EditText etName;
    private EditText etDate;
    private EditText etAmmount;
    private TextView tvName;
    private TextView tvDate;
    private TextView tvAmmount;
    private Spinner spinnerCategory;
    private Button btnSave;
    private String name, date, ammount, spinner;
    private MainController controller;

    //Constructors
    public ExpensesActivity(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        controller = new MainController(this);
        initExpensesComponents();
    }

    private void initExpensesComponents() {

        //Find references
        ButtonListner bl = new ButtonListner();
        FragmentManager fm = getFragmentManager();
        ExpensesFragment expensesFrag = (ExpensesFragment)fm.findFragmentById(R.id.expenses_fragment);
        etName = (EditText) findViewById(R.id.et_expenses_name);
        etDate = (EditText) findViewById(R.id.et_expenses_date);
        etAmmount = (EditText) findViewById(R.id.et_expenses_ammount);

        tvName = (TextView) findViewById(R.id.tv_expenses_name);
        tvDate = (TextView) findViewById(R.id.tv_expenses_date);
        tvAmmount = (TextView) findViewById(R.id.tv_expenses_ammount);
        spinnerCategory = (Spinner) findViewById(R.id.expenses_spinner);
        btnSave = (Button) findViewById(R.id.btn_expenses_save);
        btnSave.setOnClickListener(bl);

        //Creating a spinner dropdown list for categorys
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, choseCategoryArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private class ButtonListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_expenses_save);{
                //Read all edittext and spinner then save to database...
                name = etName.getText().toString();
                date = etDate.getText().toString();
                ammount = etAmmount.getText().toString();
                spinner = spinnerCategory.getSelectedItem().toString();


                //Toast latest info for user
                Context context = getApplicationContext();
                CharSequence text = name+"\n"+date+"\n"+ammount+"\n"+spinner;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Add values to the database
                Value value = new Value(name,date,ammount,spinner);
                controller.insertExpenses(value);
                controller.updateTexts();
                Log.e("Date",""+value.getDate());
                finish();
            }
        }
    }
}

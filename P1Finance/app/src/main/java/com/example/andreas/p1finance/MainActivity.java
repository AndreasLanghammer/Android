package com.example.andreas.p1finance;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnIncome;
    private Button btnExpenses;
    private Button btnStatment;
    private TextView tvEnterName, tvTotExpenses, tvTotIncome, tvBalance;
    private EditText editFName, editSName;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController(this);
        initComponents();
        checkRegister();
    }



    private void initComponents() {
        DataBase db = new DataBase(this);
        ButtonListner bl = new ButtonListner();
        btnIncome = (Button)findViewById(R.id.btn_income);
        btnIncome.setOnClickListener(bl);
        btnExpenses =(Button)findViewById(R.id.btn_expenses);
        btnExpenses.setOnClickListener(bl);
        btnStatment = (Button)findViewById(R.id.btn_statment);
        btnStatment.setOnClickListener(bl);
        tvEnterName = (TextView)findViewById(R.id.tv_enter_name);
        tvTotExpenses =(TextView)findViewById(R.id.tv_expenses_tot);
        tvTotIncome =(TextView)findViewById(R.id.tv_income_tot);
        tvBalance = (TextView)findViewById(R.id.tv_balance);
    }

    //Add/ login a user.
    private void checkRegister() {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String surname = prefs.getString("surname", null);
        if (name != null && surname != null) {
            tvEnterName.setText("User: " + name + " " + surname);
        }
        else {
            newClient();
        }
    }
    //if not logged in, then this method is executed
    private void newClient() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Welcome please enter:");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        editFName = new EditText(this);
        editFName.setHint("First name");
        editFName.setSingleLine();
        layout.addView(editFName);

        editSName = new EditText(this);
        editSName.setHint("Surname");
        editSName.setSingleLine();
        layout.addView(editSName);

        dialog.setView(layout);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editFName.getText().toString().trim();
                String surname = editSName.getText().toString().trim();
                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                editor.putString("name", name);
                editor.putString("surname", surname);
                editor.commit();
                checkRegister(); // Has to change the navHeader's text
            }
        });
        dialog.show();
    }

    void updateTextFields() {
        int income = controller.getTotIncome();
        int expenses = controller.getTotExpenses();
        int balance = controller.getBalance();
        tvTotIncome.setText("Income: " + income);
        tvTotExpenses.setText("Expenses: " + expenses);
        tvBalance.setText("Balance: " + balance);
        Log.e("hej", "du");
    }
    @Override
    public  void onResume(){
        super.onResume();
        controller.updateTexts();
        updateTextFields();
    }

    //ButtonListners listning for input
    private class ButtonListner implements View.OnClickListener{
        //check button that is clicked.
        @Override
        public void onClick(View v) {
            int temId = v.getId();
            Fragment fragment = null;

            if (temId == R.id.btn_expenses) { //to add expenses
                Intent intent = new Intent(MainActivity.this, ExpensesActivity.class);
                MainActivity.this.startActivity(intent);
            } else if (temId == R.id.btn_income) { //to add income
                Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
                MainActivity.this.startActivity(intent);
            } else if (temId == R.id.btn_statment) {//to show statement
                controller.showList();
            }
        }
    }
}

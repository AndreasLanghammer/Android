package com.example.andreas.p1finance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StatementActivity extends Activity {

    private ListView lvStatement;
    private TextView title, date, amount;
    private ImageView food, house, travel, other, category;
    private int textSize = 20;

    public StatementActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        lvStatement = (ListView)findViewById(R.id.lv_statement);
        Intent intent = getIntent();
        new StatementController(this, intent);


        lvStatement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Value value = (Value) parent.getItemAtPosition(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());

                LinearLayout layout = new LinearLayout(view.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50, 50, 0, 0);

                title = new TextView(view.getContext());
                title.setTextSize(textSize);
                title.setText("Title: " + value.getTitle());
                layout.addView(title);

                date = new TextView(view.getContext());
                date.setTextSize(textSize);
                date.setText("Date: " + value.getDate());
                layout.addView(date);

                amount = new TextView(view.getContext());
                amount.setTextSize(textSize);
                amount.setText("Amount: " + value.getAmount());
                layout.addView(amount);

                category = new ImageView(view.getContext());
                ImageView image = new ImageView(view.getContext());

                if (value.getCategory().equals("Food")){
                    category.setImageResource(R.drawable.food);
                }else if(value.getCategory().equals("Household")){
                    category.setImageResource(R.drawable.home);
                }else if(value.getCategory().equals("Travel")){
                    category.setImageResource(R.drawable.travel);
                }else if(value.getCategory().equals("Other")){
                    category.setImageResource(R.drawable.other);
                }

                layout.addView(category);
                dialog.setView(layout);
                dialog.setCancelable(false);
                dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                });
                dialog.show();

            }
        });
    }
    public void setAdapter(ArrayAdapter<Value> listviewAdapter) {
        lvStatement.setAdapter(listviewAdapter);
    }

}

package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {
    Spinner spinner;
    EditText contactname,contactnumber;
    Button save;
    String pos;
    ArrayAdapter<CharSequence>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        spinner=(Spinner)findViewById(R.id.spinner1);
        contactname=(EditText)findViewById(R.id.editText);
        contactnumber=(EditText)findViewById(R.id.editText2);
        save=(Button)findViewById(R.id.button9);

        //initializing spinner

        adapter=ArrayAdapter.createFromResource(this,R.array.contacts,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                pos=adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(position)+"is selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //saving the contact details;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contactname.length() ==0) {
                    contactname.setError("please fill this.");
                } else if(contactnumber.length() ==0) {
                    contactnumber.setError("please fill the blank");

                }else {
                    if(pos.equalsIgnoreCase("Contact_1"))
                    {
                        SharedPreferences sharedPreferences1 = getSharedPreferences("my_contacts",MODE_PRIVATE);
                        SharedPreferences.Editor editor1= sharedPreferences1.edit();

                        editor1.putString("contact1",contactname.getText().toString());
                        editor1.putString("number1",contactnumber.getText().toString());
                        editor1.commit();
                        String num1=sharedPreferences1.getString("number1","None");
                        Toast.makeText(ContactActivity.this,num1+" number is sucessfully saved",Toast.LENGTH_SHORT).show();
                        contactnumber.setText(null);
                        contactname.setText(null);

                    }
                    else if(pos.equalsIgnoreCase("Contact_2"))
                    {
                        SharedPreferences sharedPreferences1 = getSharedPreferences("my_contacts",MODE_PRIVATE);
                        SharedPreferences.Editor editor1= sharedPreferences1.edit();
                        editor1.putString("contact2",contactname.getText().toString());
                        editor1.putString("number2",contactnumber.getText().toString());
                        editor1.commit();
                        String num2=sharedPreferences1.getString("number2","None");
                        Toast.makeText(ContactActivity.this,num2+" number is sucessfully saved",Toast.LENGTH_SHORT).show();
                        contactnumber.setText(null);
                        contactname.setText(null);

                    }
                    else if(pos.equalsIgnoreCase("Contact_3"))
                    {
                        SharedPreferences sharedPreferences1 = getSharedPreferences("my_contacts",MODE_PRIVATE);
                        SharedPreferences.Editor editor1= sharedPreferences1.edit();
                        editor1.putString("contact3",contactname.getText().toString());
                        editor1.putString("number3",contactnumber.getText().toString());
                        editor1.commit();
                        String num3=sharedPreferences1.getString("number3","None");
                        Toast.makeText(ContactActivity.this,num3+" number is sucessfully saved",Toast.LENGTH_SHORT).show();
                        contactnumber.setText(null);
                        contactname.setText(null);
                }
               // Toast.makeText(ContactActivity.this,pos+" is clicked",Toast.LENGTH_SHORT).show();



            }
        };









    });
    }}

package com.example.emergencyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name,pass;
    CheckBox checkBox;
    Button button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;




    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name =(EditText)findViewById(R.id.editText4);
        pass=(EditText)findViewById(R.id.editText6);
        checkBox=(CheckBox)findViewById(R.id.checkBox2);

        button=findViewById(R.id.button);
        sharedPreferences =getSharedPreferences("loginref",MODE_PRIVATE);
        editor=sharedPreferences.edit();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlogin();

            }


        });
        savelogin=sharedPreferences.getBoolean("savelogin",true);
        if(savelogin == true)
        {
            name.setText(sharedPreferences.getString("username",null));
            pass.setText(sharedPreferences.getString("password",null));
        }
       // finish();



    }


    private void getlogin() {
        String username=name.getText().toString();
        String password=pass.getText().toString();
        if(checkBox.isChecked())
        {
            editor.putBoolean("savelogin",true);
            editor.putString("username",username);
            editor.putString("password",password);
            editor.commit();
            if (name.length() ==0) {
                name.setError("Enter name");
            } else if (pass.length() ==0) {
                pass.setError("Enter password");
            }
            else
            {
                Intent intent = new Intent(MainActivity.this,welcomeActivity.class);
                startActivity(intent);

            }


        }else
        {
            Toast.makeText(MainActivity.this,"please clicked on \"remember me\"",Toast.LENGTH_LONG).show();
        }
    }


}

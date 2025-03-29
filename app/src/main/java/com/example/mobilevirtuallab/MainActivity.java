package com.example.mobilevirtuallab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button login;
    public static final String MSSVpattern = "[1-2][0-9]+";
    private boolean isValidMSSV ;
    private EditText txtMSSV;
    private TextView txtValidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        txtMSSV = (EditText) findViewById(R.id.Login_mssv);
        txtValidate = (TextView) findViewById(R.id.txtValidate);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidMSSV){
                    Toast.makeText(getApplicationContext(), "Mã số sinh viên không chính xác", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent myintent = new Intent(MainActivity.this,ChildActivity.class);
                startActivity(myintent);
            }
        });

        txtMSSV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtValidate.setText("");
                String mssv = txtMSSV.getText().toString().trim();
                isValidMSSV = (mssv.matches(MSSVpattern) && s.length() == 7);
                if(!isValidMSSV){
                    txtValidate.setTextColor(Color.rgb(255,0,0));
                    txtValidate.setText("Mã số sinh viên không chính xác");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
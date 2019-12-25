package com.example.savetextinnerfile521;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private final String FILE_LOGIN = "login.txt";
    private final String FILE_PASSWORD = "password.txt";
    EditText editLogin;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);
        Button buttonOk = findViewById(R.id.buttonOK);
        Button buttonRegistration = findViewById(R.id.buttonRegistration);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editLogin.getText().toString();
                String password = editPassword.getText().toString();
                if (login.equals("") || password.equals("")) {
                    toastZeroEdit();
                } else {
                    int count = writeData(login, password);
                    if (count == 2) {
                        Toast.makeText(MainActivity.this,
                                getString(R.string.writing_successfully),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editLogin.getText().toString();
                String password = editPassword.getText().toString();
                String loginSave ="";
                String passwordSave = "";
                if (login.equals("") || password.equals("")) {
                    toastZeroEdit();
                } else {
                    loginSave = readFile(FILE_LOGIN, R.string.error_read_login);
                    passwordSave = readFile(FILE_PASSWORD, R.string.error_read_password);
                    if (login.equals(loginSave) && password.equals(passwordSave)) {
                        Toast.makeText(MainActivity.this,
                                getString(R.string.data_equal),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this,
                                getString(R.string.data_not_equal),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private int writeData(String login, String password) {
        int count = 0;
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_LOGIN, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            bw.write(login);
            bw.close();
            count+=1;
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,
                    getString(R.string.error_write_login),
                    Toast.LENGTH_SHORT).show();
        }
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_PASSWORD, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            bw.write(password);
            bw.close();
            count+=1;
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,
                    getString(R.string.error_write_password),
                    Toast.LENGTH_SHORT).show();
        }
        return count;
    }

    private String readFile(String fileName, int resource) {
        String loginSave = "";
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            loginSave = br.readLine();
            br.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,
                    getString(resource),
                    Toast.LENGTH_SHORT).show();
        }
        return loginSave;
    }

    private void toastZeroEdit() {
        Toast.makeText(MainActivity.this,
                getString(R.string.zero_login_password),
                Toast.LENGTH_SHORT).show();
    }
}

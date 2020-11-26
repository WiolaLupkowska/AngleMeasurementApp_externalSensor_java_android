package com.example.rehabilitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LogRegister extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_register);
    }


    public void OnClickLog(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


}

package com.example.rehabilitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mbientlab.metawear.data.Sign;

public class SignUp extends AppCompatActivity {

    TextInputLayout registerName, registerUsername, registerPhone, registerEmail, registerPassword;
    Button btnRegister;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registerName = findViewById(R.id.register_name);
        registerUsername = findViewById(R.id.register_username);
        registerPhone = findViewById(R.id.register_phone);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);


    }

    public void onClickBtnRejestracja(View view) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        //pobranie wartości z textfields
        String name = registerName.getEditText().getText().toString();
        String username = registerUsername.getEditText().getText().toString();
        String phone = registerPhone.getEditText().getText().toString();
        String email = registerEmail.getEditText().getText().toString();
        String password = registerPassword.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, phone, email,password);
        reference.child(phone).setValue(helperClass);







    }

}

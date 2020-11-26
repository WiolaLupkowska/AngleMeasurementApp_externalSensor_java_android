package com.example.rehabilitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {


    Button btnEmail, btnPassword;
    TextInputLayout logInUsername, logInPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInUsername = findViewById(R.id.log_in_username);
        logInPassword = findViewById(R.id.log_in_password);

    }


    private Boolean validatePassword(){
        String val = logInPassword.getEditText().getText().toString();
        if(val.isEmpty()){
            logInPassword.setError("Pole nie może być puste");
            return false;
        }else{
            logInPassword.setError(null);
            logInPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername(){
        String val = logInUsername.getEditText().getText().toString();
        if(val.isEmpty()){
            logInUsername.setError("Pole nie może być puste");
            return false;
        }else{
            logInUsername.setError(null);
            logInUsername.setErrorEnabled(false);
            return true;
        }
    }
//
//    public void loginUser (View view){
//        //sprawdzenie poprawnosci logowania
//        if(!validateEmail() | !validatePassword()){
//            return;
//        }else{
//            isEmail();
//        }
//    }

    private void isUsername() {//sprawdzanie podanego Username  w bazie, pobranie przypisanego do niego hasla
        String userEnteredUsername = logInUsername.getEditText().getText().toString().trim();
        String userEnteredPassword = logInPassword.getEditText().getText().toString().trim();
        System.out.println(userEnteredPassword);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);//szukanie w bazie wpisanego emailu

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){//jesli są dane w datasnapshot

                    logInUsername.setError(null);
                    logInUsername.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    System.out.println(passwordFromDB);
                    if(passwordFromDB.equals(userEnteredPassword)){

                        logInUsername.setError(null);
                        logInUsername.setErrorEnabled(false);

                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String phoneFromDB = dataSnapshot.child(userEnteredUsername).child("phone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("phone", phoneFromDB);
                        intent.putExtra("email", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    }else{
                        logInPassword.setError("Nieprawidłowe hasło");
                        logInPassword.requestFocus();
                    }
                }else{
                    logInUsername.setError("Nieprawidłowy email");
                    logInUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void onClickBtnZaloguj(View view) {
        if(!validateUsername() | !validatePassword()){
            return;
        }else{
            isUsername();
        }
    }

    public void onClickBtnZarejestruj(View view) {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}

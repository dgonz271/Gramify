package com.example.gramify;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gramify.fragments.ComposeFragment;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    //login credentials
    private EditText eTextUsername;
    private EditText eTextPassword;
    private Button loginButton;

    //Sign up credentials
    private EditText eTextEmail;
    private EditText eTextCreateUsername;
    private EditText eTextCreatePassword;
    private Button signupButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //This method will check if a user was already logged in to determine what activity to go to Main or Login
        checkIfLoggedIn();

        //login credentials
        eTextUsername = findViewById(R.id.et_username);
        eTextPassword = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.login_button);

        //signup credentials
        eTextEmail = findViewById(R.id.etEmail);
        eTextCreateUsername = findViewById(R.id.et_create_username);
        eTextCreatePassword = findViewById(R.id.et_create_password);
        signupButton = findViewById(R.id.create_account_button);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = eTextUsername.getText().toString();
                String password = eTextPassword.getText().toString();

                //call method where user logs in
                login(username, password);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eTextEmail.getText().toString();
                String createdUsername = eTextCreateUsername.getText().toString();
                String createdPassword = eTextCreatePassword.getText().toString();

                signUp(email, createdUsername, createdPassword);
            }
        });
    }

    private void signUp(String email, String createdUsername, String createdPassword) {
        // Creating ParseUser
        ParseUser user = new ParseUser();
        // Setting core properties
        user.setUsername(createdUsername);
        user.setPassword(createdPassword);
        user.setEmail(email);

        // Invoking signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error signing up");
                    Toast.makeText(LoginActivity.this, "Error signing up", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }
                Log.d(TAG, "Successfully navigating to new activity");
                Toast.makeText(LoginActivity.this, "You're logged in as: " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }
        });

    }

    private void checkIfLoggedIn() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null)
            goToMainActivity();
    }

    private void login(final String username, final String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //TODO: Better error handling
                //ParseException e will be null if everything runs smooth
                //Otherwise there was an issue with logging in
                if (e != null) {
                    Log.e(TAG, "Error logging in");
                    Toast.makeText(LoginActivity.this, "Error logging in", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }

                Log.d(TAG, "Successfully navigating to new activity");
                Toast.makeText(LoginActivity.this, "You're logged in as: " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
                goToMainActivity();


            }
        });
    }

    private void goToMainActivity() {
        Log.d(TAG, "Headed to new activity");
        //Sends user to new activity
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }
}

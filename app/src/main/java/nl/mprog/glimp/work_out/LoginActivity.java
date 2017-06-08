package nl.mprog.glimp.work_out;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText emailET;
    private EditText passwordET;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialise Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // initialise EditTexts
        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);

        setListener();
    }

    // set listener to check whether the user is signed in or not
    private void setListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // get current user
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                } else {
                    // user is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void logIn(View view) {

        // get email and password from EditTexts
        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                // triggers when login credentials are incorrect,
                                // or something else went wrong
                                Log.w("email", "signInWithEmail ", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Logged in user: " + email,
                                        Toast.LENGTH_SHORT).show();
                                goToMainActivity();
                            }
                        }
                    });
        } else if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter an email and password",
                    Toast.LENGTH_SHORT).show();
        }

        // clear EditTexts
        emailET.getText().clear();
        passwordET.getText().clear();
    }

    // registers a new user
    public void createUser(View view) {

        // get email and password from EditTexts
        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        // email must be nonempty, password must be at least 6 characters
        if (password.length() >= 6 && !email.isEmpty()) {

            // create new user and add to Firebase
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("create user", "createUserWithEmail:onComplete: "
                                    + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                // triggers when user is already registered,
                                // or something else went wrong
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // user is successfully created
                                Toast.makeText(LoginActivity.this, "Created user: " + email,
                                        Toast.LENGTH_SHORT).show();
                                goToMainActivity();
                            }
                        }
                    });
        } else if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter an email and password",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Password must be at least 6 characters long",
                    Toast.LENGTH_SHORT).show();
        }

        // clear EditTexts
        emailET.getText().clear();
        passwordET.getText().clear();
    }

    // sends the user to the MainActivity
    private void goToMainActivity() {
        this.startActivity(new Intent(this, MainActivity.class));
    }
}

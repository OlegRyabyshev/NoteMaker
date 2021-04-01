package xyz.fcr.notemaker.activity_classes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import xyz.fcr.notemaker.MainActivity;
import xyz.fcr.notemaker.R;

public class LoginActivity extends AppCompatActivity {

    private TextView mEmail;
    private TextView mPassword;
    private Button mLoginButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.text_email_login);
        mPassword = findViewById(R.id.text_password_login);
        mLoginButton = findViewById(R.id.button_login);
        firebaseAuth = FirebaseAuth.getInstance();

        mLoginButton.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                mEmail.setError("Email is required.");
                return;
            }

            if (TextUtils.isEmpty(password)){
                mPassword.setError("Password is required.");
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Welcome " + email, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}

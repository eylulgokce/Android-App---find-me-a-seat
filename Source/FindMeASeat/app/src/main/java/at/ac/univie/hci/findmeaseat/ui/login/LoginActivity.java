package at.ac.univie.hci.findmeaseat.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import at.ac.univie.hci.findmeaseat.MainActivity;
import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.user.AuthenticationService;
import at.ac.univie.hci.findmeaseat.model.user.AuthenticationServiceFactory;


public class LoginActivity extends AppCompatActivity {

    private final AuthenticationService authenticationService = AuthenticationServiceFactory.getSingletonInstance();

    private TextView usernameTextView;
    private TextView passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTextView = findViewById(R.id.LogIn_username);
        passwordTextView = findViewById(R.id.LogIn_Password);
    }

    public void login(View view) {
        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        try {
            authenticationService.authenticateUser(username, password);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (Throwable exception) {
            Toast.makeText(this, "Login fehlgeschlagen!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        usernameTextView.setText("");
        passwordTextView.setText("");
    }

}

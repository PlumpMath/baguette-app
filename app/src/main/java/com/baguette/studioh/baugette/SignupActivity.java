package com.baguette.studioh.baugette;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.unbindDrawables(findViewById(R.id.SignupActivity));
        System.gc();
    }

    public void onSignupNextStepClick(View view) {
        String oriPassword = ((EditText)findViewById(R.id.signupPassword)).getText().toString();
        String samePassword = ((EditText)findViewById(R.id.signupPasswordAgain)).getText().toString();
        String idText = ((EditText)findViewById(R.id.signupID)).getText().toString();
        String usernameText = ((EditText)findViewById(R.id.signupUsrname)).getText().toString();
        if (oriPassword.equals("") || samePassword.equals("") || idText.equals("") || usernameText.equals("")) {
            Toast.makeText(this, "Something is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (!oriPassword.equals(samePassword)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, SignupFinalizeActivity.class);
        intent.putExtra("ID", idText);
        intent.putExtra("Username", usernameText);
        intent.putExtra("Password", oriPassword);
        startActivity(intent);
    }
}

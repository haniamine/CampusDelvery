package ma.ac.emi.campusdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ma.ac.emi.campusdelivery.admin.AdminActivity;

public class LoginActivity extends AppCompatActivity {

    EditText emailId,password;
    Button btnSign;
    TextView txtRedirect;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editMail);
        password = findViewById(R.id.editPass);
        btnSign = findViewById(R.id.btnSign);
        txtRedirect = findViewById(R.id.txtRedirect);

        //// Login config
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFireUser = mFirebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mFireUser != null){
                    Toast.makeText(LoginActivity.this,"Vous êtes connecté",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Connectez-Vous",Toast.LENGTH_SHORT).show();
                }
            }
        };

        //// BTN config
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Veuillez entrer un email valide !");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Veuillez entrer un mot de passe !");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Les champs sont vides", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    if(email.equals("admin") && pwd.equals("admin") ){
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    }else{
                        mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Les champs sont incorrects", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                }
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Error Fatal Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /// Redirecting to other page
        txtRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

}

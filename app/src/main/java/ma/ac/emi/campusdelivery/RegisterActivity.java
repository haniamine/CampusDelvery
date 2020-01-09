package ma.ac.emi.campusdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText emailId,password,passwordConfirm;
    Button btnSign;
    TextView txtRedirect;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editMail);
        password = findViewById(R.id.editPass);
        passwordConfirm = findViewById(R.id.editPassConf);
        btnSign = findViewById(R.id.btnSign);
        txtRedirect = findViewById(R.id.txtRedirect);

        //// BTN config
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                String pwdConf = passwordConfirm.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Veuillez entrer un email valide !");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    emailId.setError("Veuillez entrer un mot de passe !");
                    emailId.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Les champs sont vides", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    if(pwd.equals(pwdConf)){
                        mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Les champs sont incorrects", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Les mots de passes ne sont pas identiques", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Error Fatal Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /// Redirecting to other page
        txtRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(i);
                finish();
            }
        });
    }
}

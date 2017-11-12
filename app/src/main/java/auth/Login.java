package auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.sharepdf.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import principal.Principal;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button login_registrar, login_entrar;
    EditText login_password, login_correo;
    CheckBox login_ver_password;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_login);

        login_registrar =(Button) findViewById(R.id.login_registrar);
        login_entrar = (Button) findViewById(R.id.login_entrar);
        login_correo = (EditText) findViewById(R.id.login_correo);
        login_password = (EditText)findViewById(R.id.login_password);
        login_ver_password = (CheckBox)findViewById(R.id.login_ver_password);

        login_ver_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                if(!ischeck){
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }else{
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        login_entrar.setOnClickListener(this);
        login_registrar.setOnClickListener(this);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if(user != null){
                    //SI se ha iniciado sesión
                    Log.i("Sesion","Sesion iniciaciada con email"+user.getEmail());
                    Intent principal = new Intent(Login.this, Principal.class);
                    startActivity(principal);
                    //Aqui podria ir la siguiente activity ya que si el usuario ya esta inciado la sesion se slatria esta parte
                }else{
                    Log.i("Sesion","sesión cerrada");
                }
            }
        };
    }


    private void iniciarSesion(String correo, String password){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){ //Si la operación a sido correcta osea si se ha creado el usuario
                    Log.i("Sesion","Sesion iniciada");
                }else{
                    Log.i("Sesion",task.getException().getMessage()+"");
                    Toast.makeText(Login.this, "Usuario no registrado, o otro error",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_entrar:
                String correo = login_correo.getText().toString();
                String password = login_password.getText().toString();
                iniciarSesion(correo, password);
                break;
            case R.id.login_registrar:
                Intent registro = new Intent(Login.this, Registro.class);
                startActivity(registro);
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }
}
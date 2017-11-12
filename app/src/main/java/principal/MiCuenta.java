package principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.sharepdf.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Alex on 12/11/2017.
 */

public class MiCuenta  extends AppCompatActivity implements View.OnClickListener {
    Button mi_cuenta_volver, mi_cuenta_cambio_password;
    TextView mi_cuenta_usuario, mi_cuenta_frase_del_dia, mi_cuenta_credito_disponible,
            mi_cuenta_archivos_descargados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_mi_cuenta);

        mi_cuenta_volver = (Button)findViewById(R.id.mi_cuenta_volver);
        mi_cuenta_cambio_password = (Button)findViewById(R.id.mi_cuenta_cambio_password);
        mi_cuenta_usuario = (TextView)findViewById(R.id.mi_cuenta_usuario);
        mi_cuenta_frase_del_dia = (TextView)findViewById(R.id.mi_cuenta_frase_del_dia);
        mi_cuenta_credito_disponible = (TextView)findViewById(R.id.mi_cuenta_credito_disponible);
        mi_cuenta_archivos_descargados = (TextView)findViewById(R.id.mi_cuenta_archivos_descargados);

        mi_cuenta_volver.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mi_cuenta_volver:
                FirebaseAuth.getInstance().signOut();
                Intent principal = new Intent(MiCuenta.this, Principal.class);
                startActivity(principal);
                break;
        }



    }
}

package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class AutorConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtDetalleCodigo,txtDetalleNombre,txtDetalleApellido,txtDetalleCorreo,txtDetalleTelefono,txtDetalleFechaNaci,txtDetalleGrado,txtDetallePais;
    Button btnDetalleRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_consulta_detalle);

        txtDetalleCodigo = findViewById(R.id.txtDetalleCodigo);
        txtDetalleNombre = findViewById(R.id.txtDetalleNombre);
        txtDetalleApellido = findViewById(R.id.txtDetalleApellido);
        txtDetalleCorreo = findViewById(R.id.txtDetalleCorreo);
        txtDetalleTelefono = findViewById(R.id.txtDetalleTelefono);
        txtDetalleFechaNaci = findViewById(R.id.txtDetalleFechaNaci);
        txtDetalleGrado = findViewById(R.id.txtDetalleGrado);
        txtDetallePais = findViewById(R.id.txtDetallePais);
        btnDetalleRegresar = findViewById(R.id.btnDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Autor objAutor = (Autor) extras.get("VAR_OBJETO");

        txtDetalleCodigo.setText(Integer.toString(objAutor.getIdAutor()));
        txtDetalleNombre.setText("Nombre: "+objAutor.getNombres());
        txtDetalleApellido.setText("Apellido: "+objAutor.getApellidos());
        txtDetalleCorreo.setText("Correo: "+objAutor.getCorreo());
        txtDetalleTelefono.setText("Telefono: "+objAutor.getTelefono());
        txtDetalleFechaNaci.setText("Fecha Nacimiento: "+objAutor.getFechaNacimiento());
        txtDetalleGrado.setText("Grado: "+objAutor.getGrado().getDescripcion());
        txtDetallePais.setText("Pais: "+objAutor.getPais().getNombre());

        btnDetalleRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutorConsultaDetalleActivity.this, AutorConsultaActivity.class);
                startActivity(intent);
            }
        });
    }
}
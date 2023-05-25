package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class AlumnoConsultaDetalleActivity extends NewAppCompatActivity {
    TextView txtDetalleCodigo,txtDetalleNombre,txtDetalleApellido,txtDetalleCorreo,txtDetalleTelefono,txtDetalleFechaNaci,txtDetalledni,txtDetallePais,txtDetalleDireccion;
    Button btnDetalleRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_consulta_detalle);


        txtDetalleCodigo = findViewById(R.id.txtDetalleCodigoAlu);
        txtDetalleNombre = findViewById(R.id.txtDetalleNombreAlu);
        txtDetalleApellido = findViewById(R.id.txtDetalleApellidoAlu);
        txtDetalleCorreo = findViewById(R.id.txtDetalleCorreoAlu);
        txtDetalleTelefono = findViewById(R.id.txtDetalleTelefonoAlu);
        txtDetalleFechaNaci = findViewById(R.id.txtDetalleFechaNaciAlu);
        txtDetalledni = findViewById(R.id.txtDetalleDniAlu);
        txtDetalleDireccion = findViewById(R.id.txtDetalleDireccion);
        txtDetallePais = findViewById(R.id.txtDetallePais);
        btnDetalleRegresar = findViewById(R.id.btnDetalleRegresarAlu);

        Bundle extras = getIntent().getExtras();
        Alumno obj = (Alumno) extras.get("VAR_OBJETO");

        txtDetalleCodigo.setText(Integer.toString(obj.getIdAlumno()));
        txtDetalleNombre.setText("Nombre: "+obj.getNombres());
        txtDetalleApellido.setText("Apellido: "+obj.getApellidos());
        txtDetalleCorreo.setText("Correo: "+obj.getCorreo());
        txtDetalleTelefono.setText("Telefono: "+obj.getTelefono());
        txtDetalleFechaNaci.setText("Fecha Nacimiento: "+obj.getFechaNacimiento());
        txtDetalledni.setText("DNI: "+obj.getDni());
        txtDetalleDireccion.setText("Dirección: "+obj.getDireccion());
        txtDetallePais.setText("Pais: "+obj.getPais().getNombre());

        btnDetalleRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlumnoConsultaDetalleActivity.this, AlumnoConsultaActivity.class);
                startActivity(intent);
            }
        });
    }

    }

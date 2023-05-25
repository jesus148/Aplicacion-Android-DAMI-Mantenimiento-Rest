package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.widget.TextView;



import com.cibertec.movil_modelo_proyecto_2022_2.R;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;

import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;


public class LibroConsultaDetalleActivity extends NewAppCompatActivity {

    Button btnRegresar;
    TextView txtDetalleTitulo, txtDetalleAnio, txtDetallePaisNombre,txtDetalleSerie, txtDetalleFechaRegistro, txtDetalleEstado, txtDetalleCategoriaDescrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_consulta_detalle);

        txtDetalleTitulo = findViewById(R.id.txtDetalleTitulo);
        txtDetalleAnio = findViewById(R.id.txtDetalleAnio);
        txtDetalleSerie = findViewById(R.id.txtDetalleSerie);
        txtDetalleFechaRegistro = findViewById(R.id.txtDetalleFechaRegistro);
        txtDetalleEstado = findViewById(R.id.txtLibroEstado);
        txtDetalleCategoriaDescrip = findViewById(R.id.txtDetalleCategoriaDescrip);
        txtDetallePaisNombre = findViewById(R.id.txtDetallePaisNombre);
        btnRegresar = findViewById(R.id.btnDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Libro objLibro = (Libro) extras.get("VAR_OBJETOS");

        txtDetalleTitulo.setText(objLibro.getTitulo());
        txtDetalleAnio.setText("Año : " + objLibro.getAnio());
        txtDetalleSerie.setText("Serie : "+objLibro.getSerie());
        txtDetalleFechaRegistro.setText("Fecha de Creacion : " + objLibro.getFechaRegistro());
        txtDetalleEstado.setText(String.valueOf("Estado : " + objLibro.getEstado()));
        txtDetalleCategoriaDescrip.setText("Categoría : " + objLibro.getCategoria().getDescripcion());
        txtDetallePaisNombre.setText("Pais : " + objLibro.getPais().getNombre());

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibroConsultaDetalleActivity.this, LibroConsultaActivity.class);
                startActivity(intent);
            }
        });
    }
}
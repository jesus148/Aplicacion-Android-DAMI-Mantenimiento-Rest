package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;


public class LibroCrudFormularioActivity extends NewAppCompatActivity {


    Button btnCrudRegistrar,btnCrudRegresar;
    TextView txtTitulo;
    EditText txtCrudNombre,txtAnio,txtSerie,txtFechaRegistro,txtEstado;
    Spinner spnCrudLibroCategoria,spnCrudLibroPais;

    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> lstNombresCategoria = new ArrayList<String>();

    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstNombresPais = new ArrayList<String>();

    ServicePais servicePais ;
    ServiceCategoria serviceCategoria ;
    ServiceLibro serviceLibro ;

    //El tipo define si es REGISTRA o ACTUALIZA
    String tipo;

    //Se recibe el libro seleccionado
    Libro objLibroSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_formulario);

        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnCrudRegistrar = findViewById(R.id.btnCrudRegistrar);
        txtTitulo = findViewById(R.id.idCrudTituloCliente);

        txtCrudNombre = findViewById(R.id.txtCrudNombre);
        txtAnio = findViewById(R.id.txtAnio);
        txtSerie = findViewById(R.id.txtSerie);
        txtFechaRegistro = findViewById(R.id.txtFechaRegistro);
        txtEstado = findViewById(R.id.txtEstado);

        spnCrudLibroCategoria = findViewById(R.id.spnCrudLibroCategoria);
        spnCrudLibroPais = findViewById(R.id.spnCrudLibroPais);

        adaptadorCategoria = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresCategoria);
        spnCrudLibroCategoria.setAdapter(adaptadorCategoria);

        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPais);
        spnCrudLibroPais.setAdapter(adaptadorPais);

        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        if (tipo.equals("ACTUALIZA")){
            objLibroSeleccionado = (Libro) extras.get("var_objeto");
            txtCrudNombre.setText(objLibroSeleccionado.getTitulo());
            txtAnio.setText(objLibroSeleccionado.getAnio());
            txtSerie.setText(objLibroSeleccionado.getSerie());
            txtFechaRegistro.setText(objLibroSeleccionado.getFechaRegistro());
            txtEstado.setText(objLibroSeleccionado.getEstado());
        }

        btnCrudRegistrar.setText(tipo);
        txtTitulo.setText(titulo);

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);

        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        LibroCrudFormularioActivity.this,
                        LibroCrudListaActivity.class);
                startActivity(intent);
            }
        });
    }



}
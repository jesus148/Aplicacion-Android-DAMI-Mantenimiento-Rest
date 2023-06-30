package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class EditorialConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtDetalleTitulo, txtDetalleFechaC, txtDetalleCategoria, txtDetalleRuc, txtDetallePais, txtDetalleDireccion;
    Button btnDetalleRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_consulta_detalle);
        txtDetalleTitulo = findViewById(R.id.txtDetalleTitulo);
        txtDetalleFechaC = findViewById(R.id.txtDetalleFechaC);
        txtDetalleCategoria = findViewById(R.id.txtDetalleCategoria);
        txtDetalleRuc = findViewById(R.id.txtDetalleRuc);
        txtDetallePais = findViewById(R.id.txtDetallePais);
        txtDetalleDireccion = findViewById(R.id.txtDetalleDireccion);
        btnDetalleRegresar = findViewById(R.id.btnDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Editorial objEditorial = (Editorial) extras.get("VAR_OBJETO");

        txtDetalleTitulo.setText(objEditorial.getRazonSocial());
        txtDetalleFechaC.setText("Fecha de creación: "+objEditorial.getFechaCreacion());
        txtDetalleCategoria.setText("Categoría: "+objEditorial.getCategoria().getDescripcion());
        txtDetalleRuc.setText("Ruc: "+objEditorial.getRuc());
        txtDetallePais.setText("País: "+objEditorial.getPais().getNombre());
        txtDetalleDireccion.setText("Dirección: "+objEditorial.getDireccion());

        btnDetalleRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditorialConsultaDetalleActivity.this, EditorialConsultaActivity.class);
                startActivity(intent);
            }
        });

    }
}
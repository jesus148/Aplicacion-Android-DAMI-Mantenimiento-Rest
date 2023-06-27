package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;


public class ProveedorConsultaDetalleActivity extends NewAppCompatActivity {

    TextView txtDetalleCodigo,txtDetalleRazonSocial,txtDetalleRUC,txtDetalleDireccion,txtDetalleTelefono,txtDetalleCelular,txtDetalleContacto,txtDetallePais, txtDetalleProveedor;
    Button btnDetalleRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_consulta_detalle);

        txtDetalleCodigo = findViewById(R.id.txtDetalleCodigo);
        txtDetalleRazonSocial = findViewById(R.id.txtDetalleRazonSocial);
        txtDetalleRUC = findViewById(R.id.txtDetalleRUC);
        txtDetalleDireccion = findViewById(R.id.txtDetalleDireccion);
        txtDetalleTelefono = findViewById(R.id.txtDetalleTelefono);
        txtDetalleCelular = findViewById(R.id.txtDetalleCelular);
        txtDetalleContacto = findViewById(R.id.txtDetalleContacto);
        txtDetallePais = findViewById(R.id.txtDetallePais);
        txtDetalleProveedor =findViewById(R.id.txtDetalleProveedor);
        btnDetalleRegresar = findViewById(R.id.btnDetalleRegresar);

        Bundle extras = getIntent().getExtras();
        Proveedor objProveedor = (Proveedor) extras.get("VAR_OBJETO");

        txtDetalleCodigo.setText(Integer.toString(objProveedor.getIdProveedor()));
        txtDetalleRazonSocial.setText("Razón Social: "+objProveedor.getRazonsocial());
        txtDetalleRUC.setText("RUC: "+objProveedor.getRuc());
        txtDetalleDireccion.setText("Dirección: "+objProveedor.getDireccion());
        txtDetalleTelefono.setText("Teléfono: "+objProveedor.getTelefono());
        txtDetalleCelular.setText("Celular: "+objProveedor.getCelular());
        txtDetalleContacto.setText("Contacto: "+objProveedor.getContacto());
        txtDetallePais.setText("País: "+objProveedor.getPais().getNombre());
        txtDetalleProveedor.setText("Proveedor: "+objProveedor.getTipoProveedor().getDescripcion());

        btnDetalleRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProveedorConsultaDetalleActivity.this, ProveedorConsultaActivity.class);
                startActivity(intent);
            }
        });
    }
}
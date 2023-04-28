package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.TipoProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceTipoProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorRegistraActivity extends NewAppCompatActivity {

    Spinner spnPaisProveedor, spnTipoProveedorProveedor;
    Button btnRegistrarProveedor;
    EditText txtRazonSocialProveedor, txtRUCProveedor, txtDireccionProveedor, txtTelefonoProveedor, txtCelularProveedor, txtContactoProveedor;
    ServiceProveedor servProveedor;

    //Adaptadores
    ArrayAdapter<Pais> paisArrayAdapter;
    ArrayAdapter<TipoProveedor> tipoProveedorAdapter;
    List<Pais> paisList = new ArrayList<>();
    List<TipoProveedor> tipoProveedorList = new ArrayList<>();

    ServicePais servicePais;

    ServiceTipoProveedor serviceTipoProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_registra);

        txtRazonSocialProveedor = findViewById(R.id.txtRazonSocialProveedor);
        txtRUCProveedor = findViewById(R.id.txtRUCProveedor);
        txtDireccionProveedor = findViewById(R.id.txtDireccionProveedor);
        txtTelefonoProveedor = findViewById(R.id.txtTelefonoProveedor);
        txtCelularProveedor = findViewById(R.id.txtCelularProveedor);
        txtContactoProveedor = findViewById(R.id.txtContactoProveedor);
        spnPaisProveedor = findViewById(R.id.spnPaisProveedor);
        spnTipoProveedorProveedor = findViewById(R.id.spnTipoProveedorProveedor);
        btnRegistrarProveedor = findViewById(R.id.btnRegistrarProveedor);

        paisArrayAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paisList);
        tipoProveedorAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipoProveedorList);

        spnPaisProveedor.setAdapter(paisArrayAdapter);
        spnTipoProveedorProveedor.setAdapter(tipoProveedorAdapter);

        servProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceTipoProveedor = ConnectionRest.getConnection().create(ServiceTipoProveedor.class);

        Call<List<Pais>> callPais = servicePais.listaPais();
        Call<List<TipoProveedor>> callTipoProveedor = serviceTipoProveedor.listaTodos();
        callPais.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        for (Pais pais : response.body()) {
                            paisList.add(pais);
                        }
                        paisArrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {

            }
        });

        callTipoProveedor.enqueue(new Callback<List<TipoProveedor>>() {
            @Override
            public void onResponse(Call<List<TipoProveedor>> call, Response<List<TipoProveedor>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        for (TipoProveedor tipoProveedor : response.body()) {
                            tipoProveedorList.add(tipoProveedor);
                        }
                        tipoProveedorAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TipoProveedor>> call, Throwable t) {

            }
        });
        btnRegistrarProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String razonSocialProveedor = txtRazonSocialProveedor.getText().toString();
                String rucProveedor = txtRUCProveedor.getText().toString();
                String direccionProveedor = txtDireccionProveedor.getText().toString();
                String telefonoProveedor = txtTelefonoProveedor.getText().toString();
                String celularProveedor = txtCelularProveedor.getText().toString();
                String contactoProveedor = txtContactoProveedor.getText().toString();
                Pais paisProveedor = (Pais) spnPaisProveedor.getSelectedItem();
                TipoProveedor tipoProveedorProveedor = (TipoProveedor) spnTipoProveedorProveedor.getSelectedItem();

                if (!razonSocialProveedor.matches(ValidacionUtil.TEXTO)) {
                    mensajeAlert("La razon social es de 2 a 20 caracteres.");
                } else if (!rucProveedor.matches(ValidacionUtil.RUC)) {
                    mensajeAlert("El RUC debe contener 11 dígitos.");
                } else if (!direccionProveedor.matches(ValidacionUtil.DIRECCION)) {
                    mensajeAlert("La dirección debe contener de 3 a 30 caracteres.");
                } else if (!telefonoProveedor.matches(ValidacionUtil.CELULAR)) {
                    mensajeAlert("El telefono debe contener 9 dígitos.");
                } else if (!celularProveedor.matches(ValidacionUtil.CELULAR)) {
                    mensajeAlert("El celular debe contener 9 dígitos.");
                } else if (!contactoProveedor.matches(ValidacionUtil.NOMBRE)) {
                    mensajeAlert("El contacto debe contener de 3 a 30 caracteres.");
                } else if (spnPaisProveedor.getSelectedItemPosition() == 1) {
                    mensajeAlert("Seleccione un país.");
                } else if (spnTipoProveedorProveedor.getSelectedItemPosition() == 1) {
                    mensajeAlert("Seleccione un tipo de proveedor.");
                } else {

                    Proveedor obj = new Proveedor();
                    obj.setRazonsocial(razonSocialProveedor);
                    obj.setRuc(rucProveedor);
                    obj.setDireccion(direccionProveedor);
                    obj.setTelefono(telefonoProveedor);
                    obj.setCelular(celularProveedor);
                    obj.setContacto(contactoProveedor);
                    obj.setEstado(1);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setPais(paisProveedor);
                    obj.setTipoProveedor(tipoProveedorProveedor);

                    registraProveedor(obj);

                }

            }
        });

    }

    public void registraProveedor(Proveedor obj) {
        Call<Proveedor> call = servProveedor.insertaProveedor(obj);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()) {
                    Proveedor objSalida = response.body();
                    mensajeAlert("Registro exitoso " + objSalida.getIdProveedor());
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeAlert("Error " + t.getMessage());
            }
        });
    }

    public void mensajeAlert(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}
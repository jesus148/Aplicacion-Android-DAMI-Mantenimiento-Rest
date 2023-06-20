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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.TipoProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceTipoProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegresar, btnCrudRegistra;
    TextView txtTitulo;

    EditText txtRazonSocial, txtRUC, txtDireccion, txtTelefono, txtCelular, txtContacto;
    Spinner spnPais, spnTipoProveedor;

    ServiceProveedor serviceProveedor;

    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstPais = new ArrayList<String>();
    ServicePais servicePais;

    ArrayAdapter<String> adaptadorTipoProveedor;
    ArrayList<String> lstTipoProveedor = new ArrayList<String>();
    ServiceTipoProveedor serviceTipoProveedor;

    String tipo;

    Proveedor objProveedorSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_formulario);

        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceTipoProveedor = ConnectionRest.getConnection().create(ServiceTipoProveedor.class);

        btnCrudRegistra = findViewById(R.id.btnRegistrarProveedor);
        txtTitulo = findViewById(R.id.idCrudTituloProveedor);

        txtRazonSocial = findViewById(R.id.txtRazonSocialProveedor);
        txtRUC = findViewById(R.id.txtRUCProveedor);
        txtDireccion = findViewById(R.id.txtDireccionProveedor);
        txtTelefono = findViewById(R.id.txtTelefonoProveedor);
        txtCelular = findViewById(R.id.txtCelularProveedor);
        txtContacto = findViewById(R.id.txtContactoProveedor);
        spnPais = findViewById(R.id.spnPaisProveedor);
        spnTipoProveedor = findViewById(R.id.spnTipoProveedorProveedor);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorTipoProveedor = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstTipoProveedor);
        spnTipoProveedor.setAdapter(adaptadorTipoProveedor);

        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        cargaPais();
        cargaTipoProveedor();

        if (tipo.equals("ACTUALIZA")) {
            objProveedorSeleccionado = (Proveedor) extras.get("var_objeto");
            txtRazonSocial.setText(objProveedorSeleccionado.getRazonsocial());
            txtRUC.setText(objProveedorSeleccionado.getRuc());
            txtDireccion.setText(objProveedorSeleccionado.getDireccion());
            txtTelefono.setText(objProveedorSeleccionado.getTelefono());
            txtCelular.setText(objProveedorSeleccionado.getCelular());
            txtContacto.setText(objProveedorSeleccionado.getContacto());
        }

        btnCrudRegistra.setText(tipo);
        txtTitulo.setText(titulo);

        btnCrudRegresar = findViewById(R.id.btnRegresarProveedor);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProveedorCrudFormularioActivity.this, ProveedorCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String razonSocial = txtRazonSocial.getText().toString();
                String ruc = txtRUC.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String celular = txtCelular.getText().toString();
                String contacto = txtContacto.getText().toString();
                String pais = spnPais.getSelectedItem().toString();
                String tipoProveedor = spnTipoProveedor.getSelectedItem().toString();

                Pais objNewPais = new Pais();
                objNewPais.setIdPais(Integer.parseInt(pais.split(":")[0]));

                TipoProveedor objNewTipoProveedor = new TipoProveedor();
                objNewTipoProveedor.setIdTipoProveedor(Integer.parseInt(tipoProveedor.split(":")[0]));

                Proveedor objNewProveedor = new Proveedor();
                objNewProveedor.setRazonsocial(razonSocial);
                objNewProveedor.setRuc(ruc);
                objNewProveedor.setDireccion(direccion);
                objNewProveedor.setTelefono(telefono);
                objNewProveedor.setCelular(celular);
                objNewProveedor.setContacto(contacto);
                objNewProveedor.setPais(objNewPais);
                objNewProveedor.setTipoProveedor(objNewTipoProveedor);
                objNewProveedor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objNewProveedor.setEstado(1);

                if (tipo.equals("REGISTRAR")) {
                    insertaProveedor(objNewProveedor);
                } else if (tipo.equals("ACTUALIZAR")) {
                    Proveedor objAux = (Proveedor) extras.get("var_objeto");
                    objNewProveedor.setIdProveedor(objAux.getIdProveedor());
                    actualizaProveedor(objNewProveedor);
                }
            }
        });
    }

    public void insertaProveedor(Proveedor objProveedor) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objProveedor);
        // mensajeAlert(json);
        Call<Proveedor> call = serviceProveedor.insertaProveedor(objProveedor);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()) {
                    Proveedor objSalida = response.body();
                    String msg = "Se registró el Proveedor con exito\n";
                    msg += "ID : " + objSalida.getIdProveedor() + "\n";
                    msg += "Razon Social : " + objSalida.getRazonsocial();
                    mensajeAlert(msg);
                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void actualizaProveedor(Proveedor objProveedor) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objProveedor);
        // mensajeAlert(json);
        Call<Proveedor> call = serviceProveedor.actualizaProveedor(objProveedor);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()) {
                    Proveedor objSalida = response.body();
                    String msg = "Se actualizó el Proveedor con exito\n";
                    msg += "ID : " + objSalida.getIdProveedor() + "\n";
                    msg += "Razon Social : " + objSalida.getRazonsocial();
                    mensajeAlert(msg);
                } else {
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lst = response.body();
                    for(Pais obj:lst){
                        lstPais.add(obj.getIdPais()+":"+obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Pais objPais = objProveedorSeleccionado.getPais();
                        String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos = -1;
                        for(int i=0; i< lstPais.size(); i++){
                            if (lstPais.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnPais.setSelection(pos);
                    }

                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaTipoProveedor(){
        Call<List<TipoProveedor>> call = serviceTipoProveedor.listaTodos();
        call.enqueue(new Callback<List<TipoProveedor>>() {
            @Override
            public void onResponse(Call<List<TipoProveedor>> call, Response<List<TipoProveedor>> response) {
                if(response.isSuccessful()){
                    List<TipoProveedor> lst = response.body();
                    for(TipoProveedor obj:lst){
                        lstTipoProveedor.add(obj.getIdTipoProveedor()+":"+obj.getDescripcion());
                    }
                    adaptadorTipoProveedor.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        TipoProveedor objTipoProveedor = objProveedorSeleccionado.getTipoProveedor();
                        String aux2 = objTipoProveedor.getIdTipoProveedor()+":"+objTipoProveedor.getDescripcion();
                        int pos = -1;
                        for(int i=0; i< lstTipoProveedor.size(); i++){
                            if (lstTipoProveedor.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnTipoProveedor.setSelection(pos);
                    }

                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<TipoProveedor>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
}
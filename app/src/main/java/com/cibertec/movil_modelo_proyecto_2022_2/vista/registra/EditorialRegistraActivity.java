package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialRegistraActivity extends NewAppCompatActivity {

    EditText txtNombre, txtDireccion;
    Spinner cboPais, cboCategoria;
    ArrayAdapter<String> adaptadorP;
    ArrayList<String> lstPaises = new ArrayList<String>();
    List<Pais> lstp;
    ArrayAdapter<String> adaptadorC;
    ArrayList<String> lstCategorias = new ArrayList<String>();
    List<Categoria> lstc;
    Button btnRegistEdit;
    ServicePais paisservice;
    ServiceCategoria categoriaservice;
    ServiceEditorial editorialservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_registra);

        adaptadorP = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.
                support_simple_spinner_dropdown_item, lstPaises);
        cboPais = findViewById(R.id.cboPais);
        cboPais.setAdapter(adaptadorP);

        adaptadorC = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.
                support_simple_spinner_dropdown_item, lstCategorias);
        cboCategoria = findViewById(R.id.cboCategoria);
        cboCategoria.setAdapter(adaptadorC);

        txtNombre = findViewById(R.id.txtNombre);
        txtDireccion = findViewById(R.id.txtDireccion);
        cboPais = findViewById(R.id.cboPais);
        cboCategoria = findViewById(R.id.cboCategoria);
        btnRegistEdit = findViewById(R.id.btnRegistEdit);

        paisservice = ConnectionRest.getConnection().create(ServicePais.class);

        listaPaises();

        categoriaservice = ConnectionRest.getConnection().create(ServiceCategoria.class);

        listaCategoria();

        editorialservice = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnRegistEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String dir = txtDireccion.getText().toString();
                String pais = cboPais.getSelectedItem().toString();
                String idPais = pais.split(":")[0];
                String cat = cboCategoria.getSelectedItem().toString();
                String idCat = cat.split(":")[0];

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));

                Categoria objCat = new Categoria();
                objCat.setIdCategoria(Integer.parseInt(idPais));

                Editorial obj = new Editorial();
                obj.setRazonSocial(nom);
                obj.setDireccion(dir);
                obj.setCategoria(objCat);
                obj.setPais(objPais);
                obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                obj.setFechaCreacion(FunctionUtil.getFechaActualStringDateTime());
                obj.setEstado(1);

                registraEditorial(obj);
            }
        });

    }

    public void listaPaises(){
        Call<List<Pais>> call = paisservice.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    lstp = response.body();
                    for(Pais obj: lstp){
                        lstPaises.add(obj.getNombre());
                    }
                    adaptadorP.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
            }
        });
    }
    public void listaCategoria(){
        Call<List<Categoria>> call = categoriaservice.listaCategoriaDeEditorial();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    lstc = response.body();
                    for(Categoria obj: lstc){
                        lstCategorias.add(obj.getDescripcion());
                    }
                    adaptadorC.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });
    }
    public void registraEditorial(Editorial obj){
        Call<Editorial> call =  editorialservice.insertaEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if(response.isSuccessful()){
                    Editorial objSalida = response.body();
                    mensajeAlert("Registro exitoso" + objSalida.getIdEditorial());
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("Error" + t.getMessage());
            }
        });
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}
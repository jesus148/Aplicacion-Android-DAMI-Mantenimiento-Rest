package com.cibertec.movil_modelo_proyecto_2022_2;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends NewAppCompatActivity {

    EditText txtNombre, txtDireccion;
    Spinner cboPais, cboCategoria;
    ArrayAdapter<String> adaptadorP;
    ArrayList<String> lstPaises = new ArrayList<String>();
    List<Pais> lstp;
    ArrayAdapter<String> adaptadorC;
    ArrayList<String> lstCategorias = new ArrayList<String>();
    Button btnRegistEdit;
    ServicePais paisservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_registra);

        adaptadorP = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.
                support_simple_spinner_dropdown_item, lstPaises);
        cboPais = findViewById(R.id.cboPais);
        cboPais.setAdapter(adaptadorP);

        txtNombre = findViewById(R.id.txtNombre);
        txtDireccion = findViewById(R.id.txtDireccion);
        cboPais = findViewById(R.id.cboPais);
        cboCategoria = findViewById(R.id.cboCategoria);
        btnRegistEdit = findViewById(R.id.btnRegistEdit);

        paisservice = ConnectionRest.getConnection().create(ServicePais.class);
       /* btnRegistEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String dir = txtDireccion.getText().toString();
                String pais = cboPais.getSelectedItem().toString();
                String cat = cboCategoria.getSelectedItem().toString();

                Editorial obj = new Editorial();
                obj.setRazonSocial(nom);
                obj.setDireccion(dir);

            }
        });*/
        listaPaises();
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
    /*public void registraEditorial(Editorial obj){
        Call<Editorial> call =  service.insertaEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {

            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {

            }
        });
    }*/



}
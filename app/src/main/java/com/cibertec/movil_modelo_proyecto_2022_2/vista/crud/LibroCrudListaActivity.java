package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.LibroCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListar,btnCrudRegistra;
    GridView gridCrudLibro;

    ArrayList<Libro> data = new ArrayList<Libro>();
    LibroCrudAdapter adaptador;
    ServiceLibro serviceLibro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_lista);

        btnCrudListar= findViewById(R.id.btnCrudListar);
        btnCrudRegistra= findViewById(R.id.btnCrudRegistra);
        gridCrudLibro= findViewById(R.id.gridCrudLibro);
        adaptador = new LibroCrudAdapter(this,R.layout.activity_libro_crud_item,data);
        gridCrudLibro.setAdapter(adaptador);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        LibroCrudListaActivity.this,
                        LibroCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "REGISTRA LIBRO");
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);
            }
        });

        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });
        gridCrudLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Libro objLibro = data.get(position);

                Intent intent = new Intent(
                        LibroCrudListaActivity.this,
                        LibroCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "ACTUALIZA LIBRO");
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", objLibro);
                startActivity(intent);

            }
        });
        lista();
    }
    public void lista(){
        Call<List<Libro>> call = serviceLibro.listaLibro();
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                List<Libro> lista =response.body();
                data.clear();
                data.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {

            }
        });
    }

}
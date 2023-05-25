package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.LibroAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroConsultaActivity extends AppCompatActivity {

    //Boton
    Button btnListar;
    //ListViw
    GridView gridLibro;
    ArrayList<Libro> data = new ArrayList<Libro>();
    LibroAdapter adaptador;

    //service
    ServiceLibro serviceLibro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_consulta);

        btnListar = findViewById(R.id.btnLista);

        gridLibro = findViewById(R.id.gridProductos);
        adaptador = new LibroAdapter(this,R.layout.activity_libro_item,data);
        gridLibro.setAdapter(adaptador);

        gridLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Libro objLibro  =  data.get(position);

                Intent intent = new Intent(LibroConsultaActivity.this, LibroConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETOS", objLibro);
                startActivity(intent);

            }
        });

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaLibro();
            }
        });
    }
    public void listaLibro(){
        Call<List<Libro>> call = serviceLibro.listaLibro();
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful()){
                    List<Libro> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {

            }
        });
    }
}
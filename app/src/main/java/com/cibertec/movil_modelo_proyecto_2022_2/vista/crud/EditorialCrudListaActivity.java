package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListar, btnCrudRegistra;

    //GridView
    GridView gridCrudEditorial;
    ArrayList<Editorial> data = new ArrayList<Editorial>();
    EditorialCrudAdapter adaptador;

    //service
    ServiceEditorial serviceEditorial;

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_lista);

        btnCrudListar = findViewById(R.id.btnCrudListar);
        btnCrudRegistra = findViewById(R.id.btnCrudRegistra);
        gridCrudEditorial = findViewById(R.id.gridCrudEditorial);
        adaptador = new EditorialCrudAdapter(this,R.layout.activity_editorial_crud_item,data);
        gridCrudEditorial.setAdapter(adaptador);

        serviceEditorial =  ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "REGISTRA EDITORIAL");
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

        gridCrudEditorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Editorial objCliente = data.get(position);

                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "ACTUALIZA Editorial");
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", objCliente);
                startActivity(intent);

            }
        });


    }

    public void lista(){
        Call<List<Editorial>> call = serviceEditorial.listaEditorial();
        call.enqueue(new Callback<List<Editorial>>() {
            @Override
            public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                List<Editorial> lista =response.body();
                data.clear();
                data.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Editorial>> call, Throwable t) {

            }
        });
    }

}
package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AutorAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.ProveedorAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorConsultaActivity extends NewAppCompatActivity {

    Button btnListaProveedor;
    GridView gridProveedor;
    ArrayList<Proveedor> data = new ArrayList<Proveedor>();
    ProveedorAdapter adaptador;
    ServiceProveedor serviceProveedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_consulta);

        btnListaProveedor = findViewById(R.id.btnListaProveedor);
        gridProveedor = findViewById(R.id.gridProveedor);
        adaptador = new ProveedorAdapter(this, R.layout.activity_proveedor_item, data);
        gridProveedor.setAdapter(adaptador);

        gridProveedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Proveedor objProveedor = data.get(position);
                Intent intent = new Intent(ProveedorConsultaActivity.this, ProveedorConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETO",objProveedor);
                startActivity(intent);
            }
        });

        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);

        btnListaProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaProveedor();
            }
        } );
    }

    public void listaProveedor(){
        Call<List<Proveedor>> call = serviceProveedor.listaProveedor();
        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {
                if (response.isSuccessful()){
                    List<Proveedor> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {

            }
        });


    }


 }
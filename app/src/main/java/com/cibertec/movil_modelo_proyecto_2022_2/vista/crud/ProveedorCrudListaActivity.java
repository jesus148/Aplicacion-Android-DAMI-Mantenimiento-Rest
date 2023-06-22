package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.ProveedorCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorCrudListaActivity extends NewAppCompatActivity {

    Button btnCrudListar, btnCrudRegistra;

    GridView gridCrudProveedor;
    ArrayList<Proveedor> data = new ArrayList<Proveedor>();
    ProveedorCrudAdapter adaptador;

    ServiceProveedor serviceProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_lista);

        btnCrudListar = findViewById(R.id.btnCrudListar);
        btnCrudRegistra = findViewById(R.id.btnCrudRegistra);
        gridCrudProveedor = findViewById(R.id.gridCrudProveedor);
        adaptador = new ProveedorCrudAdapter(this, R.layout.activity_proveedor_crud_item, data);
        gridCrudProveedor.setAdapter(adaptador);

        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ProveedorCrudListaActivity.this,
                        ProveedorCrudFormularioActivity.class);

                intent.putExtra("var_titulo", "REGISTRA PROVEEDOR");
                intent.putExtra("var_tipo", ProveedorCrudFormularioActivity.TIPO_REGISTRAR);
                startActivity(intent);
            }
        });

        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });

        gridCrudProveedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Proveedor objProveedor = data.get(position);

                Intent intent = new Intent(
                        ProveedorCrudListaActivity.this,
                        ProveedorCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "ACTUALIZAR PROVEEDOR");
                intent.putExtra("var_tipo", ProveedorCrudFormularioActivity.TIPO_ACTUALIZAR);
                intent.putExtra("var_objeto", objProveedor);
                startActivity(intent);

            }
        });

        lista();
    }

    public void lista() {
        Call<List<Proveedor>> call = serviceProveedor.listaProveedor();
        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {
                List<Proveedor> lista = response.body();
                data.clear();
                data.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {

            }
        });
    }
}
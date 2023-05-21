package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.cibertec.movil_modelo_proyecto_2022_2.MainActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorialConsultaActivity extends NewAppCompatActivity {

    Button btnListar;
    GridView gridEditorial;
    ArrayList<Editorial> data = new ArrayList<Editorial>();
    EditorialAdapter adaptador;
    ServiceEditorial serviceEditorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editorial_consulta);

        btnListar = findViewById(R.id.btnListar);
        gridEditorial = findViewById(R.id.gridEditorial);
        adaptador = new EditorialAdapter(this, R.layout.activity_editorial_item, data);
        gridEditorial.setAdapter(adaptador);
        gridEditorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Editorial objEditorial = data.get(position);
                Intent intent = new Intent( EditorialConsultaActivity.this, EditorialConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETO", objEditorial);
                startActivity(intent);
            }
        });
        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaEditorial();
            }
        } );
    }

    public void listaEditorial(){
        Call<List<Editorial>> call = serviceEditorial.listaEditorial();
        call.enqueue(new Callback<List<Editorial>>() {
            @Override
            public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                if (response.isSuccessful()){
                    List<Editorial> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Editorial>> call, Throwable t) {

            }
        });

    }

}

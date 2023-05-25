package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.AlumnoAdapter;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;

import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;

import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoConsultaActivity extends NewAppCompatActivity {
    Button btnListaAlumno;
    GridView gridAlumno;
    ArrayList<Alumno> data = new ArrayList<Alumno>();

    AlumnoAdapter adaptador;
    ServiceAlumno serviceAlumno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumno_consulta);

        btnListaAlumno = findViewById(R.id.btnListaAlumno);
        gridAlumno = findViewById(R.id.gridAlumno);
        adaptador = new AlumnoAdapter(this, R.layout.activity_autor_item, data);
        gridAlumno.setAdapter(adaptador);
        gridAlumno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alumno obj = data.get(position);
                Intent intent = new Intent(AlumnoConsultaActivity.this, AlumnoConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETO",obj);
                startActivity(intent);
            }
        });

        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);
        btnListaAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaAlumno();
            }
        } );

    }
    public void listaAlumno(){
        Call<List<Alumno>> call = serviceAlumno.listaAlumno();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if (response.isSuccessful()){
                    List<Alumno> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });
    }

}
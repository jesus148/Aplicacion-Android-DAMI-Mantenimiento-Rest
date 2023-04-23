package com.cibertec.movil_modelo_proyecto_2022_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceLibro;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends NewAppCompatActivity {
    Spinner spnCat, spnPais;
    Button btnReg;
    EditText txtTit, txtAn, txtSer, txtFech, txtEstado;

    ServiceLibro servLibro;
    ServiceCategoria servCat;
    ServicePais servPais;

    ArrayAdapter<String> adaptador;
    ArrayList<String> categoria = new ArrayList<String>();
    ArrayList<String> pais = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_registra);

        servLibro = ConnectionRest.getConnection().create(ServiceLibro.class);
        servCat = ConnectionRest.getConnection().create(ServiceCategoria.class);
        servPais = ConnectionRest.getConnection().create(ServicePais.class);

        //adaptador
        adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categoria);
        spnCat = findViewById(R.id.spnCat);
        spnCat.setAdapter(adaptador);



        adaptador = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pais);
        spnPais = findViewById(R.id.spnPais);
        spnPais.setAdapter(adaptador);

        cargaCategoria();
        cargaPais();

        txtTit = findViewById(R.id.txtTitulo);
        txtAn = findViewById(R.id.txtAnio);
        txtSer = findViewById(R.id.txtSerie);
        txtFech = findViewById(R.id.txtFechRegistro);
        txtEstado = findViewById(R.id.txtEstado);
        btnReg = findViewById(R.id.btnRegistrar);

        Locale.setDefault( new Locale("es_ES"));
        txtFech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, month);
                                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                                txtFech.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titulo = txtTit.getText().toString();
                String an = txtAn.getText().toString();
                String ser = txtSer.getText().toString();
                String fecCre = txtFech.getText().toString();
                String est = txtFech.getText().toString();


                String pais = spnPais.getSelectedItem().toString();
                String idPais = pais.split(":")[0];
                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));

                Libro objLibro = new Libro();
                objLibro.setTitulo(titulo);
                objLibro.setAnio(Integer.parseInt(an));
                objLibro.setSerie(ser);
                objLibro.setFechaRegistro(fecCre);
                objLibro.setEstado(Integer.parseInt(est));
                objLibro.setPais(objPais);
//                objLibro.setCategoria(objCat);
                insertaLibro(objLibro);

            }
        });

    }
    public  void insertaLibro(Libro objLibro){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objLibro);

        mensajeAlert(json);

        Call<Libro> call = servLibro.insertaEditorial(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful()){
                    Libro objSalida = response.body();

                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaCategoria(){
        Call<List<Categoria>> call = servCat.listaCategoriaDeLibro();
        call.enqueue(new Callback<List<Categoria>>() {

            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<Categoria> lstCate =  response.body();
                    for(Categoria obj: lstCate){
                        categoria.add(obj.getIdCategoria() +":"+ obj.getDescripcion());
                    }
                    adaptador.notifyDataSetChanged();
                }else{
                    mensajeToast("Categoria Error al cargar >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeToast("Categoria - Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaPais(){
        Call<List<Pais>> call = servPais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {

            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstPais =  response.body();
                    for(Pais obj: lstPais){
                        pais.add(obj.getIdPais() +":"+ obj.getNombre());
                    }
                    adaptador.notifyDataSetChanged();
                }else{
                    mensajeToast("Pais Error al cargar >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Pais - Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}
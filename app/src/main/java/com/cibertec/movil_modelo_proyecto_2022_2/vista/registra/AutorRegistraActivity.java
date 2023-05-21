package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceGrado;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorRegistraActivity extends NewAppCompatActivity {

    Spinner spnPais;
    Spinner spnGrado;
    ServicePais servicePais;
    ServiceGrado serviceGrado;
    ArrayAdapter<String> adapPais;
    ArrayAdapter<String> adapGrado;
    ArrayList<String> paises = new ArrayList<String>();
    ArrayList<String> grados = new ArrayList<String>();
    ServiceAutor serviceAutor;
    Button btnRegistrarAut;
    EditText txtnombre,txtapellido,txtcorreo,txtFechaNacimiento,texTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autor_registra);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceGrado = ConnectionRest.getConnection().create(ServiceGrado.class);
        serviceAutor =  ConnectionRest.getConnection().create(ServiceAutor.class);
        cargaPais();
        cargaGrado();
        //Para el adapatador
        adapPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnPais);
        spnPais.setAdapter(adapPais);

        adapGrado= new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, grados);
        spnGrado = findViewById(R.id.spnGrado);
        spnGrado.setAdapter(adapGrado);

        txtnombre = findViewById(R.id.txtNombre);
        txtapellido = findViewById(R.id.txtApellido);
        txtcorreo = findViewById(R.id.txtCorreo);
        txtFechaNacimiento = findViewById(R.id.txtFecha);
        texTelefono = findViewById(R.id.txtTelefono);
        btnRegistrarAut = findViewById(R.id.btnRegistrarAutor);

        Locale.setDefault( new Locale("es_ES"));

        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(
                        AutorRegistraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {

                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH,month);
                                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                                txtFechaNacimiento.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnRegistrarAut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //datos q recibe de string
                String nom = txtnombre.getText().toString();
                String ape = txtapellido.getText().toString();
                String corr = txtcorreo.getText().toString();
                String fechNa = txtFechaNacimiento.getText().toString();
                String tele = texTelefono.getText().toString();

                if(!nom.matches(ValidacionUtil.NOMBRE)){
                   txtnombre.setError("El nombre es de 3 a 30 caracteres");
                }
                else if(!ape.matches(ValidacionUtil.NOMBRE)){
                   txtapellido.setError("El apellido es de 3 a 30 caracteres");
                }
                else if(!corr.matches(ValidacionUtil.CORREO)){
                    txtcorreo.setError("Correo inválido");
                }
                else if(!tele.matches(ValidacionUtil.CELULAR)){
                    texTelefono.setError("El teléfono debe ser de 9 caracteres");
                }else if (!fechNa.matches(ValidacionUtil.FECHA)){
                    txtFechaNacimiento.setError("La fecha de nacimiento es YYYY-MM-dd");
                }else{

                    String pais = spnPais.getSelectedItem().toString();
                    String idPais = pais.split(":")[0];
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    String grado= spnGrado.getSelectedItem().toString();
                    String idGrado = grado.split(":")[0];
                    Grado objGrado = new Grado();
                    objGrado.setIdGrado(Integer.parseInt(idGrado));

                    Autor objAutor = new Autor();
                    objAutor.setNombres(nom);
                    objAutor.setApellidos(ape);
                    objAutor.setCorreo(corr);
                    objAutor.setFechaNacimiento(fechNa);
                    objAutor.setTelefono(tele);
                    objAutor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objAutor.setEstado(1);
                    objAutor.setGrado(objGrado);
                    objAutor.setPais(objPais);

                    insertarAutor(objAutor);
                }
            }
        });
    }
    public void insertarAutor(Autor objAutor){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAutor);

        //mensajeAlert(json);
        Call<Autor> call = serviceAutor.insertarAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if(response.isSuccessful()){
                    Autor objRegistrado= response.body();
                    String msg ="Se registro el Autor \n";
                    msg += "ID: " + objRegistrado.getIdAutor()+"\n";
                    msg += "Nombre del Autor: "+ objRegistrado.getNombres();
                    mensajeAlert(msg);

                }else{
                    mensajeToast("Error al acceder al servicio REST >>>" + response.message());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeToast("Error al acceder al servicio REST >>>" +t.getMessage());
            }
        });
    }

    public void cargaGrado() {
        Call<List<Grado>> call = serviceGrado.Todos();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if (response.isSuccessful()) {
                    List<Grado> lstGrados = response.body();
                    for (Grado obj : lstGrados) {
                        grados.add(obj.getIdGrado() + ":" + obj.getDescripcion());
                    }
                    adapGrado.notifyDataSetChanged();
                } else {
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });


    }


    public void cargaPais() {
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()) {
                    List<Pais> lstPaises = response.body();
                    for (Pais obj : lstPaises) {
                        paises.add(obj.getIdPais() + ":" + obj.getNombre());
                    }
                    adapPais.notifyDataSetChanged();
                } else {
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
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
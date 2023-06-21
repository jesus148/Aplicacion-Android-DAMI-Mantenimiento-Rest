package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
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


public class LibroCrudFormularioActivity extends NewAppCompatActivity {


    Button btnCrudRegistrar,btnCrudRegresar;
    TextView txtTitulo;
    EditText txtCrudNombre,txtAnio,txtSerie,txtFechaRegistro,txtEstado;
    Spinner spnCrudLibroCategoria,spnCrudLibroPais;

    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> lstNombresCategoria = new ArrayList<>();
    ServiceCategoria serviceCategoria ;

    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstNombresPais = new ArrayList<>();
    ServicePais servicePais ;

    ServiceLibro serviceLibro ;

    //El tipo define si es REGISTRA o ACTUALIZA
    String tipo;

    //Se recibe el libro seleccionado
    Libro objLibroSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_formulario);

        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnCrudRegistrar = findViewById(R.id.btnCrudRegistrar);
        txtTitulo = findViewById(R.id.idCrudTituloCliente);

        txtCrudNombre = findViewById(R.id.txtCrudNombre);
        txtAnio = findViewById(R.id.txtAnio);
        txtSerie = findViewById(R.id.txtSerie);
        txtFechaRegistro = findViewById(R.id.txtFechaRegistro);
        txtEstado = findViewById(R.id.txtEstado);

        adaptadorCategoria = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresCategoria);
        spnCrudLibroCategoria = findViewById(R.id.spnCrudLibroCategoria);
        spnCrudLibroCategoria.setAdapter(adaptadorCategoria);

        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPais);
        spnCrudLibroPais = findViewById(R.id.spnCrudLibroPais);
        spnCrudLibroPais.setAdapter(adaptadorPais);

        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        cargaCategoria();
        cargaPaises();

        btnCrudRegistrar.setText(tipo);
        txtTitulo.setText(titulo);

        if (tipo.equals("ACTUALIZA")){
            objLibroSeleccionado = (Libro) extras.get("var_objeto");

            txtCrudNombre.setText(objLibroSeleccionado.getTitulo());
            txtAnio.setText(objLibroSeleccionado.getAnio());
            txtSerie.setText(objLibroSeleccionado.getSerie());
            txtFechaRegistro.setText(objLibroSeleccionado.getFechaRegistro());
            txtEstado.setText(objLibroSeleccionado.getEstado());
        }
        Locale.setDefault( new Locale("es_ES"));

        txtFechaRegistro.setOnClickListener(new View.OnClickListener() {


            //explciacion cada vez que que demos click en el input creara un calendario
            @Override

            public void onClick(View view) {

                Calendar myCalendar= Calendar.getInstance();

                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(

                        //RevistaRegistraActivity : esto poner el mainactivity donde te encuentra ose el nombre del archivo en el que estamos

                        LibroCrudFormularioActivity.this,

                        new DatePickerDialog.OnDateSetListener() {

                            @Override

                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {



                                myCalendar.set(Calendar.YEAR, year);

                                myCalendar.set(Calendar.MONTH,month);

                                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                                //poner la variable donde esta inout fecha txtFecha

                                txtFechaRegistro.setText(dateFormat.format(myCalendar.getTime()));

                            }

                        },

                        myCalendar.get(Calendar.YEAR),

                        myCalendar.get(Calendar.MONTH),

                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }

        });

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);

        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        LibroCrudFormularioActivity.this,
                        LibroCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titul = txtCrudNombre.getText().toString();
                String an = txtAnio.getText().toString();
                String ser = txtSerie.getText().toString();
                String fecCre = txtFechaRegistro.getText().toString();
                String est = txtEstado.getText().toString();

                if(!titul.matches(ValidacionUtil.TEXTO)) {

                    txtCrudNombre.setError("La revista  es de 2 a 20 caracteres");


                }else if (!ser.matches(ValidacionUtil.TEXTO)) {
                    txtSerie.setError("La fecha de creación es YYYY-MM-dd");
                }else if (!fecCre.matches(ValidacionUtil.FECHA)) {
                    txtFechaRegistro.setError("La fecha de creación es YYYY-MM-dd");
                }
                else{


                    String cat = (String) spnCrudLibroCategoria.getSelectedItem();
                    String idPaiss = cat.split(":")[0];
                    Categoria objPaiss = new Categoria();
                    objPaiss.setIdCategoria(Integer.parseInt(idPaiss));

                    String paises = spnCrudLibroPais.getSelectedItem().toString();//convierte a string
                    String idpaises = paises.split(":")[0];//corta y recibe el id
                    Pais objPais= new Pais();
                    objPais.setIdPais(Integer.parseInt(idpaises));

                    Libro objNewCliente = new Libro();
                    objNewCliente.setTitulo(titul);
                    objNewCliente.setAnio(Integer.parseInt(an));
                    objNewCliente.setSerie(ser);
                    objNewCliente.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objNewCliente.setEstado(1);
                    objNewCliente.setCategoria(objPaiss);
                    objNewCliente.setPais(objPais);


                    if (tipo.equals("REGISTRA")){
                        insertaLibro(objNewCliente);
                    }else if (tipo.equals("ACTUALIZA")){
                        Libro objAux = (Libro) extras.get("var_objeto");
                        objNewCliente.setIdLibro(objAux.getIdLibro());
                        actualizaLibro(objNewCliente);
                    }
                }
            }
        });
    }

    public void insertaLibro(Libro objLibro){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objLibro);

        mensajeAlert(json);

        Call<Libro> call = serviceLibro.insertaEditorial(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful()){
                    Libro objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdLibro()
                            + " >>> Razón Social >>> " +  objSalida.getTitulo());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
    public void actualizaLibro(Libro objCliente){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objCliente);
        // mensajeAlert(json);
        Call<Libro> call = serviceLibro.actualizaLibro(objCliente);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if(response.isSuccessful()){
                    Libro objSalida = response.body();
                    String msg="Se actualizó el Libro con exito\n";
                    msg+="ID : "+ objSalida.getIdLibro() +"\n";
                    msg+="Titulo : "+ objSalida.getTitulo() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaCategoria(){
        //llama al metodo del service
        Call<List<Categoria>> call = serviceCategoria.listaCategoriaDeLibro();
        call.enqueue(new Callback<List<Categoria>>() {
            //all esta ok
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<Categoria> lstPaises=  response.body();//guarda all data
                    //clase guia desenvolsa
                    for(Categoria obj: lstPaises){
                        //se almacena en el array lstNombresCategoria con los valores concatendaos  el id y descripcion
                        lstNombresCategoria.add(obj.getIdCategoria() + ":" + obj.getDescripcion());
                    }
                    //notifica al adaptador
                    adaptadorCategoria.notifyDataSetChanged();

                    if(tipo.equals("ACTUALIZA")){

                        //de ese revista obtiene la pais solo de ese revista
                        Pais objPais = objLibroSeleccionado.getPais();
                        //concatena en 1 string de esa pais el id y Descripcion
                        String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos = -1; //obtine la posicion
                        for(int i=0; i< lstNombresCategoria.size(); i++){
                            if (lstNombresCategoria.get(i).equals(aux2)){
                                //encuentra la posicion
                                pos = i;
                                break; //finaliza el proceso
                            }
                        }

                        spnCrudLibroCategoria.setSelection(pos);

                    }
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }


            //si hay falla
            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
        public void cargaPaises(){

            //llama al metodo del service
            Call<List<Pais>> call = servicePais.listaPais();
            call.enqueue(new Callback<List<Pais>>() {
                //all esta ok
                @Override
                public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                    if (response.isSuccessful()){
                        List<Pais> lstPaises=  response.body();//guarda all data
                        //clase guia desenvolsa
                        for(Pais obj: lstPaises){
                            //se almacena en el array lstNombresCategoria con los valores concatendaos  el id y descripcion
                            lstNombresPais.add(obj.getIdPais() + ":" + obj.getNombre());
                        }
                        //notifica al adaptador
                        adaptadorPais.notifyDataSetChanged();

                        if(tipo.equals("ACTUALIZA")){

                            //de ese revista obtiene la pais solo de ese revista
                            Pais objPais = objLibroSeleccionado.getPais();
                            //concatena en 1 string de esa pais el id y Descripcion
                            String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                            int pos = -1; //obtine la posicion
                            for(int i=0; i< lstNombresPais.size(); i++){
                                if (lstNombresPais.get(i).equals(aux2)){
                                    //encuentra la posicion
                                    pos = i;
                                    break; //finaliza el proceso
                                }
                            }

                            spnCrudLibroPais.setSelection(pos);

                        }
                    }else{
                        mensajeToast("Error al acceder al Servicio Rest >>> ");
                    }
                }


                //si hay falla
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
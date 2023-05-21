package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;










// esto es para METODO FECHA DATE DEL INPUT DATE PARA VER Y ELEJIR MEJOR LA FECHA
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;







//GSON IMPORTACIONES
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;








import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaRegistraActivity extends NewAppCompatActivity {





//===========================================================


    //CARGA SPINNER O COMBO MODALIDAD
    // los numeros son el orden de los pasos
    Spinner spnModalidad; // 3

    ArrayAdapter<String> adaptadorModalidad;   //2

    ArrayList<String> listaModal = new ArrayList<String>();  // 1

    //recordar no es cribir igual la variable al tipo de dato rest
    ServiceModalidad serviceModalidad; //trae all rest especifico o hijo all la data adentro






//===========================================================


    //CARGA SPINNER O COMBO PAIS

    Spinner spnPaises;  //3
    ArrayAdapter<String> adaptadoPaises;   //2

    ArrayList<String> listaPises = new ArrayList<String>();  // 1

    ServicePais servicepais; //trae all rest especifico o hijo all la data adentro








//===========================================================


    // DECLARANDO VARIBALE INSERTAR REGISTRAR EL TIPO DE DATO




    Button btnRegistrar;

    EditText txtRevista;

    EditText txtFrecuencia;

    EditText txtFecha;


    ServiceRevista servicerevista;//insertar , trae all el rest especifico pa insertar









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revista_registra);



//===========================================================
        //CARGA SPINNER O COMBO MODALIDAD

        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaModal);
        spnModalidad = findViewById(R.id.spnModalidaRevis);
        spnModalidad.setAdapter(adaptadorModalidad);

        // metodo carga combo

        cargaModalidad();




//===========================================================
        //CARGA SPINNER O COMBO PAIS

        servicepais = ConnectionRest.getConnection().create(ServicePais.class);


        adaptadoPaises = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaPises);
        spnPaises = findViewById(R.id.spnPaisRevis);
        spnPaises.setAdapter(adaptadoPaises);

        cargaPaises();






        //===========================================================
        //INSERTAR REVISTA CON EL REST : recibiendo data de los inputs

        servicerevista=ConnectionRest.getConnection().create(ServiceRevista.class);   // insertar




        //===========================================================

        // recibe el id de la gui para relacionarlos con sus inputs y relacionado con sus variables
        //osea el input se almacena en la variable

        txtRevista = findViewById(R.id.txtNombreRevista);
        txtFrecuencia =  findViewById(R.id.txtFrecuenciaRevista);
        txtFecha =   findViewById(R.id.txtFechaRevista);
        btnRegistrar =  findViewById(R.id.btnRegistRevista);








        // METODO AYUDA AL USUARIO , EN EL INPUT FECHA DE CREACION AL ELEGIR , MAS RAPIDO Y + BONITO LA FECHA (AÑO - MES -DIA)FORMATO
        // ======================================================================

        // esto sirve para el metodo de abajo METODO AYUDA USUARIO PARA FECHA
        //basimamente es para que se pueda ver en español esa ayuda
        Locale.setDefault( new Locale("es_ES"));



        //txtFecha : variable de fecha pal usuario
        txtFecha.setOnClickListener(new View.OnClickListener() {


            //explciacion cada vez que que demos click en el input creara un calendario
            @Override

            public void onClick(View view) {

                Calendar myCalendar= Calendar.getInstance();

                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(

                        //RevistaRegistraActivity : esto poner el mainactivity donde te encuentra ose el nombre del archivo en el que estamos

                        RevistaRegistraActivity.this,

                        new DatePickerDialog.OnDateSetListener() {

                            @Override

                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {



                                myCalendar.set(Calendar.YEAR, year);

                                myCalendar.set(Calendar.MONTH,month);

                                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                                txtFecha.setText(dateFormat.format(myCalendar.getTime()));

                            }

                        },

                        myCalendar.get(Calendar.YEAR),

                        myCalendar.get(Calendar.MONTH),

                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }

        });










        // ====================================================================================================
        // INSERTAR METODO PARA EDITORIAL

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //recibiendo la data
                String nomRevis = txtRevista.getText().toString();
                String frecuRevi = txtFrecuencia.getText().toString();
                String fecRevis = txtFecha.getText().toString();




                //validaciones
                if(!nomRevis.matches(ValidacionUtil.TEXTO)){

                    txtRevista.setError("La revista  es de 2 a 20 caracteres");


                } else if (!frecuRevi.matches(ValidacionUtil.TEXTO)) {
                    txtFrecuencia.setError("frecuencia es de 2 a 20 caracteres");
                }else if (!fecRevis.matches(ValidacionUtil.FECHA)) {
                    txtFecha.setError("La fecha de creación es YYYY-MM-dd");
                }else{



                    //recibiendo el id modalidad
                    String modalidad = spnModalidad.getSelectedItem().toString();
                    String idModalidad = modalidad.split(":")[0];
                    Modalidad objModalidad = new Modalidad();
                    objModalidad.setIdModalidad(Integer.parseInt(idModalidad));



                    //recibiendo el id pais
                    String paises = spnPaises.getSelectedItem().toString();
                    String idpaises = paises.split(":")[0];
                    Pais objPais= new Pais();
                    objPais.setIdPais(Integer.parseInt(idpaises));


                    //FINAL PASANDO LOS DATOS ORDENADOS A SUS REST FINAL RESPECTIVO

                    Revista objRevista = new Revista();


                    objRevista.setNombre(nomRevis);
                    objRevista.setFrecuencia(frecuRevi);
                    objRevista.setFechaCreacion(fecRevis);
                    objRevista.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    objRevista.setEstado(1);
                    objRevista.setModalidad(objModalidad);
                    objRevista.setPais(objPais);



                 insertaRevista(objRevista);




















                }


            }
        });

        // =================================================================================================

































    }








//===========================================================
    //METODOS VOID
    //  metodos void fuera de la carga al inicio de la app(agi dentro lo llamamamos)










    //METODO PRINCIPAL INSERTAR EDITORIAL
    //insertaRevista(Revista objRevista) : objRevista debe estar escrito igual en ServiceRevista osea el service rest que registraras



    public  void insertaRevista(Revista objRevista){



        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objRevista);
        //mensajeAlert(json);





        Call<Revista> call = servicerevista.insertaRevista(objRevista);

        call.enqueue(new Callback<Revista>() {
            //all ok
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()){
                    Revista objRevista = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objRevista.getIdRevista()
                            + " >>> nombre de revista>>> " +  objRevista.getNombre());
                }else{
                    mensajeAlert(response.toString());
                }
            }

            //si hay error
            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });








    }




















    //1 METODO MODALIDADES##############
    //CARGA COMBO MODADALIDAD SUBCLASE DE REVISTA
    public void cargaModalidad(){


        Call<List<Modalidad>> call = serviceModalidad.listamodalidad();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lstModalidad =  response.body();
                    for(Modalidad obj: lstModalidad){
                        listaModal.add(obj.getIdModalidad()+":"+ obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }


            //si hay falla
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }












    //2 METODO PAISES##############


    public void cargaPaises(){


        Call<List<Pais>> call = servicepais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstPaises=  response.body();
                    for(Pais obj: lstPaises){
                        listaPises.add(obj.getIdPais() + ":" + obj.getNombre());
                    }
                    adaptadoPaises.notifyDataSetChanged();
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














    //muestra los mensajes


    //MENSAJE TEMPORAL EN LA APP DEL CEL GUI aparece abajo
    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }



    //mensaje permanente que se mostrara en la app

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }











}
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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;








import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.registra.RevistaRegistraActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaCrudFormularioActivity extends NewAppCompatActivity {







    //incializamos las variables(recomendable poner el mismo nombre de las variables con sus inputs de este layaout)
    Button btnCrudRegresar , btnCrudRegistra;
    TextView txtTitulo;



    EditText txtRevista;

    EditText txtFrecuencia;

    EditText txtFecha;


    ServiceRevista servicerevista;//insertar , trae all el rest especifico pa insertar









//===========================================================


    //CARGA SPINNER O COMBO MODALIDAD
    // los numeros son el orden de los pasos
    Spinner spnModalidad; // 3

    ArrayAdapter<String> adaptadorModalidad;   //2

    //aca se ponde all la data
    ArrayList<String> listaModal = new ArrayList<String>();  // 1

    //recordar no es cribir igual la variable al tipo de dato rest
    ServiceModalidad serviceModalidad; //trae all rest especifico o hijo all la data adentro









//===========================================================


    //CARGA SPINNER O COMBO PAIS

    Spinner spnPaises;  //3
    ArrayAdapter<String> adaptadoPaises;   //2

    //aca se pone all la data
    ArrayList<String> listaPises = new ArrayList<String>();  // 1

    ServicePais servicepais; //trae all rest especifico o hijo all la data adentro











    //===========================================================
//    REGISTRAR CLIENTE
    ServiceRevista serviceRevista ;






    //===========================================================
    //como globlal
    //El tipo define si es REGISTRA o ACTUALIZA osea es la variable pa cambiar el titulo del boton ademas determina si registra o actualiza
    String tipo;





    //===========================================================
    //como globlal
    //Se recibe el cliente seleccionado
    Revista objRevistaSeleccionado;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_formulario);



//        ====================================================================================================================
        //declarando los componente que faltan con sus id del layaout


        txtRevista = findViewById(R.id.txtNombreRevista);
        txtFrecuencia =  findViewById(R.id.txtFrecuenciaRevista);
        txtFecha =   findViewById(R.id.txtFechaRevista);

















//===========================================================
        //CARGA SPINNER O COMBO MODALIDAD
        // OJO IMPORATANTE SI HAY UN  ERROR EL REST A CARGAR EL COMBO SOLO LEVANTAR TU INSOMIA Y Y DALE GET A LOS REST HIJOS(COMBOS)solo inicialos

        //conecta con el service especifico
        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaModal);
        spnModalidad = findViewById(R.id.spnModalidaRevis);
        spnModalidad.setAdapter(adaptadorModalidad);

        // metodo carga combo si vamos actulizar ponerlo debajo del tipo variable
        //cargaModalidad();













//===========================================================
        //CARGA SPINNER O COMBO PAIS
        // OJO IMPORATANTE SI HAY UN  ERROR EL REST A CARGAR EL COMBO SOLO LEVANTAR TU INSOMIA Y Y DALE GET A LOS REST HIJOS(COMBOS)solo inicialos

        //conecta con el service especifico
        servicepais = ConnectionRest.getConnection().create(ServicePais.class);


        adaptadoPaises = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaPises);
        spnPaises = findViewById(R.id.spnPaisRevis);
        spnPaises.setAdapter(adaptadoPaises);

        // metodo carga combo si vamos actulizar ponerlo debajo del tipo variable
        //cargaPaises();









        //===========================================================
        //INSERTAR REVISTA CON EL REST : recibiendo data de los inputs

        servicerevista=ConnectionRest.getConnection().create(ServiceRevista.class);   // insertar









//        ====================================================================================================================
        //VERIFICA SI VA A ACTULIZAR O VA REGISTRAR SEGUN ESO CAMBIAR EL VALOR DE INPUTS DE ESTE LAYAOUT
        //declaramos las variables con los componentes para ser cambiado sus valores dependiendo del usuario
        btnCrudRegistra = findViewById(R.id.btnRegistRevista);
        txtTitulo = findViewById(R.id.idCrudTituloRevista);

        //RECIBIENDO DATA YA SEA ACTULIZAR O RGISTRAR DE LOS METODOS DEL RevistaCrudListaActivity
        //si es del boton registrar cambiar los valores de los componetes a registrar
        //si es cuando seleciona un item solo 1 cambia el valor a actualizar
        //var_tipo y var_titulo : estas variable debe estar escrita igual en el RevistaCrudListaActivity
        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");



        //recomendable poner debajo de esto el carga catgeoria para ver si es actualiza
        //poner aca los metodo de cargas combos en caso falle
        cargaModalidad();
        cargaPaises();


        //cambiar tanto el boton como la caja de texto
        btnCrudRegistra.setText(tipo);
        txtTitulo.setText(titulo);










        //SOLO PARA ACTUALIZA PARA PINTAR LA DATA SELECCIONADA
        //osea esto cuando de all lista de clientes del ClienteCrudListaActivity seleccionamos 1
        // METODO IGUAL PERO CUANDO DE CLICK EN ClienteCrudListaActivity EN UN OBJETO CLIENTE SE ABRIRE ESTE ACTIVITY JUNTO CON SU
        //LAYAOUT CON LA DATA YA CARGADA , se debe cargar la data o pintar EN LOS INPUTS ADEMAS VERIFICA SI ES ACTUALIZA CON EL EQUALS


        if (tipo.equals("ACTUALIZA")){

            //obtiene  el cliente solo el seleccionado
            objRevistaSeleccionado = (Revista) extras.get("var_objeto");

            // pinta los inputs con esa data de ese objeto
            txtRevista.setText(objRevistaSeleccionado.getNombre());
            txtFrecuencia.setText(objRevistaSeleccionado.getFrecuencia());
            txtFecha.setText(objRevistaSeleccionado.getFechaCreacion());

            //recordar si hay combos dirigite ahi tambien para cargar el combo respectivo







        }































        // ==========================================================================================================
        // METODO AYUDA AL USUARIO , EN EL INPUT FECHA DE CREACION AL ELEGIR , MAS RAPIDO Y + BONITO LA FECHA (AÑO - MES -DIA)FORMATO
        //recordar importar + arriba
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

                        RevistaCrudFormularioActivity.this,

                        new DatePickerDialog.OnDateSetListener() {

                            @Override

                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {



                                myCalendar.set(Calendar.YEAR, year);

                                myCalendar.set(Calendar.MONTH,month);

                                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                                //poner la variable donde esta inout fecha txtFecha

                                txtFecha.setText(dateFormat.format(myCalendar.getTime()));

                            }

                        },

                        myCalendar.get(Calendar.YEAR),

                        myCalendar.get(Calendar.MONTH),

                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }

        });




























        //        ===========================================================================================
        //BOTON PARA REGRESAR
        //declaramos las variables este boton sera para regresar
        btnCrudRegresar = findViewById(R.id.btnRegresaRevista);

        //evento al dar clcik para regresar  EVENTO PARA REGRESAR AL RevistaCrudListaActivity
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RevistaCrudFormularioActivity.this, //donde estoy
                        RevistaCrudListaActivity.class); //y adonde quiero ir q es regresar
                startActivity(intent);
            }
        });















        // ====================================================================================================
        // INSERTAR METODO PARA REVISTA

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //recibiendo la data de los inputs
                String nomRevis = txtRevista.getText().toString();
                String frecuRevi = txtFrecuencia.getText().toString();
                String fecRevis = txtFecha.getText().toString();




                //matches : verifica si es igual , si no es igual lanza false y el if lo convierte a v y ejecuta
                //validaciones verificando esos inputs qu cumplan ciertos parametros
                if(!nomRevis.matches(ValidacionUtil.TEXTO)){

                    //dentro del input le mostrara un icono de advertencia para que corriga
                    txtRevista.setError("La revista  es de 2 a 20 caracteres");


                } else if (!frecuRevi.matches(ValidacionUtil.TEXTO)) {
                    //dentro del input le mostrara un icono de advertencia  para que corriga
                    txtFrecuencia.setError("frecuencia es de 2 a 20 caracteres");
                }else if (!fecRevis.matches(ValidacionUtil.FECHA)) {
                    //dentro del input le mostrara un icono de advertencia  para que corriga
                    txtFecha.setError("La fecha de creación es YYYY-MM-dd");
                }else{



                    //recibiendo el id modalidad rest hijo
                    String modalidad = spnModalidad.getSelectedItem().toString(); //convierte a string
                    String idModalidad = modalidad.split(":")[0]; //corta y recibe el id
                    Modalidad objModalidad = new Modalidad();
                    objModalidad.setIdModalidad(Integer.parseInt(idModalidad));//almacena el solo el id



                    //recibiendo el id pais rest hijo
                    String paises = spnPaises.getSelectedItem().toString();//convierte a string
                    String idpaises = paises.split(":")[0];//corta y recibe el id
                    Pais objPais= new Pais();
                    objPais.setIdPais(Integer.parseInt(idpaises));//almacena el solo el id


                    //FINAL PASANDO LOS DATOS ORDENADOS A SUS REST FINAL RESPECTIVO
                     // recordar que el id no se envia el rest lo crea
                    //ademas ciertos datos se haran manualmente como el esta fecha creacion solo para el registrar
                    //siempre enviar all los datos ya sea al registra o actualizar
                    Revista objRevista = new Revista();


                    objRevista.setNombre(nomRevis);
                    objRevista.setFrecuencia(frecuRevi);
                    objRevista.setFechaCreacion(fecRevis);
                    //estos 2 valores solo seran para registra ademas no saldra en el layaout pq lo hcaemos nosotros
                    objRevista.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());//lo mandamos por aqui dato de hoy
                    objRevista.setEstado(1);//lo mandamos por aqui el estado obvio es 1

                    objRevista.setModalidad(objModalidad); //rest hijo solo su id
                    objRevista.setPais(objPais);//rest hijo solo su id solo su id









                    //=====================================================================
                    //VERIFICA SI ES ACTUALIZA O REGISTRA
                    //VERIFICAR SEGUN LA VARIABLE   String tipo = (String) extras.get("var_tipo"); es la variable segun sea registra o actualizar

                    if (tipo.equals("REGISTRA")){


                        //cuando registra el id lo crea el rest no en se envia eso
                        insertaRevista(objRevista);
                    }else if (tipo.equals("ACTUALIZA")){
                        //actualiza la data ya esta cargada y despues segun el usuario lo actualiza pero falta el id para enviar y aca lo obtenemos
                        Revista objAux = (Revista) extras.get("var_objeto"); //obtiene nuevamente el objeto de all grid de REVISTA solo 1
                        objRevista.setIdRevista(objAux.getIdRevista()); //obitene su id lo que faltaba pq el id no debe moodificarse
                        actualizaRevista(objRevista); // ahora si en envia el objeto completo
                    }

                    //=====================================================================
















                }


            }
        });

        // =================================================================================================




























    }








    //METOD0O VOID NO DEVUELVE NADA POR ESO ESTAN AFUERA DEL ONCREATE











    //____________________________________________________________________________________________
    //METODO PRINCIPAL INSERTAR REVISTA
    //insertaRevista(Revista objRevista) : objRevista debe estar escrito igual en ServiceRevista osea el service rest que registraras

    public  void insertaRevista(Revista objRevista){



        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objRevista);
        //mensajeAlert(json);




        //entra al service por el metodo
        Call<Revista> call = servicerevista.insertaRevista(objRevista);

        call.enqueue(new Callback<Revista>() {
            //all ok
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()){
                    //dato de regreso para confimrar el envio
                    Revista objRevista = response.body();
                    //mensaje aparece en la app permanete con el dato enviado
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
    //____________________________________________________________________________________________















    //        ===========================================================================================
    //METODO ACTUALIZA CLIENTE  PARECIDO A INSERTA

    public void actualizaRevista(Revista objRevista){


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objRevista);
        // mensajeAlert(json);

        //entra al service por el metodo
        Call<Revista> call = servicerevista.actualizaRevista(objRevista);
        call.enqueue(new Callback<Revista>() {
            //all ok
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if(response.isSuccessful()){
                    //dato de regreso para confimrar el envio
                    Revista objSalida= response.body();
                    //mensaje aparece en la app permanete con el dato enviado
                    String msg="Se actualizó revista con exito\n";
                    msg+="ID : "+ objSalida.getIdRevista() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombre() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            //si hay error
            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }



















































    //1 METODO MODALIDADES#######################################################################################
    ////===========================================================================================================
    // OJO IMPORATANTE SI HAY UN  ERROR EL REST A CARGAR EL COMBO SOLO LEVANTAR TU INSOMIA Y Y DALE GET A LOS REST HIJOS(COMBOS)solo inicialos



    //CARGA COMBO MODADALIDAD SUBCLASE DE REVISTA
    public void cargaModalidad(){


        //llama al metodo del service
        Call<List<Modalidad>> call = serviceModalidad.listamodalidad();
        call.enqueue(new Callback<List<Modalidad>>() {
            //all esta ok
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lstModalidad =  response.body(); //guarda all data
                    //clase guia desenvolsa
                    for(Modalidad obj: lstModalidad){
                        //se almacena en el array lstNombresCategoria con los valores concatendaos  el id y descripcion
                        listaModal.add(obj.getIdModalidad()+":"+ obj.getDescripcion());
                    }
                    //notifica al adaptador
                    adaptadorModalidad.notifyDataSetChanged();



                    //___________________________ VERIFICA CUANDO LA CARGA LA APCLICACION SI LA SI ES PARA ACTUALIZAR OSEA SELECCIONA DEL GRIDVIEW
                    //a traves de la variable tipo que contiene el valor osea cuando pulsar el o selecciona un o objteo revista de all el girdview
                    //va a acargar esa modalidad en el combo
                    //basicamente carga la modalidad segun el objeto seleccionado

                    if(tipo.equals("ACTUALIZA")){

                        //de ese revista obtiene la modalidad solo de ese revista
                        Modalidad objModalidad = objRevistaSeleccionado.getModalidad();
                        //concatena en 1 string de esa modalidad el id y Descripcion
                        String aux2 = objModalidad.getIdModalidad()+":"+objModalidad.getDescripcion();
                        int pos = -1; //obtine la posicion
                        //hace un for listaModal que ya lo tiene cargado la data de modalidad
                        //y compara con el equals con el string aux2 si es igual obtiene esa posicion
                        for(int i=0; i< listaModal.size(); i++){
                            if (listaModal.get(i).equals(aux2)){
                                //encuentra la posicion
                                pos = i;
                                break; //finaliza el proceso
                            }
                        }

                        // spnModalidad que es el combo mostra esa categoria segun la posicion
                        spnModalidad.setSelection(pos);

                    }
                    //______________________________________




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







    //2 METODO PAISES#########################################################################


    public void cargaPaises(){



        //llama al metodo del service
        Call<List<Pais>> call = servicepais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            //all esta ok
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstPaises=  response.body();//guarda all data
                    //clase guia desenvolsa
                    for(Pais obj: lstPaises){
                        //se almacena en el array lstNombresCategoria con los valores concatendaos  el id y descripcion
                        listaPises.add(obj.getIdPais() + ":" + obj.getNombre());
                    }
                    //notifica al adaptador
                    adaptadoPaises.notifyDataSetChanged();



                    //___________________________ VERIFICA CUANDO LA CARGA LA APCLICACION SI LA SI ES PARA ACTUALIZAR OSEA SELECCIONA DEL GRIDVIEW
                    //a traves de la variable tipo que contiene el valor osea cuando pulsar el o selecciona un o objteo revista de all el girdview
                    //va a acargar esa pais en el combo
                    //basicamente carga el pais combo segun el objeto seleccionado

                    if(tipo.equals("ACTUALIZA")){

                        //de ese revista obtiene la pais solo de ese revista
                        Pais objPais = objRevistaSeleccionado.getPais();
                        //concatena en 1 string de esa pais el id y Descripcion
                        String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos = -1; //obtine la posicion
                        //hace un for listaPises que ya lo tiene cargado la data de pais
                        //y compara con el equals con el string aux2 si es igual obtiene esa posicion
                        for(int i=0; i< listaPises.size(); i++){
                            if (listaPises.get(i).equals(aux2)){
                                //encuentra la posicion
                                pos = i;
                                break; //finaliza el proceso
                            }
                        }

                        // spnPaises que es el combo mostra esa pais segun la posicion
                        spnPaises.setSelection(pos);

                    }
                    //______________________________________









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

















    //===========================================================================================================
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
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

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Grado;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAutor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceGrado;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.vista.registra.AutorRegistraActivity;
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

public class AutorCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegresar , btnCrudRegistra;
    TextView txtTitulo;
    EditText txtNombre,txtApellido,txtCorreo,txtFechaNaci,txtTelefono;
    Spinner spnPais,spnGrado;

    ArrayAdapter<String> adapPais;
    ArrayList<String> lstNombresPais = new ArrayList<String>();

    ArrayAdapter<String> adapGrado;
    ArrayList<String> lstNombresgrado = new ArrayList<String>();

    ServicePais servicePais;
    ServiceGrado serviceGrado;
    ServiceAutor serviceAutor;

    String tipo;

    Autor objAutorSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_formulario);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceGrado = ConnectionRest.getConnection().create(ServiceGrado.class);
        serviceAutor =  ConnectionRest.getConnection().create(ServiceAutor.class);


        adapPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPais);
        spnPais = findViewById(R.id.spnCrudAutorPais);
        spnPais.setAdapter(adapPais);

        adapGrado= new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresgrado);
        spnGrado = findViewById(R.id.spnCrudAutorCargo);
        spnGrado.setAdapter(adapGrado);

        txtNombre = findViewById(R.id.txtCrudNombre);
        txtApellido = findViewById(R.id.txtCrudApellido);
        txtCorreo = findViewById(R.id.txtCrudCorreo);
        txtFechaNaci= findViewById(R.id.txtCrudFechaNaci);
        txtTelefono = findViewById(R.id.txtCrudTelefono);

        btnCrudRegistra = findViewById(R.id.btnCrudRegistrar);
        txtTitulo = findViewById(R.id.idCrudTituloAutor);

        Locale.setDefault( new Locale("es_ES"));

        txtFechaNaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(
                        AutorCrudFormularioActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {

                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH,month);
                                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                                txtFechaNaci.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        cargaGrado();
        cargaPais();
        if (tipo.equals("ACTUALIZA")){
            objAutorSeleccionado = (Autor) extras.get("var_objeto");
            txtNombre.setText(objAutorSeleccionado.getNombres());
            txtApellido.setText(objAutorSeleccionado.getApellidos());
            txtCorreo.setText(objAutorSeleccionado.getCorreo());
            txtFechaNaci.setText(objAutorSeleccionado.getFechaNacimiento());
            txtTelefono.setText(objAutorSeleccionado.getTelefono());
        }
        btnCrudRegistra.setText(tipo);
        txtTitulo.setText(titulo);

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        AutorCrudFormularioActivity.this,
                        AutorCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //datos q recibe de string
                String nom = txtNombre.getText().toString();
                String ape = txtApellido.getText().toString();
                String corr = txtCorreo.getText().toString();
                String fechNa = txtFechaNaci.getText().toString();
                String tele = txtTelefono.getText().toString();

                if(!nom.matches(ValidacionUtil.NOMBRE)){
                    txtNombre.setError("El nombre es de 3 a 30 caracteres");
                }
                else if(!ape.matches(ValidacionUtil.NOMBRE)){
                    txtApellido.setError("El apellido es de 3 a 30 caracteres");
                }
                else if(!corr.matches(ValidacionUtil.CORREO)){
                    txtCorreo.setError("Correo inválido");
                }
                else if(!tele.matches(ValidacionUtil.CELULAR)){
                    txtTelefono.setError("El teléfono debe ser de 9 caracteres");
                }else if (!fechNa.matches(ValidacionUtil.FECHA)){
                    txtFechaNaci.setError("La fecha de nacimiento es YYYY-MM-dd");
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


                if (tipo.equals("REGISTRA")){
                    insertaAutor(objAutor);
                }else if (tipo.equals("ACTUALIZA")){
                    Autor objAux = (Autor) extras.get("var_objeto");
                    objAutor.setIdAutor(objAux.getIdAutor());
                    actualizaAutor(objAutor);
                }
                }
            }
        });
    }

    public void insertaAutor(Autor objAutor){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAutor);
        // mensajeAlert(json);
        Call<Autor> call = serviceAutor.insertarAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if(response.isSuccessful()){
                    Autor objSalida = response.body();
                    String msg="Autor se registró con exito\n";
                    msg+="ID : "+ objSalida.getIdAutor() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombres() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void actualizaAutor(Autor objAutor){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAutor);
        // mensajeAlert(json);
        Call<Autor> call = serviceAutor.actualizaAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if(response.isSuccessful()){
                    Autor objSalida = response.body();
                    String msg="Se actualizó con exito\n";
                    msg+="ID : "+ objSalida.getIdAutor() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombres() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }
    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if(response.isSuccessful()){
                    List<Pais> lst = response.body();
                    for(Pais obj:lst){
                        lstNombresPais.add(obj.getIdPais()+":"+obj.getNombre());
                    }
                    adapPais.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Pais objPais = objAutorSeleccionado.getPais();
                        String aux2 = objPais.getIdPais()+":"+objPais.getNombre();
                        int pos = -1;
                        for(int i=0; i< lstNombresPais.size(); i++){
                            if (lstNombresPais.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnPais.setSelection(pos);
                    }

                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
    public void cargaGrado(){
        Call<List<Grado>> call = serviceGrado.Todos();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if(response.isSuccessful()){
                    List<Grado> lst = response.body();
                    for(Grado obj:lst){
                        lstNombresgrado.add(obj.getIdGrado()+":"+obj.getDescripcion());
                    }
                    adapGrado.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Grado objGrado = objAutorSeleccionado.getGrado();
                        String aux2 = objGrado.getIdGrado()+":"+objGrado.getDescripcion();
                        int pos = -1;
                        for(int i=0; i< lstNombresgrado.size(); i++){
                            if (lstNombresgrado.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnGrado.setSelection(pos);
                    }

                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

}
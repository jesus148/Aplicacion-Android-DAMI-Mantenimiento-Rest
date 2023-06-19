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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
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

public class EditorialCrudFormularioActivity extends NewAppCompatActivity {

    Button btnCrudRegresar , btnCrudRegistra;
    TextView txtTitulo;

    EditText txtNombre, txtDireccion, txtRuc, txtFechaCrea;
    Spinner spnPais, spnCategoria;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> lstNombresCategoria = new ArrayList<String>();

    ServiceCategoria serviceCategoria ;

    ServicePais servicePais;
    ServiceEditorial serviceEditorial ;

    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstNombresPais = new ArrayList<String>();

    //El tipo define si es REGISTRA o ACTUALIZA
    String tipo;

    //Se recibe el cliente seleccionado
    Editorial objEditorialSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_formulario);

        serviceCategoria = ConnectionRest.getConnection().create(ServiceCategoria.class);
        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);

        btnCrudRegistra = findViewById(R.id.btnCrudRegistrar);
        txtTitulo = findViewById(R.id.idCrudTituloEditorial);

        txtNombre = findViewById(R.id.txtCrudNombre);
        txtDireccion = findViewById(R.id.txtCrudDireccion);
        txtRuc = findViewById(R.id.txtCrudRuc);
        txtFechaCrea = findViewById(R.id.txtCrudFechaCreación);
        spnPais = findViewById(R.id.spnCrudEditorialPais);
        spnCategoria = findViewById(R.id.spnCrudEditorialCategoria);

        adaptadorCategoria = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresCategoria);
        spnCategoria.setAdapter(adaptadorCategoria);


        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPais);
        spnPais.setAdapter(adaptadorPais);

        txtFechaCrea.setOnClickListener(new View.OnClickListener() {


            //explciacion cada vez que que demos click en el input creara un calendario
            @Override

            public void onClick(View view) {

                Calendar myCalendar= Calendar.getInstance();

                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

                new DatePickerDialog(


                        EditorialCrudFormularioActivity.this,

                        new DatePickerDialog.OnDateSetListener() {

                            @Override

                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {



                                myCalendar.set(Calendar.YEAR, year);

                                myCalendar.set(Calendar.MONTH,month);

                                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                                txtFechaCrea.setText(dateFormat.format(myCalendar.getTime()));

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

        cargaCategoria();
        cargaPais();

        if (tipo.equals("ACTUALIZA")){
            objEditorialSeleccionado = (Editorial) extras.get("var_objeto");
            txtNombre.setText(objEditorialSeleccionado.getRazonSocial());
            txtDireccion.setText(objEditorialSeleccionado.getDireccion());
            txtRuc.setText(objEditorialSeleccionado.getRuc());
            txtFechaCrea.setText(objEditorialSeleccionado.getFechaCreacion());
        }


        btnCrudRegistra.setText(tipo);
        txtTitulo.setText(titulo);

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        EditorialCrudFormularioActivity.this,
                        EditorialCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtNombre.getText().toString();
                String dir = txtDireccion.getText().toString();
                String ruc = txtRuc.getText().toString();
                String fec = txtFechaCrea.getText().toString();
                String pais = spnPais.getSelectedItem().toString();
                String categoria = spnCategoria.getSelectedItem().toString();

                Categoria objNewCategoria = new Categoria();
                objNewCategoria.setIdCategoria(Integer.parseInt(categoria.split(":")[0]));

                Pais objNewPais = new Pais();
                objNewPais.setIdPais(Integer.parseInt(pais.split(":")[0]));

                Editorial objNewEditorial = new Editorial();
                objNewEditorial.setRazonSocial(nom);
                objNewEditorial.setDireccion(dir);
                objNewEditorial.setRuc(ruc);
                objNewEditorial.setFechaCreacion(fec);
                objNewEditorial.setPais(objNewPais);
                objNewEditorial.setCategoria(objNewCategoria);

                if (tipo.equals("REGISTRA")){
                    insertaEditorial(objNewEditorial);
                }else if (tipo.equals("ACTUALIZA")){
                    Editorial objAux = (Editorial) extras.get("var_objeto");
                    objNewEditorial.setIdEditorial(objAux.getIdEditorial());
                    actualizaEditorial(objNewEditorial);
                }
            }
        });

    }

    public void insertaEditorial(Editorial objEditorial){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objEditorial);
        // mensajeAlert(json);
        Call<Editorial> call = serviceEditorial.insertaEditorial(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if(response.isSuccessful()){
                    Editorial objSalida = response.body();
                    String msg="Se registró el Cliente con exito\n";
                    msg+="ID : "+ objSalida.getIdEditorial() +"\n";
                    msg+="NOMBRE : "+ objSalida.getRazonSocial() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void actualizaEditorial(Editorial objEditorial){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objEditorial);
        // mensajeAlert(json);
        Call<Editorial> call = serviceEditorial.actualizaEditorial(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if(response.isSuccessful()){
                    Editorial objSalida = response.body();
                    String msg="Se actualizó el Cliente con exito\n";
                    msg+="ID : "+ objSalida.getIdEditorial() +"\n";
                    msg+="NOMBRE : "+ objSalida.getRazonSocial() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void cargaCategoria(){
        Call<List<Categoria>> call = serviceCategoria.listaCategoriaDeEditorial();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    List<Categoria> lst = response.body();
                    for(Categoria obj:lst){
                        lstNombresCategoria.add(obj.getIdCategoria()+":"+obj.getDescripcion());
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Categoria objCategoria = objEditorialSeleccionado.getCategoria();
                        String aux2 = objCategoria.getIdCategoria()+":"+objCategoria.getDescripcion();
                        int pos = -1;
                        for(int i=0; i< lstNombresCategoria.size(); i++){
                            if (lstNombresCategoria.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnCategoria.setSelection(pos);
                    }

                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
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
                    adaptadorPais.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Pais objPais = objEditorialSeleccionado.getPais();
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

}
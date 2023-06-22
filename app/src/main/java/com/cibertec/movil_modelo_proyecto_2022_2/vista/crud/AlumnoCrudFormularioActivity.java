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

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
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

public class AlumnoCrudFormularioActivity extends NewAppCompatActivity {
    Button btnCrudRegresar , btnCrudRegistra;
    TextView txtTitulo;

    EditText txtNombre, txtDireccion, txtDni, txtFechaCrea,txtApellido,txtTelefono,txtCorreo,getTxtDireccion,txtFechaNacimiento,txtEstado,txtId;
    Spinner spnPais, spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> lstNombresModalidad = new ArrayList<String>();

    ServiceModalidad serviceModalidad ;

    ServicePais servicePais;
    ServiceAlumno serviceAlumno ;

    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> lstNombresPais = new ArrayList<String>();

    //El tipo define si es REGISTRA o ACTUALIZA
    String tipo;

    //Se recibe el cliente seleccionado
    Alumno objAlumnoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_formulario);
        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);

        btnCrudRegistra = findViewById(R.id.btnCrudRegistrar);
        txtTitulo = findViewById(R.id.idCrudTituloAlumno);

        txtNombre = findViewById(R.id.txtCrudNombre);
        txtApellido = findViewById(R.id.txtCrudApellido);
        txtTelefono = findViewById(R.id.txtCrudTelefono);
        txtDni = findViewById(R.id.txtCrudDni);
        txtCorreo = findViewById(R.id.txtCrudCorreo);
        txtDireccion = findViewById(R.id.txtCrudDireccion);
        txtFechaNacimiento = findViewById(R.id.txtCrudFechaNacimiento);
        spnPais = findViewById(R.id.spnCrudAlumnoPais);
        spnModalidad = findViewById(R.id.spnCrudAlumnoModalidad);
        txtEstado=findViewById(R.id.txtCrudEstado);

        adaptadorModalidad = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);


        adaptadorPais = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lstNombresPais);
        spnPais.setAdapter(adaptadorPais);

        txtFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();
                new DatePickerDialog(
                        AlumnoCrudFormularioActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {
                                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));
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

        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        String titulo = (String) extras.get("var_titulo");

        cargaModalidad();
        cargaPais();

        if (tipo.equals("ACTUALIZA")){
            objAlumnoSeleccionado = (Alumno) extras.get("var_objeto");
            txtNombre.setText(objAlumnoSeleccionado.getNombres());
            txtApellido.setText(objAlumnoSeleccionado.getApellidos());
            txtTelefono.setText(objAlumnoSeleccionado.getTelefono());
            txtDni.setText(objAlumnoSeleccionado.getDni());
            txtCorreo.setText(objAlumnoSeleccionado.getCorreo());
            txtDireccion.setText(objAlumnoSeleccionado.getDireccion());
            txtFechaNacimiento.setText(objAlumnoSeleccionado.getFechaNacimiento());
            txtEstado.setText(String.valueOf(objAlumnoSeleccionado.getEstado()));
        }


        btnCrudRegistra.setText(tipo);
        txtTitulo.setText(titulo);

        btnCrudRegresar = findViewById(R.id.btnCrudRegresar);
        btnCrudRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        AlumnoCrudFormularioActivity.this,
                        AlumnoCrudListaActivity.class);
                startActivity(intent);
            }
        });

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtNombre.getText().toString();
                String ape = txtApellido.getText().toString();
                String tel = txtTelefono.getText().toString();
                String dni = txtDni.getText().toString();
                String correo = txtCorreo.getText().toString();
                String dir = txtDireccion.getText().toString();
                String fecNa = txtFechaNacimiento.getText().toString();
                Integer estado =Integer.parseInt(txtEstado.getText().toString());
                String pais = spnPais.getSelectedItem().toString();
                String modalidad = spnModalidad.getSelectedItem().toString();

                Modalidad objNewModalidad = new Modalidad();
                objNewModalidad.setIdModalidad(Integer.parseInt(modalidad.split(":")[0]));

                Pais objNewPais = new Pais();
                objNewPais.setIdPais(Integer.parseInt(pais.split(":")[0]));
                if (!nom.matches(ValidacionUtil.TEXTO)){
                    mensajeToast("El nombre es de 2 a 20 caracteres");
                    txtNombre.setError("El nombre es de 2 a 20 caracteres");
                }else if (!ape.matches(ValidacionUtil.TEXTO)){
                    mensajeToast("El apellido es de 2 a 20 caracteres");
                    txtApellido.setError("El apellido es de 2 a 20 caracteres");
                }else if (!tel.matches(ValidacionUtil.CELULAR)){
                    mensajeToast("El número celular es de 9 dígitos");
                    txtTelefono.setError("El número celular es de 9 dígitos");
                }else if (!dni.matches(ValidacionUtil.DNI)){
                    mensajeToast("El DNI es de 8 dígitos");
                    txtDni.setError("El DNI es de 8 dígitos");
                }else if (!correo.matches(ValidacionUtil.CORREO)){
                    mensajeToast("Formato incorrecto");
                    txtCorreo.setError("Formato incorrecto");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    mensajeToast("La dirección es de 3 a 30 caracteres");
                    txtDireccion.setError("La dirección es de 3 a 30 caracteres");
                }else if (!fecNa.matches(ValidacionUtil.FECHA)){
                    mensajeToast("La fecha de nacimiento es YYYY-MM-dd");
                    txtFechaNacimiento.setError("La fecha de nacimiento es YYYY-MM-dd");
                }else {
                    Alumno obj = new Alumno();
                    obj.setNombres(nom);
                    obj.setApellidos(ape);
                    obj.setTelefono(tel);
                    obj.setDni(dni);
                    obj.setCorreo(correo);
                    obj.setDireccion(dir);
                    obj.setFechaNacimiento(fecNa);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setEstado(estado);
                    obj.setPais(objNewPais);
                    obj.setModalidad(objNewModalidad);

                    if (tipo.equals("REGISTRA")) {
                        insertaAlumno(obj);
                    } else if (tipo.equals("ACTUALIZA")) {
                        Alumno objAux = (Alumno) extras.get("var_objeto");
                        obj.setIdAlumno(objAux.getIdAlumno());
                        actualizaAlumno(obj);
                    }
                }
            }
        });

    }

    public void insertaAlumno(Alumno objAlumno){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAlumno);
        // mensajeAlert(json);
        Call<Alumno> call = serviceAlumno.insertaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.isSuccessful()){
                    Alumno objSalida = response.body();
                    String msg="Se registró el Cliente con exito\n";
                    msg+="ID : "+ objSalida.getIdAlumno() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombres() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void actualizaAlumno(Alumno objAlumno){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objAlumno);
        // mensajeAlert(json);
        Call<Alumno> call = serviceAlumno.actualizaAlumno(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.isSuccessful()){
                    Alumno objSalida = response.body();
                    String msg="Se actualizó el Cliente con exito\n";
                    msg+="ID : "+ objSalida.getIdAlumno() +"\n";
                    msg+="NOMBRE : "+ objSalida.getNombres() ;
                    mensajeAlert(msg);
                }else {
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });

    }

    public void cargaModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listamodalidad();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if(response.isSuccessful()){
                    List<Modalidad> lst = response.body();
                    for(Modalidad obj:lst){
                        lstNombresModalidad.add(obj.getIdModalidad()+":"+obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                    if (tipo.equals("ACTUALIZA")){
                        Modalidad objModalidad = objAlumnoSeleccionado.getModalidad();
                        String aux2 = objModalidad.getIdModalidad()+":"+objModalidad.getDescripcion();
                        int pos = -1;
                        for(int i=0; i< lstNombresModalidad.size(); i++){
                            if (lstNombresModalidad.get(i).equals(aux2)){
                                pos = i;
                                break;
                            }
                        }
                        spnModalidad.setSelection(pos);
                    }

                }else{
                    mensajeAlert(""+response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
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
                        Pais objPais = objAlumnoSeleccionado.getPais();
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
    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }
}
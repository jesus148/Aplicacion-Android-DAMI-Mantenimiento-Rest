package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;




import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Modalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceAlumno;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceModalidad;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServicePais;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.FunctionUtil;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ValidacionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoRegistraActivity extends NewAppCompatActivity {

    EditText txtNombre, txtApellido, txtTelefono, txtdni, txtCorreo, txtDireccion, txtNacimiento;
    Spinner cboPais;
    Spinner cboModalidad;
    Button btnGuardar;
    ArrayList<String> listPais= new ArrayList<>();
    ArrayList<String> listModalidad = new ArrayList<>();
    ArrayAdapter<String> adaptadorP;
    ArrayAdapter<String> adaptadorM;
    ServiceAlumno serviceAlumno;
    List<Pais> lPais;
    List<Modalidad> lModalidad;
    ServicePais servicePais;
    ServiceModalidad serviceModalidad;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alumno_registra);

        adaptadorP = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.
                support_simple_spinner_dropdown_item, listPais);
        cboPais = findViewById(R.id.pais);
        cboPais.setAdapter(adaptadorP);

        adaptadorM = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.
                support_simple_spinner_dropdown_item, listModalidad);
        cboModalidad = findViewById(R.id.modalidad);
        cboModalidad.setAdapter(adaptadorM);

        txtNombre = findViewById(R.id.nombre);
        txtApellido = findViewById(R.id.apellidos);
        txtTelefono = findViewById(R.id.telefono);
        txtdni = findViewById(R.id.dni);
        txtCorreo = findViewById(R.id.correo);
        txtDireccion= findViewById(R.id.direccion);
        txtNacimiento= findViewById(R.id.fNacimiento);
        btnGuardar = findViewById(R.id.btnGuardar);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);

        listaPaises();

        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);

        listaModalidad();

        serviceAlumno = ConnectionRest.getConnection().create(ServiceAlumno.class);


        txtNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar= Calendar.getInstance();
                new DatePickerDialog(
                        AlumnoRegistraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {
                                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH,month);
                                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                                txtNacimiento.setText(dateFormat.format(myCalendar.getTime()));
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = txtNombre.getText().toString();
                String ape = txtApellido.getText().toString();
                String tel = txtTelefono.getText().toString();
                String dni = txtdni.getText().toString();
                String correo = txtCorreo.getText().toString();
                String dir = txtDireccion.getText().toString();
                String fnac = txtNacimiento.getText().toString();
                String pais = cboPais.getSelectedItem().toString();
                String idPais = pais.split(":")[0];
                String Mod = cboModalidad.getSelectedItem().toString();
                String idMod = Mod.split(":")[0];





                if (!nom.matches(ValidacionUtil.TEXTO)){
                    mensajeToast("El nombre es de 2 a 20 caracteres");
                    txtNombre.setError("El nombre es de 2 a 20 caracteres");
                }else if (!ape.matches(ValidacionUtil.TEXTO)){
                    mensajeToast("El apellido es de 2 a 20 caracteres");
                    txtApellido.setError("La dirección social es de 3 a 30 caracteres");
                }else if (!tel.matches(ValidacionUtil.CELULAR)){
                    mensajeToast("El número celular es de 9 dígitos");
                    txtTelefono.setError("El número celular es de 9 dígitos");
                }else if (!dni.matches(ValidacionUtil.DNI)){
                    mensajeToast("El DNI es de 8 dígitos");
                    txtdni.setError("El DNI es de 8 dígitos");
                }else if (!correo.matches(ValidacionUtil.CORREO)){
                    mensajeToast("Formato incorrecto");
                    txtCorreo.setError("Formato incorrecto");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    mensajeToast("La dirección es de 3 a 30 caracteres");
                    txtDireccion.setError("La dirección es de 3 a 30 caracteres");
                }else if (!fnac.matches(ValidacionUtil.FECHA)){
                    mensajeToast("La fecha de creación es YYYY-MM-dd");
                    txtNacimiento.setError("La fecha de creación es YYYY-MM-dd");
                }else{
                    Pais objPais = new Pais();
                    objPais.setIdPais(Integer.parseInt(idPais));

                    Modalidad objMod = new Modalidad();
                    objMod.setIdModalidad(Integer.parseInt(idMod));

                    Alumno obj = new Alumno();

                    obj.setNombres(nom);
                    obj.setApellidos(ape);
                    obj.setTelefono(tel);
                    obj.setDni(dni);
                    obj.setCorreo(correo);
                    obj.setDireccion(dir);
                    obj.setFechaNacimiento(fnac);
                    obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                    obj.setPais(objPais);
                    obj.setModalidad(objMod);
                    obj.setEstado(1);

                    registraAlumno(obj);
                }




            }
        });

    }

    public void listaPaises(){
        Call<List<Pais>> call = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    lPais = response.body();
                    for(Pais obj: lPais){
                        listPais.add(obj.getIdPais() + ":" + obj.getNombre());
                    }
                    adaptadorP.notifyDataSetChanged();
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void listaModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listamodalidad();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    lModalidad = response.body();
                    for(Modalidad obj: lModalidad){
                        listModalidad.add(obj.getIdModalidad() + ":" + obj.getDescripcion());
                    }
                    adaptadorM.notifyDataSetChanged();
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void registraAlumno(Alumno obj){
        Call<Alumno> call =  serviceAlumno.insertaAlumno(obj);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.isSuccessful()){
                    Alumno objSalida = response.body();
                    mensajeAlert("Registro exitoso" + objSalida.getIdAlumno());
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                mensajeAlert("Error" + t.getMessage());
            }
        });
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }
}
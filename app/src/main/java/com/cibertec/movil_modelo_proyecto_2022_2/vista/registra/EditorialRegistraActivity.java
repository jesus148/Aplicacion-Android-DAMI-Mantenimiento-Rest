package com.cibertec.movil_modelo_proyecto_2022_2.vista.registra;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Categoria;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Pais;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceCategoria;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
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


public class EditorialRegistraActivity extends NewAppCompatActivity {

    EditText txtNombre, txtDireccion, txtFecha, txtRuc;
    Spinner cboPais, cboCategoria;
    ArrayAdapter<String> adaptadorP;
    ArrayList<String> lstPaises = new ArrayList<String>();
    List<Pais> lstp;
    ArrayAdapter<String> adaptadorC;
    ArrayList<String> lstCategorias = new ArrayList<String>();
    List<Categoria> lstc;
    Button btnRegistEdit;
    ServicePais paisservice;
    ServiceCategoria categoriaservice;
    ServiceEditorial editorialservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_registra);

        adaptadorP = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.
                support_simple_spinner_dropdown_item, lstPaises);
        cboPais = findViewById(R.id.cboPaís);
        cboPais.setAdapter(adaptadorP);

        adaptadorC = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.
                support_simple_spinner_dropdown_item, lstCategorias);
        cboCategoria = findViewById(R.id.cboCategoria);
        cboCategoria.setAdapter(adaptadorC);

        txtNombre = findViewById(R.id.txtNombre);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtFecha = findViewById(R.id.txtFecha);
        txtRuc = findViewById(R.id.txtRuc);
        cboPais = findViewById(R.id.cboPaís);
        cboCategoria = findViewById(R.id.cboCategoria);
        btnRegistEdit = findViewById(R.id.btnRegistEdit);

        txtFecha.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Calendar myCalendar= Calendar.getInstance();

                new DatePickerDialog(

                        EditorialRegistraActivity.this,

                        new DatePickerDialog.OnDateSetListener() {

                            @Override

                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {

                                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", new Locale("es"));

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



        paisservice = ConnectionRest.getConnection().create(ServicePais.class);

        listaPaises();

        categoriaservice = ConnectionRest.getConnection().create(ServiceCategoria.class);

        listaCategoria();

        editorialservice = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnRegistEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String nom = txtNombre.getText().toString();
                String dir = txtDireccion.getText().toString();
                String ruc = txtRuc.getText().toString();
                String fec = txtFecha.getText().toString();

                if (!nom.matches(ValidacionUtil.TEXTO)){
                    //mensajeToast("La razón social es de 2 a 20 caracteres");
                    txtNombre.setError("la razon social es de 2 a 20 caracteres");
                }else if (!ruc.matches(ValidacionUtil.RUC)){
                    //mensajeToast("El RUC es 11 dígitos");
                    txtRuc.setError("El RUC es 11 dígitos");
                }else if (!dir.matches(ValidacionUtil.DIRECCION)){
                    // mensajeToast("La dirección social es de 3 a 30 caracteres");
                    txtDireccion.setError("La dirección social es de 3 a 30 caracteres");
                }else if (!fec.matches(ValidacionUtil.FECHA)){
                    // mensajeToast("La fecha de creación es YYYY-MM-dd");
                    txtFecha.setError("La fecha de creación es YYYY-MM-dd");
                }else{
                String pais = cboPais.getSelectedItem().toString();
                String idPais = pais.split(":")[0];
                String cat = cboCategoria.getSelectedItem().toString();
                String idCat = cat.split(":")[0];



                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));

                Categoria objCat = new Categoria();
                objCat.setIdCategoria(Integer.parseInt(idCat));

                Editorial obj = new Editorial();
                obj.setRazonSocial(nom);
                obj.setDireccion(dir);
                obj.setCategoria(objCat);
                obj.setPais(objPais);
                obj.setRuc(ruc);
                obj.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                obj.setFechaCreacion(fec);
                obj.setEstado(1);

                registraEditorial(obj);
            }}
        });

    }

    public void listaPaises(){
        Call<List<Pais>> call = paisservice.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    lstp = response.body();
                    for(Pais obj: lstp){
                        lstPaises.add(obj.getIdPais() + ":" + obj.getNombre());
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
    public void listaCategoria(){
        Call<List<Categoria>> call = categoriaservice.listaCategoriaDeEditorial();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    lstc = response.body();
                    for(Categoria obj: lstc){
                        lstCategorias.add(obj.getIdCategoria() + ":" + obj.getDescripcion());
                    }
                    adaptadorC.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });
    }
    public void registraEditorial(Editorial obj){
        Call<Editorial> call =  editorialservice.insertaEditorial(obj);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if(response.isSuccessful()){
                    Editorial objSalida = response.body();
                    mensajeAlert("Registro exitoso ID:" + objSalida.getIdEditorial());
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
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

}
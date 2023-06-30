package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;


import android.annotation.SuppressLint;
import android.content.Intent;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.EditorialCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceEditorial;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialCrudListaActivity extends NewAppCompatActivity {

    static List<Editorial> listaEditorial = new ArrayList<>();
    Button btnCrearPDF;
    Button btnCrudListar, btnCrudRegistra;

    //GridView
    GridView gridCrudEditorial;
    ArrayList<Editorial> data = new ArrayList<Editorial>();
    EditorialCrudAdapter adaptador;

    //service
    ServiceEditorial serviceEditorial;

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_lista);


        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions();
        }

        btnCrearPDF = findViewById(R.id.btnCrearPdf);

        listaEditorial.add(new Editorial(9999,"prueba1","comas", "1234567911", "2020-02-02", "2020-02-02", 1));

        btnCrearPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearPDF();
            }
        });

        btnCrudListar = findViewById(R.id.btnCrudListar);
        btnCrudRegistra = findViewById(R.id.btnCrudRegistra);
        gridCrudEditorial = findViewById(R.id.gridCrudEditorial);
        adaptador = new EditorialCrudAdapter(this,R.layout.activity_editorial_crud_item,data);
        gridCrudEditorial.setAdapter(adaptador);

        serviceEditorial =  ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "REGISTRA EDITORIAL");
                intent.putExtra("var_tipo", "REGISTRA");
                startActivity(intent);
            }
        });



        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();
            }
        });

        gridCrudEditorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Editorial objCliente = data.get(position);

                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_titulo", "ACTUALIZA Editorial");
                intent.putExtra("var_tipo", "ACTUALIZA");
                intent.putExtra("var_objeto", objCliente);
                startActivity(intent);

            }
        });


    }


    public void lista(){
        Call<List<Editorial>> call = serviceEditorial.listaEditorial();
        call.enqueue(new Callback<List<Editorial>>() {
            @Override
            public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                List<Editorial> lista =response.body();
                data.clear();
                data.addAll(lista);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Editorial>> call, Throwable t) {

            }
        });


    }

    public static void crearPDF() {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/EjemploITextPDF";

            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "usuarios.pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            Document documento = new Document();
            PdfWriter.getInstance(documento, fileOutputStream);

            documento.open();

            Paragraph titulo = new Paragraph(
                    "Lista de Usuarios \n\n\n",
                    FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE)
            );

            documento.add(titulo);

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Razón Social");
            tabla.addCell("Ruc");
            tabla.addCell("Dirección");

            for (int i = 0 ; i < listaEditorial.size() ; i++) {
                tabla.addCell(listaEditorial.get(i).getRazonSocial());
                tabla.addCell(listaEditorial.get(i).getRuc());
                tabla.addCell(listaEditorial.get(i).getDireccion());

            }

            documento.add(tabla);

            documento.close();


        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 200) {
            if(grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }


}
package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;


import java.util.List;

public class RevistaAdapter extends ArrayAdapter<Revista>  {




    //BASICAMENTE ESTE METODO AL LLAMARLO CREARA OBJETOS EN LA ESTRUCTURA DEL LAYOUT SECUNDARIO activity_item_porducto
    //luego con esa estructura ya cargada se lo pasamos al activity principal para q liste




    private Context context;
    private List<Revista> lista;









    public RevistaAdapter(@NonNull Context context, int resource, @NonNull List<Revista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }












    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        //obtiene permiso o entra en el layaout activity_item_producto secundario lo relaciona
        View row = inflater.inflate(R.layout.activity_item_revista, parent, false);





        //obtiene el id y guardarlo en un variable algo asi declarando
        TextView txtNombre = row.findViewById(R.id.txtNombreRevista);
        TextView txtidrevista = row.findViewById(R.id.txtidRevista);
        TextView txtFrecuencia = row.findViewById(R.id.txtfrecuencia);
        TextView txtFecCreacion = row.findViewById(R.id.txtfechaCreacion);
        TextView txtEstado = row.findViewById(R.id.txtEstado);






        //creamos la clase guia objProducto
        //lista tiene toda la data lo relacionamos con la clase guia para desembolsar toda la data producto q son varias filas depende
        //tambien convertimos a string y eso lo pasamos inputs del layaout activity_item_producto secundario
        //los atributos de la clase guia deben ser iguales
        Revista objRevista = lista.get(position);


        //convertimos a string pq los inputs son de string
        txtNombre.setText(String.valueOf(objRevista.getNombre()));
        txtidrevista.setText(String.valueOf(objRevista.getIdRevista()));
        txtFrecuencia.setText(String.valueOf(objRevista.getFrecuencia()));
        txtFecCreacion.setText(String.valueOf(objRevista.getFechaCreacion()));
        txtEstado.setText(String.valueOf(objRevista.getEstado()));








        return row;




    }











































}

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




//BASICAMENTE ESTE METODO AL LLAMARLO CREARA OBJETOS EN LA ESTRUCTURA DEL LAYOUT SECUNDARIO activity_revista_crud_item
//luego con esa estructura ya cargada se lo pasamos al activity principal para q liste(revistaCrudListaActivity)



//heredar de tu entidad rest padre  ArrayAdapter<revista>
//cuando crees esta clase pon  el cursor en public > create constructor > selecciona el segundo

public class RevistaCrudAdapter extends ArrayAdapter<Revista> {




    //BASICAMENTE ESTE METODO AL LLAMARLO CREARA OBJETOS EN LA ESTRUCTURA DEL LAYOUT SECUNDARIO activity_item_revista
    //luego con esa estructura ya cargada se lo pasamos al activity principal para q liste




    private Context context;
    private List<Revista> lista;//SE ALMACENA LOS OBJETOS CREADOS




    public RevistaCrudAdapter(@NonNull Context context, int resource,@NonNull List<Revista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;


    }



    //para crear esto metodo escirbe getView (y selecciona el int position,.......)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        //obtiene permiso o entra en el layaout activity_cliente_crud_item  lo relaciona para crear con la estructura
        View row = inflater.inflate(R.layout.activity_revista_crud_item, parent, false);











        //declarando y relacionando con los inputs.etc del activity_cliente_crud_item con sus id
        TextView txtId =row.findViewById(R.id.txtCrudRevistaItemId);
        TextView txtNombre = row.findViewById(R.id.txtCrudRevistaItemNombre);
        TextView txtFrecuencia = row.findViewById(R.id.txtCrudRevistaItemFrecuencia);
      //  TextView txtFecCre= row.findViewById(R.id.txtCruRevistaItemFecCre);  por el momento no ira x esapcio
        TextView txtModalidad = row.findViewById(R.id.txtCrudRevistaItemModalidad);
        TextView txtPais = row.findViewById(R.id.txtCrudRevistaItemPais);





        //creamos la clase guia objProducto
        //lista tiene toda la data lo relacionamos con la clase guia para desembolsar toda la data producto q son varias filas depende
        //tambien convertimos a string y eso lo pasamos inputs del layaout activity_item_producto secundario
        //los atributos de la clase guia deben ser iguales
        Revista objRevista = lista.get(position);





        //convertimos a string pq los inputs son de string
        txtId.setText(String.valueOf(objRevista.getIdRevista()));
        txtNombre.setText(objRevista.getNombre() );
        txtFrecuencia.setText(objRevista.getFrecuencia() );
     //   txtFecCre.setText(String.valueOf(objRevista.getFechaCreacion()));por el momento no ira x esapcio
        txtModalidad.setText(objRevista.getModalidad().getDescripcion());
        txtPais.setText(objRevista.getPais().getNombre());








        return row;











    }










}

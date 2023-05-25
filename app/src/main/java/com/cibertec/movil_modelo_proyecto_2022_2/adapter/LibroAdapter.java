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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import java.util.List;

public class LibroAdapter extends ArrayAdapter<Libro>  {

    private Context context;
    private List<Libro> lista;

    public LibroAdapter(@NonNull Context context, int resource, @NonNull List<Libro> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_libro_item, parent, false);

        TextView txtId = row.findViewById(R.id.txtIdLibro);
        TextView txtTitulo = row.findViewById(R.id.txtTituloLibro);
        TextView txtSerie = row.findViewById(R.id.txtSerieLibro);
        TextView txtAnio = row.findViewById(R.id.txtAnio);

        Libro objLibro = lista.get(position);
        txtId.setText(String.valueOf(objLibro.getIdLibro()));
        txtTitulo.setText(objLibro.getTitulo());
        txtAnio.setText(String.valueOf(objLibro.getAnio()));
        txtSerie.setText(objLibro.getSerie());


        return row;
    }
}

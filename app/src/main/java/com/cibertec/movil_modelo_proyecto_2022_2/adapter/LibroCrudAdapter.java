package com.cibertec.movil_modelo_proyecto_2022_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Libro;
import com.cibertec.movil_modelo_proyecto_2022_2.R;

import java.util.List;

public class LibroCrudAdapter extends ArrayAdapter<Libro> {
    private Context context;
    private List<Libro> lista;

    public LibroCrudAdapter(@NonNull Context context, int resource, @NonNull List<Libro> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_libro_crud_item, parent, false);

        TextView txtId = row.findViewById(R.id.txtCrudLibroItemId);
        TextView txtTitulo = row.findViewById(R.id.txtCrudLibroTitulo);
        TextView txtAnio = row.findViewById(R.id.txtCrudLibroAnio);
        TextView txtCategoria = row.findViewById(R.id.txtCrudLibroItemCategoria);
        TextView txtPais = row.findViewById(R.id.txtCrudLibroItemPais);

        Libro obj = lista.get(position);
        txtId.setText(String.valueOf(obj.getIdLibro()));
        txtTitulo.setText(String.valueOf(obj.getTitulo()));
        txtAnio.setText(String.valueOf(obj.getAnio()));
        txtCategoria.setText(String.valueOf(obj.getCategoria().getDescripcion()));
        txtPais.setText(String.valueOf(obj.getPais().getNombre()));

        return row;
    }
}

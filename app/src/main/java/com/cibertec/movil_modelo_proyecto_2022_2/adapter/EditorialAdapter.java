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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;

import java.util.List;

public class EditorialAdapter extends ArrayAdapter<Editorial>  {

    private Context context;
    private List<Editorial> lista;

    public EditorialAdapter(@NonNull Context context, int resource, @NonNull List<Editorial> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_editorial_item, parent, false);
        TextView txtId =row.findViewById(R.id.txtIdEditorial);
        TextView txtTitulo= row.findViewById(R.id.txtTituloEditorial);
        TextView txtDireccion = row.findViewById(R.id.txtDireccion);
        TextView txtPais = row.findViewById(R.id.txtPais);

        Editorial objEditorial = lista.get(position);
        txtId.setText(String.valueOf(objEditorial.getIdEditorial()));
        txtTitulo.setText(objEditorial.getRazonSocial());
        txtPais.setText(objEditorial.getPais().getNombre());
        txtDireccion.setText(objEditorial.getDireccion());
        return row;
    }
}

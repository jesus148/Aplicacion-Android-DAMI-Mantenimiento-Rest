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

public class EditorialCrudAdapter extends ArrayAdapter<Editorial> {

    private Context context;
    private List<Editorial> lista;

    public EditorialCrudAdapter(@NonNull Context context, int resource, @NonNull List<Editorial> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_editorial_crud_item, parent, false);

        TextView txtId =row.findViewById(R.id.txtCrudEditorialItemId);
        TextView txtNombre = row.findViewById(R.id.txtCrudEditorialItemNombre);
        TextView txtDireccion = row.findViewById(R.id.txtCrudEditorialItemDireccion);
        TextView txtRuc = row.findViewById(R.id.txtCrudEditorialItemRuc);
        TextView txtFechaCrea = row.findViewById(R.id.txtCrudEditorialItemFechaCrea);
        TextView txtPais = row.findViewById(R.id.txtCrudEditorialItemPais);
        TextView txtCategoria = row.findViewById(R.id.txtCrudEditorialItemCategoria);

        Editorial obj = lista.get(position);
        txtId.setText(String.valueOf(obj.getIdEditorial()));
        txtNombre.setText(obj.getRazonSocial());
        txtDireccion.setText(obj.getDireccion());
        txtRuc.setText(obj.getRuc());
        txtFechaCrea.setText(obj.getFechaCreacion());
        txtPais.setText(obj.getPais().getNombre());
        txtCategoria.setText(obj.getCategoria().getDescripcion());




        return row;
    }
}

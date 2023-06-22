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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;

import java.util.List;

public class ProveedorCrudAdapter extends ArrayAdapter<Proveedor> {

    private Context context;
    private List<Proveedor> lista;

    public ProveedorCrudAdapter(@NonNull Context context, int resource, @NonNull List<Proveedor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_proveedor_crud_item, parent, false);

        TextView txtId = row.findViewById(R.id.txtCrudProveedorItemId);
        TextView txtRazonSocial = row.findViewById(R.id.txtCrudProveedorItemRazonSocial);
        TextView txtRUC = row.findViewById(R.id.txtCrudProveedorItemRUC);
        TextView txtDireccion = row.findViewById(R.id.txtCrudProveedorItemDireccion);
        TextView txtCelular = row.findViewById(R.id.txtCrudProveedorItemCelular);

        Proveedor obj = lista.get(position);

        txtId.setText(String.valueOf(obj.getIdProveedor()));

        txtRazonSocial.setText(obj.getRazonsocial());
        txtRUC.setText(obj.getRuc());
        txtDireccion.setText(obj.getDireccion());
        txtCelular.setText(obj.getCelular());

        return row;
    }

}

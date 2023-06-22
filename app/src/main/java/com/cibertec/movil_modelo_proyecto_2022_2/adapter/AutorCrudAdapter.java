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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Editorial;

import java.util.List;

public class AutorCrudAdapter extends ArrayAdapter<Autor> {

    private Context context;
    private List<Autor> lista;

    public AutorCrudAdapter(@NonNull Context context, int resource, @NonNull List<Autor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_autor_crud_item, parent, false);

        TextView txtId =row.findViewById(R.id.txtCrudAutorItemId);
        TextView txtNombre = row.findViewById(R.id.txtCrudAutorItemNombre);
        TextView txtCorreo = row.findViewById(R.id.txtCrudAutorItemCorreo);
        TextView txtTelefono = row.findViewById(R.id.txtCrudAutorItemTelefono);
        TextView txtGrado = row.findViewById(R.id.txtCrudAutorItemGrado);
        TextView txtPais = row.findViewById(R.id.txtCrudAutorItemPais);

        Autor obj = lista.get(position);
        txtId.setText(String.valueOf(obj.getIdAutor()));
        txtNombre.setText(obj.getNombres());
        txtCorreo.setText(obj.getCorreo());
        txtTelefono.setText(obj.getTelefono());
        txtGrado.setText(obj.getGrado().getDescripcion());
        txtPais.setText(obj.getPais().getNombre());




        return row;
    }
}

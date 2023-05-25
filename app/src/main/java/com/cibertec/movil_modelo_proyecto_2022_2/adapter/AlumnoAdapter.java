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
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Alumno;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Autor;


import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno>  {

    private Context context;
    private List<Alumno> lista;

    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_alumno_item, parent, false);
        TextView txtId =row.findViewById(R.id.txtIdAlumno);
        TextView txtNombre = row.findViewById(R.id.txtNombre);
        TextView txtApellido= row.findViewById(R.id.txtApellido);
        TextView txtCorreo = row.findViewById(R.id.txtCorreo);


        Alumno obj = lista.get(position);
        txtId.setText(String.valueOf(obj.getIdAlumno()));
        txtNombre.setText(obj.getNombres());
        txtApellido.setText(obj.getApellidos());
        txtCorreo.setText(obj.getCorreo());

        return row;
    }
}

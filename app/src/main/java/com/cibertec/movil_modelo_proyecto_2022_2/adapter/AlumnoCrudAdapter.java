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

import java.util.List;

public class AlumnoCrudAdapter extends ArrayAdapter<Alumno> {

    private Context context;
    private List<Alumno> lista;

    public AlumnoCrudAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_alumno_crud_item, parent, false);

        TextView txtId =row.findViewById(R.id.txtCrudAlumnoItemId);
        TextView txtNombre = row.findViewById(R.id.txtCrudAlumnoItemNombre);
        TextView txtApellido = row.findViewById(R.id.txtCrudAlumnoItemApellido);
        TextView txtTel = row.findViewById(R.id.txtCrudAlumnoItemTelefono);
        TextView txtDni = row.findViewById(R.id.txtCrudAlumnoItemDni);

        TextView txtCorreo = row.findViewById(R.id.txtCrudAlumnoItemCorreo);
        TextView txtDireccion = row.findViewById(R.id.txtCrudAlumnoItemDireccion);
       TextView txtFecNac = row.findViewById(R.id.txtCrudAlumnoItemFecNac);
        TextView txtPais = row.findViewById(R.id.txtCrudAlumnoItemPais);
        TextView txtModalidad = row.findViewById(R.id.txtCrudAlumnoItemModalidad);

        Alumno obj = lista.get(position);
        txtId.setText(String.valueOf(obj.getIdAlumno()));
        txtNombre.setText(obj.getNombres());
        txtApellido.setText(obj.getApellidos());
        txtTel.setText(obj.getTelefono());
        txtDni.setText(obj.getDni());
        txtCorreo.setText(obj.getCorreo());
        txtDireccion.setText(obj.getDireccion());
        txtFecNac.setText(obj.getFechaNacimiento());
        txtPais.setText(obj.getPais().getNombre());
        txtModalidad.setText(obj.getModalidad().getDescripcion());
        return row;
    }
}

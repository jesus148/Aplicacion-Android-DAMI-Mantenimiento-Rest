package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;








//ESTE DetalleRevistaActivity ES UNA ACTIVITY(PRINCIPAL SECUNDARIO)
//ESTE DetalleProductoActivity LO QUE HARA ES CUANDO SELECCIONOMES UN CARD DEL ACTIVITY_ITEM_revista.JVA(SECUNDARIO)
//MOSTRARA UNA LAYOUT QUE ES EL activity_detalle_revista QUE LE PERTENECE A ESTE ACTIVYT MOSTRANDO SOLO INFO DE ESa revista O CARD SELECCIONADO


//OJO ALL ACTIVITY YA SEA AL CREAR O CREA UNO NUEVO DESPUESDE CREAR EL PROYECTO
//SIEMPRE TIENE ACTIVYTY PRINCIAPL QUE ES ESTO Y SU ACTIVITY_LAYAOUT(SE CREA AUTOMATICAMENTE) QUE ES EL
// ACTIVITY_DETALLE_revista.XML





//al crear esto , en el paquete manifest > androidManifest.xml  tambien se agregara
//si quieres eliminar ahi debes tambien borrarlo

public class RevistaConsultaDetalleActivity extends NewAppCompatActivity {











    //INCIALIZANDO LAS VARIABLES CON SU TIPO DE DATO OSEA PARA LOS COMPONENTES
    TextView txNombreTitulo, txtIdRevista,txtFrecuencia,txtfeCreacion;
    TextView txtEstado, txtdescripcionModal, txtNombrePais;

    Button btnDetalleRegresar;
























    //esto se crea al carga la aplicacion de esa vista y controlador o el activity como quieres decirle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_consulta_detalle);








        //DECLARANDO ESAS VARIBALES CON SUS INPUTS DEL LAYOUT  activity_detalle_REVISTA ATRAVES DE SU ID
        txNombreTitulo = findViewById(R.id.txNombreTitulo);
        txtIdRevista = findViewById(R.id.txtIdRevista);
        txtFrecuencia = findViewById(R.id.txtFrecuencia);
        txtfeCreacion = findViewById(R.id.txtfeCreacion);
        txtEstado = findViewById(R.id.txtEstado);
        txtdescripcionModal = findViewById(R.id.txtdescripcionModal);
        txtNombrePais = findViewById(R.id.txtNombrePais);

        btnDetalleRegresar = findViewById(R.id.btnDetalleRegresar);











        //CUANDO SELECCIONEMOS UN CARD DE OBJETO PRODUCTO EN EL ACTIVIYTMAIN(PRINCIPAL)
        //ACA LO RECIBIREMOS



        //aca le pedimos los valores enviados desde el mainavtivity.java
        //le pedimos el objeto producto sus valores
        Bundle extras = getIntent().getExtras();
        //recordar VAR_OBJETO deve estar escrito igual en el  ACTIVIYTMAIN(PRINCIPAL)
        //desembolsamos junto con la clase guia
        Revista objRevista = (Revista) extras.get("var_objeto");












        //CON ESE OBJETO CARGADO PQ SOLO ES 1
        //PINTAMOS LOS INPUTS TEXT DE NUESTRO LAYOUT Q  ES EL  ACTIVITY_DETALLE_PRODUCTO.XML
        //OSEA LE AGREGAMOS LOS VALORES JUNTO CON LA CLASE GUIA ADEMAS
        //los atributos de la clase guia deben ser iguales

        txNombreTitulo.setText(objRevista.getNombre());

        txtIdRevista.setText( "código de Revista : " + objRevista.getIdRevista());

        txtFrecuencia.setText( "frecuencia de Revista : " +     objRevista.getFrecuencia());

        txtfeCreacion.setText( "creación de la Revista : " + objRevista.getFechaCreacion());

        txtEstado.setText("estado de la Revista : " +   objRevista.getEstado());

        txtdescripcionModal.setText( "tipo de modalidad : " +   objRevista.getModalidad().getDescripcion());

        txtNombrePais.setText(" Pais de la Revista  : " +    objRevista.getPais().getNombre());
























        //cuando demos click en el botonm regresar en el layaout activyty_detalle_producto.xml
        //es basicamente para retroceder y regresar al activiyt_main:xml osea el layaout princiapl en la parte del diseño o vista

        //otra forma de crear este metodo es  btnRegresar.setOnClickListener(new
        //y ahi elige View.OnClickListener()
        btnDetalleRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DetalleProductoActivity.this : donde estoy
                // MainActivity.class : adonde quiero o quiero regresar : osea al mainactivyt_java(principal)
                Intent intent = new Intent( RevistaConsultaDetalleActivity.this, RevistaConsultaActivity.class);
                startActivity(intent);
            }
        });






























    }


























}
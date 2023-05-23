package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.RevistaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaConsultaActivity extends NewAppCompatActivity {








    //RECORDAR ESTE ES EL ACTIVIY PRINCIPAL



    //  EXPLICACION DEL EJERCISIO
    //ES IGUAL AL DE LA SEMANA 7 INICIO >
    //basicamente traemos la data de un rest consumimos esa data y los mostramos en un listvview del layaout principal , usando otro layout
    //secundario esto lo usaremos como un estructura guia ahi , ADEMAS cuando demos click en cada card o tarjeta de cada onbjeto producto
    //aparecera otra ventana o layaout en donde solo me mostrara lo + importante y solamente de ese producto seleccioanado













    //INICALIZANDO LAS VARIABLES

    //Boton
    Button btnLista;

    //ORDEN PARA TRAER LA DARA PRODUCTOS
    //ListView lo combimaos pq ahora usamos el gridview
    GridView gridRevista;  //3



    //ACA SE ALMACENA ALL LA DATA DE revista AL HACER CLCIK EN BOTEN DEL LAYOAUT PRINCIAPAL
    ArrayList<Revista> data = new ArrayList<Revista>(); //2



    RevistaAdapter adpatador;  //1



    //Servicio
    ServiceRevista serviceRevista;









    //ALL ESTO SE CREA AL CARGAR LA PAGINA PERO NO SALE LA DATA revista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revista_consulta);





        //declarando las variables con los inputs en el layou principal
        btnLista = findViewById(R.id.btnLista);

        gridRevista = findViewById(R.id.gridRevista);




        //CON ESA DATA PRODUCTO LE DAMOS LA ESTRUCTURA OSEA LLAMAMOS AL ADPATADOR PARA DARLE LA ETRUCTURA QUE TIENE EL LAYOU SECUNDARIO
        //OSEA CREAMOS OBJETOS REVISTA CON ESTRUCTURA DEL LAYAOUT SECUNDARIO
        adpatador = new RevistaAdapter(this, R.layout.activity_item_revista, data);
        gridRevista.setAdapter(adpatador);















        gridRevista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //DE ESE DATA LE MANDAMOS LA POSITION DEL OBJETO O CARD SELECCIONADO , MEDIANTE LA CLASE GUIA Y ALMACENAMOS AHI objProducto
                Revista objRevista  =  data.get(position);


                //MainActivity : donde estoy
                //DetalleProductoActivity: adonde me quiero ir o se enviarle le objeto prodcuto
                Intent intent = new Intent(RevistaConsultaActivity.this, RevistaConsultaDetalleActivity.class);


                //OJO IMPORTANTE SALDRA UN ERROR AQUI:
                //dirigite a la clase guia o tabla del rest PADRE O EL REST PADRE CLASE GUIA EN ESTE CASO ES PRODUCTO
                //public class Producto  implements Serializable {}: AUMENTALE implements Serializable
                //convierte de un objeto a un medio fisico es para eso y con eso se va el error
                //ademas no olvidar con las subclases hijas o los rest hijos  que estan dentro de esa clase padre
                //en este caso Rating tambien le pondremos eso no olvida su libreria

                //a ese DetalleProductoActivity activyty le pasamos el objeto seleccionado
                //var_objeto : debe estar escrito igual en el DetalleProdutoActivity


                intent.putExtra("var_objeto", objRevista);
                startActivity(intent);
            }
        });











































        //CONECTANDO CON EL SERVICE ESPECIFICICO O EL REST HIJO
        //en esa varible declaramos el servicioRevista
        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);







        //CUANDO DAS CLIKC EN EL BOTON RECIEN LISTARA EL Revista
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaRevistas();
            }
        });




















    }


















    //METODO PARA LISTAR REVISTA
    public void listaRevistas(){
        //metodo del service del rest solo devuelve la data simple sin estructura
        //recordar poner o declarar en la variable el servicio
        Call<List<Revista>> call = serviceRevista.listarevista();
        call.enqueue(new Callback<List<Revista>>() {
            //all es ok
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                if (response.isSuccessful()){
                    //SOLO DEVUELVE LA DATA PRODUCTO , OSEA ALL PERO SIN ESTRCUTURA
                    List<Revista> lista = response.body();//aca se pone all la data producto
                    data.clear(); //limpiamos la data
                    data.addAll(lista);//agrega la data productos a esa data(array) ver + arriba
                    adpatador.notifyDataSetChanged();
                }
            }

            //hay error
            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {

            }
        });
    }



































}
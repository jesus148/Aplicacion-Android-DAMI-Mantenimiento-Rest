package com.cibertec.movil_modelo_proyecto_2022_2.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.RevistaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.RevistaCrudAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//NewAppCompatActivity    : este acitvity debe heredar de ahi solo escirbir (control  + espacio y importa autop)
public class RevistaCrudListaActivity extends NewAppCompatActivity {




    //INICILIZANDO LAS VARIABLES
    Button btnCrudListar, btnCrudRegistra;




    //PASOS  ORDEN DE COMO MOSTRAR EN EL GRIDVIW LA LISTA DE CLIENTES
    //GridView
    GridView gridCrudRevista;  // 3
    ArrayList<Revista> data = new ArrayList<Revista>(); // 2
    RevistaCrudAdapter adaptador;  //1  relacionamos con el adaptador






    //service   llamamos  o declaramos al service para obtener los metodos
    ServiceRevista serviceRevista;















    //ESTO SE CREA CUANDO CARGA LA APLICACION O ESTA VISTA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_lista);




        //DECLARANDO LAS VARIABLES
        btnCrudListar = findViewById(R.id.btnCrudListar);
        btnCrudRegistra = findViewById(R.id.btnCrudRegistra);





        //========================================================================================================
        //LISTA ALL REVISTAS
        //CON ESA DATA cliente LE DAMOS LA ESTRUCTURA OSEA LLAMAMOS AL ADPATADOR PARA DARLE LA ETRUCTURA QUE TIENE EL LAYOU S
        //OSEA CREAMOS OBJETOS Revistas CON ESTRUCTURA DEL LAYAOUT
        //pasando el adaptador al gridview
        //COLOCANDO LA DATA EN EL GRIDVIEW
        gridCrudRevista = findViewById(R.id.gridCrudRevista);
        adaptador = new RevistaCrudAdapter(this,R.layout.activity_revista_crud_item,data);
        gridCrudRevista.setAdapter(adaptador);




        //llamando al service obteniendo la connecion
        serviceRevista =  ConnectionRest.getConnection().create(ServiceRevista.class);











        //PARA REGISTRAR
        //========================================================================================================
        //MUSTRA EL ACTIVITY activity_revista_crud_formulario
        //EVENTO CUANDO SE DE CLICK EN EL BOTOM CON EL ID btnCrudListar
        //DE ACA LLAMRA AL OTRO ACTIVITY QUE ES activity_revista_crud_formulario Y LO MOSTRARA
        btnCrudRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RevistaCrudListaActivity.this, //DONDE ESTOY
                        RevistaCrudFormularioActivity.class); //LO LLEVARA AL

                //CUANDO DE CLCIK EN EL BOTON btnCrudRegistra CON ESTO MODIFICAREMOS EL TITULO Y EL BOTON DE ESE LAYAOUT revistaCrudFormularioActivity
                //osea daremos click al boton y se cambiara el valor de ese componentes del tituylo y su boton
                //var_titulo : variable debe estar escritop igual en el revistaCrudFormularioActivity
                //"REGISTRA CLIENTE" : es el valor a agregar
                //recordar seguir esta estrucutura
                intent.putExtra("var_titulo", "REGISTRA REVISTA");//TITULO
                intent.putExtra("var_tipo", "REGISTRA");  //BOTON
                startActivity(intent);  //esto va al final ojo
            }
        });




















        //CUANDO DEMOS CLICK EN LISTAR LISTARA ALL REVISTAS EN EL GRIDVIEW

        //========================================================================================================
        btnCrudListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista();  //llama al metodo + abajo
            }
        });










        //========================================================================================================

        //PARA ACTUALIZAR  osea con esa data de ese objeto pintara el otro layaout
        //CUANDO DE CLICK EN UN ITEM o seleccion 1 Revista de esa fila DEL GRIDVIEW CON ALL LA LISTA DE Revistas
        //ME LLEVA AL LAYAOUT RevistaCrudFormularioActivity YA SEA PARA ATUALIZAR O REGISTRAR

        //escibir este metodo + facil                           (elgir el primero adpaterview.onitem)
        //  gridCrudRevista.setOnItemClickListener( new  adapter
        gridCrudRevista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //aca se almacena el objeto seleccionado de all esa lista de Revistas
                Revista objRevista = data.get(position);

                Intent intent = new Intent(
                        RevistaCrudListaActivity.this,//donde estamos
                        RevistaCrudFormularioActivity.class);// donde quiero ir




                //cuando seleccion un item de un 1 objeto de esta cambiar los valores de ese layaout su titulo y boton
                //osea daremos click al al item y se cambiara el valor de ese componentes del tituylo y su boton
                //var_titulo : all las variables debe estar escritop igual en el revistaCrudFormularioActivity
                //"ATUALIZA REVISTA" : es el valor a agregar
                //recordar seguir esta estrucutura
                //enviando los parametros
                //esto tambien debemos ponerlo en el metodo para mostrar al registrar el activity_revista_crud_formulario + arriba
                intent.putExtra("var_titulo", "ACTUALIZA REVISTA");
                intent.putExtra("var_tipo", "ACTUALIZA");


                //aca le enviamos el objeto seleccionado con all esa data lista para mostrar en  activity_revista_crud_formulario
                intent.putExtra("var_objeto", objRevista);

                startActivity(intent);//esto va al final ojo

            }
        });








        //esto servira para cuando actualizae automaticamente liste al regregsar a este acitvity o cuando inicie la app
        lista();




























    }
















    //METOD0O VOID NO DEVUELVE NADA POR ESO ESTAN AFUERA DEL ONCREATE







//=============================================================================================================
    //METODO PARA LISTAR pero sin la estructura

    public void lista(){
        //metodo del service del rest solo devuelve la data simple sin estructura
        //recordar poner o declarar en la variable el servicio
        Call<List<Revista>> call = serviceRevista.listarevista();
        call.enqueue(new Callback<List<Revista>>() {
            //all es ok
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                //SOLO DEVUELVE LA DATA PRODUCTO , OSEA ALL PERO SIN ESTRCUTURA
                List<Revista> lista =response.body();
                data.clear();//limpiamos la data
                data.addAll(lista);//agrega la data clientes a esa data(array) ver + arriba
                adaptador.notifyDataSetChanged();
            }

            //hay error
            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {

            }
        });
    }

















}
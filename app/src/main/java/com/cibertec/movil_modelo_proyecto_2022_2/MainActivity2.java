package com.cibertec.movil_modelo_proyecto_2022_2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        // Agregar animaciones
        Animation animacion1 = AnimationUtils.loadAnimation( this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(  this, R. anim.desplazamiento_abajo);

        TextView codeliaTextView = findViewById(R.id.TextLogo);
        ImageView logoImageView = findViewById(R.id. LogoImage);

        codeliaTextView.setAnimation(animacion2);
        logoImageView.setAnimation(animacion1);
        new Handler(). postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( MainActivity2. this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } ,3000);
    }
}
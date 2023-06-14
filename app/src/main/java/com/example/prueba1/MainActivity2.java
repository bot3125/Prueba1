package com.example.prueba1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private TextView tv1, tv2;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        String nombreUsuario = getIntent().getStringExtra("dato");
        tv1.setText("Hola " + nombreUsuario);

        // Obtener la instancia de SharedPreferences
        sharedPreferences = getSharedPreferences("MiSharedPreferences", MODE_PRIVATE);

        // Obtener los soles guardados previamente
        int soles = sharedPreferences.getInt("soles", 0);

        // Establecer el texto en el TextView de los soles
        tv2.setText("S/ " + soles);
    }

    public void Regreso(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void Cuentas(View view) {
        Intent e = new Intent(this, MainActivity3.class);
        startActivity(e);
    }


    public void agregarSoles(View view) {
        // Incrementar el valor de los soles
        int soles = sharedPreferences.getInt("soles", 0);
        soles += 1;

        // Guardar el valor actualizado de los soles en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("soles", soles);
        editor.apply();

        // Actualizar el texto en el TextView de los soles
        tv2.setText("S/ " + soles);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Obtener los soles guardados previamente
        int soles = sharedPreferences.getInt("soles", 0);

        // Establecer el texto en el TextView de los soles
        tv2.setText("S/ " + soles);
    }

    public void Register(View view) {
        Intent e = new Intent(this, MainActivity4.class);
        startActivity(e);
    }
}

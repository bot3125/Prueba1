package com.example.prueba1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText et1;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);

        // Obtener la instancia de SharedPreferences
        sharedPreferences = getSharedPreferences("MiSharedPreferences", MODE_PRIVATE);

        // Verificar si el nombre de usuario ya está registrado
        String nombreUsuario = sharedPreferences.getString("nombreUsuario", "");

        if (!nombreUsuario.isEmpty()) {
            // Si el nombre de usuario ya está registrado, iniciar directamente MainActivity2
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("dato", nombreUsuario);
            startActivity(intent);
            finish(); // Finalizar MainActivity para que no se pueda volver atrás
        }
    }

    public void enviar(View view) {
        String nombreUsuario = et1.getText().toString();

        // Guardar el nombre del usuario en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombreUsuario", nombreUsuario);
        editor.apply();

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("dato", nombreUsuario);
        startActivity(intent);
        finish(); // Finalizar MainActivity para que no se pueda volver atrás
    }
}

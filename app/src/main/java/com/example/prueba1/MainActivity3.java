package com.example.prueba1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity3 extends AppCompatActivity {
    private TextView tv11;
    private ImageView imageView;
    private EditText etCantidad, etDescripcion;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper databaseHelper;

    public class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mi_basedatos.db";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Crear la estructura de la base de datos
            String createTableQuery = "CREATE TABLE registros (id INTEGER PRIMARY KEY, cantidad INTEGER, descripcion TEXT)";
            db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Actualizar la estructura de la base de datos, si es necesario
            if (oldVersion < 2) {
                // Ejecutar las actualizaciones necesarias para la versión 2
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv11 = findViewById(R.id.tv11);
        imageView = findViewById(R.id.imageView);
        etCantidad = findViewById(R.id.etCantidad);
        etDescripcion = findViewById(R.id.etDescripcion);

        sharedPreferences = getSharedPreferences("MiSharedPreferences", MODE_PRIVATE);

        int soles = sharedPreferences.getInt("soles", 0);

        tv11.setText("S/ " + soles);

        updateTextViewColorAndImageView(soles);

        databaseHelper = new DatabaseHelper(this);
    }

    public void seleccion(View view) {
        int viewId = view.getId();
        String mensaje = "";

        if (viewId == R.id.a) {
            mensaje = "Fue un buen día";
            incrementarSoles(50);
        } else if (viewId == R.id.b) {
            mensaje = "Se puede mejorar";
            incrementarSoles(5);
        } else if (viewId == R.id.c) {
            mensaje = "Necesitas hacer cambios";
            restarSoles(100);
        }

        if (!mensaje.isEmpty()) {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    public void gastar(View view) {
        String cantidadString = etCantidad.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        if (!cantidadString.isEmpty() && !descripcion.isEmpty()) {
            int cantidad = Integer.parseInt(cantidadString);
            restarSoles(cantidad);
            int soles = sharedPreferences.getInt("soles", 0);
            updateTextViewColorAndImageView(soles);
            guardarDatosEnBaseDeDatos(cantidad, descripcion);
            etCantidad.setText("");
            etDescripcion.setText("");
        } else {
            Toast.makeText(this, "Por favor, ingresa la cantidad y la descripción.", Toast.LENGTH_SHORT).show();
        }
    }

    private void incrementarSoles(int cantidad) {
        int soles = sharedPreferences.getInt("soles", 0);
        soles += cantidad;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("soles", soles);
        editor.apply();
        tv11.setText("S/ " + soles);
        updateTextViewColorAndImageView(soles);
    }

    private void restarSoles(int cantidad) {
        int soles = sharedPreferences.getInt("soles", 0);
        soles -= cantidad;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("soles", soles);
        editor.apply();
        tv11.setText("S/ " + soles);
        updateTextViewColorAndImageView(soles);
    }

    private void updateTextViewColorAndImageView(int soles) {
        if (soles >= 500) {
            imageView.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (soles < 500 && soles >= 200) {
            imageView.setImageResource(android.R.drawable.presence_busy);
        } else if (soles < 200) {
            imageView.setImageResource(android.R.drawable.ic_dialog_alert);
        }





        if (soles >= 100) {
            tv11.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else if (soles >= 50) {
            tv11.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            tv11.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    private void guardarDatosEnBaseDeDatos(int cantidad, String descripcion) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cantidad", cantidad);
        values.put("descripcion", descripcion);

        long result = db.insert("registros", null, values);

        if (result == -1) {
            Toast.makeText(this, "Error al guardar los datos en la base de datos.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Datos guardados en la base de datos.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}

package com.example.prueba1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        listView = findViewById(R.id.listView);

        // Obtener una instancia de la base de datos en modo lectura
        SQLiteDatabase db = openOrCreateDatabase("nombre_de_tu_base_de_datos", MODE_PRIVATE, null);

        // Realizar la consulta a la base de datos
        Cursor cursor = obtenerRegistros(db);

        // Verificar si hay datos en el cursor
        if (cursor != null && cursor.moveToFirst()) {
            // Definir los nombres de las columnas en el cursor
            String[] fromColumns = new String[]{"cantidad", "descripcion"};

            // Definir los IDs de los TextView en el layout de cada fila del ListView
            int[] toViews = new int[]{android.R.id.text1, android.R.id.text2};

            // Crear un adaptador para los datos del cursor
            CursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                    cursor, fromColumns, toViews, 0);

            // Configurar el adaptador en el ListView
            listView.setAdapter(adapter);
        } else {
            // No hay datos en la base de datos
            Toast.makeText(this, "No hay datos para mostrar", Toast.LENGTH_SHORT).show();
        }

        // Cerrar el cursor y la base de datos
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    public Cursor obtenerRegistros(SQLiteDatabase db) {
        // Realizar la consulta a la base de datos
        return db.query("registros", null, null, null, null, null, null);
    }
}

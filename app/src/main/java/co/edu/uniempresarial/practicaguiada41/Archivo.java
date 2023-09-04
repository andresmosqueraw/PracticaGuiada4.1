package co.edu.uniempresarial.practicaguiada41;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

import android.content.pm.PackageManager;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class Archivo {
    private final Context context;
    private final Activity activity;

    public Archivo(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void guardarArchivo(String nombreArchivo, String informacion) {
        File directorio = obtenerDirectorio();

        if (directorio == null) {
            return;
        }

        escribirArchivo(directorio, nombreArchivo, informacion);
    }

    private File obtenerDirectorio() {
        if (!tienePermisoEscrituraSD()) {
            solicitarPermisoEscritura();
            return null;
        }

        return crearODeterminarDirectorio();
    }

    private void escribirArchivo(File directorio, String nombreArchivo, String informacion) {
        try {
            File file = new File(directorio, nombreArchivo);
            FileWriter writer = new FileWriter(file);
            writer.append(informacion);
            writer.flush();
            writer.close();

            Toast.makeText(context, "Archivo guardado en: " + directorio, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.i("Archivo", e.getMessage());
            Toast.makeText(context, "Error al guardar el archivo", Toast.LENGTH_LONG).show();
        }
    }

    private File crearODeterminarDirectorio() {
        File directorio = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P)
                ? new File(Environment.getExternalStorageDirectory(), "ArchivoUE")
                : new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "ArchivoUE");

        if (!directorio.exists()) {
            directorio.mkdir();
        }

        return directorio;
    }

    private void solicitarPermisoEscritura() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private boolean tienePermisoEscrituraSD() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}

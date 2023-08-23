package co.edu.uniempresarial.practicaguiada41;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;

    // Version Android
    private TextView versionAndroid;
    private int versionSDK;
    // Bateria
    private ProgressBar pbLevelBaterry;
    private TextView tvLevelBaterry;
    IntentFilter batteryFilter;
    // Camara
    CameraManager cameraManager;
    String cameraId;
    private Button btnOnLight;
    private Button btnOffLight;
    // Archivo
    private EditText nameFile;
    private Archivo archivo;
    // Conexion
    private TextView tvConexion;
    ConnectivityManager conexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        begin();
        batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        // Pendiente: registerReceiver(new BatteryReceiver(this), batteryFilter);

        btnOnLight.setOnClickListener(this::onLight);
        btnOffLight.setOnClickListener(this::offLight);
    }

    private void begin(){
        this.context = getApplicationContext();
        this.activity = this;
        this.versionAndroid = findViewById(R.id.tvVersionAndroid);
        this.pbLevelBaterry = findViewById(R.id.pbLevelBattery);
        this.tvLevelBaterry = findViewById(R.id.tvLevelBatteryLB);
        this.nameFile = findViewById(R.id.etNameFile);
        this.tvConexion = findViewById(R.id.tvConexion);
        this.btnOnLight = findViewById(R.id.btnOn);
        this.btnOffLight = findViewById(R.id.btnOff);
    }

    // SO version
    @Override
    protected void onResume() {
        super.onResume();
        String versionSO = Build.VERSION.RELEASE;
        versionSDK = Build.VERSION.SDK_INT;
        versionAndroid.setText("Version Android: " + versionSO + " /SDK: " + versionSDK);
    }

    // Linterna
    private void onLight(View view){
        try {
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void offLight(View view){
        try {
            cameraManager.setTorchMode(cameraId, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package org.izv.archivosdevinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.izv.archivosdevinos.data.Vino;
import org.izv.archivosdevinos.util.Csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName() + "xyzyx";

    private static EditText et_mainId;
    private static ArrayList<Vino> allVinos;
    private Context context;
    private LinearLayout linearLayout;
    String fileName = "archivo.csv";
    Button bt_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        context = this;
        et_mainId = findViewById(R.id.et_idp);
        linearLayout = findViewById(R.id.linearLayout);
        allVinos = new ArrayList<>();
        vinoWriter();

        bt_add = findViewById(R.id.bt_add);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSecondaryActivity();
            }
        });

        Button btEdit;
        btEdit = findViewById(R.id.bt_edit);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPicker();
            }
        });
    }

    private void openSecondaryActivity() {
        Intent intent = new Intent(this, SecondaryActivity.class);
        startActivity(intent);
    }

    public static String[] getFileLines(File file, String fileName){
        File f = new File(file, fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            String cache = "";
            while ((linea = br.readLine()) != null) {
                cache += linea + "%";
            }
            br.close();
            return cache.split("%");
        } catch (IOException e) {
            Log.v(TAG, e.toString());
        }
        return null;
    }

    private void vinoWriter(){
        String[] vinos = getFileLines(getExternalFilesDir(null), fileName);
        if (vinos != null){
            for (String linea : vinos) {
                Vino vino = Csv.getVino(linea);
                allVinos.add(vino);
                ETVD etv = new ETVD(this, vino);
                linearLayout.addView(etv);
            }
        }
    }

    public static EditText getMainId(){
        return et_mainId;
    }

    public static ArrayList<Vino> getAllVinos() {
        return allVinos;
    }

    private void idPicker(){
        for (int i = 0; i < allVinos.size(); i++) {
            if (!et_mainId.getText().toString().isEmpty()) {
                if (allVinos.get(i).getId() == Long.parseLong(et_mainId.getText().toString())) {
                    ETVD etv = new ETVD(this, allVinos.get(i));
                    Intent intencion = etv.createIntent(context, ThirdActivity.class);
                    context.startActivity(intencion);
                }
            }
        }
    }

    /*
    public static String resultTv(){
        String resultado = et_mainId.getText().toString();
        return resultado;
    }

    public static int idResult(){
        int res = Integer.parseInt(et_mainId.getText().toString());
        return res;
    }
    public void onResume() {
        super.onResume();
        et_id.setText(readFile(getFilesDir(), fileName));
    }
     */

}
package org.izv.archivosdevinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.izv.archivosdevinos.data.Vino;
import org.izv.archivosdevinos.util.Csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SecondaryActivity extends AppCompatActivity {

    Button btSecAdd;
    private EditText et_id, et_nombre, et_bodega, et_color, et_origen, et_graduacion, et_fecha;
    //private TextView tvText;
    private EditText[] editText = new EditText[7];
    String fileName = "archivo.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        initialize();
    }

    private void initialize() {
        et_id = findViewById(R.id.et_id_add);
        et_nombre = findViewById(R.id.et_nombre_add);
        et_bodega = findViewById(R.id.et_bodega_add);
        et_color = findViewById(R.id.et_color_add);
        et_origen = findViewById(R.id.et_origen_add);
        et_graduacion = findViewById(R.id.et_graduacion_add);
        et_fecha = findViewById(R.id.et_fecha_add);
        //tvText = findViewById(R.id.tv_scV);

        editText[0] = et_id;
        editText[1] = et_nombre;
        editText[2] = et_bodega;
        editText[3] = et_color;
        editText[4] = et_origen;
        editText[5] = et_graduacion;
        editText[6] = et_fecha;

        btSecAdd = findViewById(R.id.bt_add_add);

        btSecAdd.setOnClickListener((View v) -> {
            newVino();
        });
    }

    private Vino vinoPicker(){
        Vino v = new Vino();
        try {
            v.setId(Long.parseLong(editText[0].getText().toString().trim()));
        } catch(NumberFormatException e){
            e.getMessage();
        }

        v.setNombre(editText[1].getText().toString().trim());
        v.setBodega(editText[2].getText().toString().trim());
        v.setColor(editText[3].getText().toString().trim());
        v.setOrigen(editText[4].getText().toString().trim());

        try {
            v.setGraduacion(Double.parseDouble(editText[5].getText().toString()));
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        try {
            v.setFecha(Integer.parseInt(editText[6].getText().toString()));
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        return v;
    }

    private boolean TextEditer(){
        boolean relleno = true;
        for (EditText campo: editText) {
            if(campo.getText().toString().isEmpty()){
                relleno = false;
            }
        }
        return relleno;
    }

    private boolean vinoExist(Vino vino){
        boolean existe = false;
        for (Vino v: MainActivity.getAllVinos()) {
            if(v.getId() == vino.getId()){
                existe = true;
            }
        }
        return existe;
    }

    public static boolean writeLine(File file, String filename, String line){
        File f = new File(file, filename);
        FileWriter fw;
        try {
            fw = new FileWriter(f, true);
            fw.write(line);
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void newVino(){
        if(TextEditer()) {
            Vino vino = vinoPicker();
            if (!vinoExist(vino)) {
                String lineaCSV = Csv.getCsv(vino);
                writeLine(getExternalFilesDir(null), fileName, lineaCSV);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
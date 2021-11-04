package org.izv.archivosdevinos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.izv.archivosdevinos.data.Vino;
import org.izv.archivosdevinos.util.Csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ThirdActivity extends AppCompatActivity {

    private EditText et_id, et_nombre, et_bodega, et_color, et_origen, et_graduacion, et_fecha;
    private EditText[] edittextA = new EditText[7];
    private Vino vino;
    Button btSecEdit, bt_delete;
    String fileName = "archivo.csv";
    //int idPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initialize();
    }

    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        vino = bundle.getParcelable("vino");

        et_id = findViewById(R.id.et_id_add);
        et_id.setText(String.valueOf(vino.getId())); et_id.setEnabled(false);
        et_nombre = findViewById(R.id.et_nombre_add);
        et_nombre.setText(vino.getNombre());
        et_bodega = findViewById(R.id.et_bodega_add);
        et_bodega.setText(vino.getBodega());
        et_color = findViewById(R.id.et_color_add);
        et_color.setText(vino.getColor());
        et_origen = findViewById(R.id.et_origen_add);
        et_origen.setText(vino.getOrigen());
        et_graduacion = findViewById(R.id.et_graduacion_add);
        et_graduacion.setText(String.valueOf(vino.getGraduacion()));
        et_fecha = findViewById(R.id.et_fecha_add);
        et_fecha.setText(String.valueOf(vino.getFecha()));

        edittextA[0] = et_id;
        edittextA[1] = et_nombre;
        edittextA[2] = et_bodega;
        edittextA[3] = et_color;
        edittextA[4] = et_origen;
        edittextA[5] = et_graduacion;
        edittextA[6] = et_fecha;

        btSecEdit = findViewById(R.id.btSecEdit);
        btSecEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        bt_delete = findViewById(R.id.btDelete);
        bt_delete.setOnClickListener(view -> {
                            deleteL();
                            Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                            startActivity(intent);
        });
        /*btDelete = findViewById(R.id.btDelete);
        btDelete.setOnClickListener(view -> {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(view.getContext());
            builder.setTitle("DELETING...");
            builder.setMessage(R.string.dialog)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteL();
                            Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
            builder.setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
         */

    }


    private void edit() {
        if (getEditVino()) {
            if (writeIn()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean getEditVino() {
        boolean resultado = true;
        String[] atributos = new String[edittextA.length];
        for (int i = 0; i < atributos.length; i++) {
            atributos[i] = edittextA[i].getText().toString();
        }
        try {
            vino.setId(Long.parseLong(atributos[0].trim()));
        } catch(NumberFormatException e){
            resultado = false;
        }

        vino.setNombre(atributos[1].trim());
        vino.setBodega(atributos[2].trim());
        vino.setColor(atributos[3].trim());
        vino.setOrigen(atributos[4].trim());

        try {
            vino.setGraduacion(Double.parseDouble(atributos[5].trim()));
        } catch (NumberFormatException e) {
            resultado = false;
        }
        try {
            vino.setFecha(Integer.parseInt(atributos[6].trim()));
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }

    public static boolean deleteLine(File file, String fileName, String id) {
        File f = new File(file, fileName);
        File f2 = new File(file, "temp.tmp");
        String string[];
        String tmp;
        try {
            FileWriter fw = new FileWriter(f2);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                string = linea.split(";");
                if (!id.equals(string[0])) {
                    tmp = linea;
                    fw.write(tmp);
                    fw.write("\n");
                    fw.flush();
                }
            }
            fw.close();
            br.close();

            f.delete();
            f2.renameTo(f);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean deleteL() {
        return deleteLine(getExternalFilesDir(null), fileName, Long.toString(vino.getId()));
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

    private boolean writeL() {
        return writeLine(getExternalFilesDir(null), fileName, Csv.getCsv(vino));
    }

    private boolean writeIn() {
        boolean borrado = deleteL();
        if (borrado){
            boolean escrito = writeL();
            if (escrito){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /*
    public void picker(File file, String fileName, String id, int iD){
        et_Id.setText(resultTv());
        String[] cadenas = readFile(getFilesDir(), fileName).split(";");
        String raw = readFile(getExternalFilesDir(null), fileName);
        for (String strTemp:cadenas) {
            Vino v = Csv.getVino(strTemp);
            if(id == iD){
                Vino v1 = v;
                et_nombre.setText(cadenas[1].trim());
                et_bodega.setText(cadenas[2].trim());
                et_color.setText(cadenas[3].trim());
                et_origen.setText(cadenas[4].trim());
                et_graduacion.setText(cadenas[5].trim());
                et_fecha.setText(cadenas[6].trim());
            }
        }
     */

}
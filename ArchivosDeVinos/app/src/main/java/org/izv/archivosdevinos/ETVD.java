package org.izv.archivosdevinos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import org.izv.archivosdevinos.data.Vino;

public class ETVD extends androidx.appcompat.widget.AppCompatTextView implements View.OnClickListener {
    Vino vino;

    public ETVD(@NonNull Context context, Vino vino) {
        super(context);
        this.vino = vino;
        this.setText(vino.getId() + ", "+ vino.getNombre() + ", " + vino.getBodega() + ", " + vino.getColor() + ", " + vino.getFecha());
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ponerIdEditTextIdEditarMain(vino.getId());
        vinoEditer();
    }

    public Intent createIntent(Context contexto, Class clase){
        Intent intent = new Intent(contexto, clase);
        Bundle bundle = new Bundle();
        bundle.putParcelable("vino", this.vino);
        intent.putExtras(bundle);
        return intent;
    }

    private void vinoEditer(){
        this.getContext().startActivity(createIntent(this.getContext(), ThirdActivity.class));
    }

    private void ponerIdEditTextIdEditarMain(long id){
        MainActivity.getMainId().setText(String.valueOf(id));
    }
}

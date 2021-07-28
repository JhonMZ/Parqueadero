package com.parqueadero.parqueadero.vista;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parqueadero.parqueadero.R;
import com.parqueadero.parqueadero.modelo.Registro;

import java.util.Date;
import java.util.List;

public class Consulta extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView listView;
    EditText edtPlaca;
    EditText edtFechaI;
    EditText edtFechaF;
    TextView txvTotal;
    CheckBox chbCarros;
    CheckBox chbMotos;
    Button btnBuscar;
    Button btnLimpiar;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        edtFechaI = (EditText) findViewById(R.id.EdtFechaInicial);
        edtFechaF = (EditText) findViewById(R.id.EdtFechaFinal);
        edtPlaca = (EditText) findViewById(R.id.EdtPlacaC);
        listView = (ListView) findViewById(R.id.ltvConsulta);
        txvTotal = (TextView) findViewById(R.id.txvTotal);
        chbCarros = (CheckBox) findViewById(R.id.chbCarrosS);
        chbMotos = (CheckBox) findViewById(R.id.chbMotosS);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);

        edtFechaI.setOnClickListener(this);
        edtFechaF.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        position = 0;

        /*List<Registro> registros = Registro.listAll(Registro.class);
        AdaptadorRegistros adaptadorRegistros = new AdaptadorRegistros(this,Registro.listAll(Registro.class));
        listView.setAdapter(adaptadorRegistros);*/
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent().setClass(this,Menu.class);
        startActivity(intent);
        finish();
    }

    public void click(View view) {
        try {
            List<Registro> registros = null;
            if(isEmpty(edtPlaca)) {
                if(isEmpty(edtFechaI) && isEmpty(edtFechaF)) {
                    registros = Registro.listAll(Registro.class);
                }else {
                    registros = Registro.find(Registro.class, "date(fecha_ingreso) between date('"
                            +edtFechaI.getText().toString().toUpperCase()+"') and date('"
                            +edtFechaF.getText().toString().toUpperCase()+"')" );
                }
            }else{
                if(validarCampos()) {
                    if(isEmpty(edtFechaI) && isEmpty(edtFechaF)) {
                        registros = Registro.find(Registro.class, "placa = ?", edtPlaca.getText().toString().toUpperCase());
                    }else{
                        registros = Registro.find(Registro.class, "placa = '"+edtPlaca.getText().toString().toUpperCase()
                                +"' and date(fecha_ingreso) between date('"
                                +edtFechaI.getText().toString().toUpperCase()+"') and date('"
                                +edtFechaF.getText().toString().toUpperCase()+"')" );
                    }
                }
            }
            if(registros != null) {
                if(registros.size()>0) {
                    AdaptadorRegistros adaptadorRegistros = new AdaptadorRegistros(this, registros);
                    listView.setAdapter(adaptadorRegistros);
                    double total = 0;
                    for (Registro registro:registros ){
                        total += Double.parseDouble(registro.getTotal()+"");
                    }
                    txvTotal.setText(Html.fromHtml("Total:</br>"+total));
                }else{
                    listView.setAdapter(null);
                    Toast.makeText(this, "No se encontro ningun registro", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "No se encontro ningun registro", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }

    public boolean validarCampos() {
        if (!edtPlaca.getText().toString().matches("([a-zA-Z]{3}[0-9]{3}$)|([a-zA-Z]{3}[0-9]{2}[a-zA-Z]{1}$)|([a-zA-Z]{3}[0-9]{2}$)")) {
            Toast.makeText(this, "Placa incorrecta", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean isEmpty(EditText edtText) {
        if (edtText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    @Override
    public void onClick(View v) {
        DatePickerFragment newFragment;
        switch (v.getId()) {
            case R.id.EdtFechaInicial:
                 newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate;
                        String s_month = "";
                        String s_day = "";
                        if(day>9)
                            s_day = ""+day;
                        else
                            s_day = "0"+day;
                        if((month+1)>9)
                            s_month = ""+(month+1);
                        else
                            s_month = "0"+(month+1);

                        selectedDate = year + "-" + s_month + "-" + s_day;

                        edtFechaI.setText(selectedDate);
                    }
                });
                newFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.EdtFechaFinal:
                 newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate;
                        String s_month = "";
                        String s_day = "";
                        if(day>9)
                            s_day = ""+day;
                        else
                            s_day = "0"+day;
                        if((month+1)>9)
                            s_month = ""+(month+1);
                        else
                            s_month = "0"+(month+1);

                        selectedDate = year + "-" + s_month + "-" + s_day;
                        edtFechaF.setText(selectedDate);
                    }
                });
                newFragment.show(getFragmentManager(), "datePicker");
                break;
        }
    }

    public void click_limpiar(View view) {
        edtPlaca.setText("");
        edtFechaF.setText("");
        edtFechaI.setText("");
        txvTotal.setText("Total:");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar");
        builder.setMessage("Desea eliminar el registro?");
        builder.setCancelable(true);
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Registro r = (Registro)listView.getItemAtPosition(position);
                Registro.delete(r);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        builder.show();
        return true;
    }

    public void sinSalida(View view) {
        int op = 0;
        switch (view.getId()){
            case R.id.chbCarrosS:
                if(chbCarros.isChecked()) {
                    if (chbMotos.isChecked()) op = 3;
                    else op = 1;
                }else{
                    if (chbMotos.isChecked()) op = 2;
                }
                break;
            case  R.id.chbMotosS:
                if(chbMotos.isChecked()) {
                    if (chbCarros.isChecked()) op = 3;
                    else op = 2;
                }else{
                    if (chbCarros.isChecked()) op = 1;
                }
                break;
        }
        if(op!=0) {
            consultarSinSalida(op);
            edtPlaca.setEnabled(false);
            edtFechaI.setEnabled(false);
            edtFechaF.setEnabled(false);
            btnLimpiar.setEnabled(false);
            btnBuscar.setEnabled(false);
            btnLimpiar.performClick();
        }else {
            listView.setAdapter(null);
            edtPlaca.setEnabled(true);
            edtFechaI.setEnabled(true);
            edtFechaF.setEnabled(true);
            btnBuscar.setEnabled(true);
            btnLimpiar.setEnabled(true);
        }
    }

    public void consultarSinSalida(int op){
        List<Registro> registros = null;
        switch (op){
            case 1:
                //Consulta carros sin salida
                registros = Registro.find(Registro.class, " tipo = ? and fecha_Salida = ?", "CARRO","");
                break;
            case 2:
                registros = Registro.find(Registro.class, " tipo = ? and fecha_Salida = ?", "MOTO","");
                break;
            case 3:
                registros = Registro.find(Registro.class, " fecha_Salida = ?", "");
                break;
            default:
                break;
        }
        if(registros!=null) {
            AdaptadorRegistros adaptadorRegistros = new AdaptadorRegistros(this, registros);
            listView.setAdapter(adaptadorRegistros);
        }else {
            Toast.makeText(this,"No hay registros sin salida",Toast.LENGTH_LONG);
        }
    }
}

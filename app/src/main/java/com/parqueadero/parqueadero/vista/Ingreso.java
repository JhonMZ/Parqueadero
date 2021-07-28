package com.parqueadero.parqueadero.vista;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parqueadero.parqueadero.R;
import com.parqueadero.parqueadero.modelo.Registro;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Ingreso extends AppCompatActivity {

    private Button btnIngresar;
    private TextInputEditText placa;
    private TextInputEditText nombre;
    private TextInputEditText identificacion;
    private TextInputEditText telefono;
    private TextInputEditText observacion;
    private RadioButton rdbCarro;
    private RadioButton rdbMoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        placa = (TextInputEditText) findViewById(R.id.EdtPlaca);
        nombre = (TextInputEditText) findViewById(R.id.EdtNombre);
        identificacion = (TextInputEditText) findViewById(R.id.EdtIdentificacion);
        telefono = (TextInputEditText) findViewById(R.id.EdtTelefono);
        observacion = (TextInputEditText) findViewById(R.id.EdtObservaciones);
        rdbCarro = (RadioButton) findViewById(R.id.rdbCarro);
        rdbMoto = (RadioButton) findViewById(R.id.rdbMoto);

        placa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isEmpty(placa)){
                    List<Registro> registros = Registro.find(Registro.class, "placa = ? order by ID desc", placa.getText().toString().toUpperCase());
                    if(registros!=null) {
                        if (registros.size() > 0) {
                            Registro reg = registros.get(0);
                            identificacion.setText(reg.getIdentificacion());
                            nombre.setText(reg.getNombre());
                            telefono.setText(reg.getTelefono());
                            if(reg.getTipo().equals("MOTO"))
                                rdbMoto.setChecked(true);
                            else if(reg.getTipo().equals("CARRO"))
                                rdbCarro.setChecked(true);
                        }else{
                            identificacion.setText("");
                            nombre.setText("");
                            telefono.setText("");
                        }
                    }else{
                        identificacion.setText("");
                        nombre.setText("");
                        telefono.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void btnIngresar(View view) {
        if(validarCampos()){
            List<Registro> registros = Registro.find(Registro.class, "placa = ? and fecha_Salida = ?", placa.getText().toString().toUpperCase(),"");
            if(registros!=null){
                if(registros.size()==0) {

                    String tipo = "";
                    if(rdbMoto.isChecked())
                        tipo = "MOTO";
                    else
                        tipo = "CARRO";
                    Date fecha = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Registro objRegistro = new Registro(placa.getText().toString().toUpperCase(),
                            identificacion.getText().toString(),
                            nombre.getText().toString(),
                            telefono.getText().toString(),
                            observacion.getText().toString(),
                            format.format(fecha),"",0,tipo);
                    objRegistro.save();
                    Toast.makeText(this,"Se ingreso correctamente",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Ya existe un registro sin salida",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean validarCampos(){
        if(isEmpty(placa)) {
            Toast.makeText(this,"Campo Placa Vacio",Toast.LENGTH_LONG).show();
            return false;
        }else{
            if(!placa.getText().toString().matches("([a-zA-Z]{3}[0-9]{3}$)|([a-zA-Z]{3}[0-9]{2}[a-zA-Z]{1}$)|([a-zA-Z]{3}[0-9]{2}$)")){
                Toast.makeText(this,"Placa incorrecta",Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(!rdbCarro.isChecked() && !rdbMoto.isChecked()){
            Toast.makeText(this,"Seleccione un tipo de vehículo",Toast.LENGTH_LONG).show();
            return false;
        }

        if(isEmpty(nombre)){
            Toast.makeText(this,"Campo Nombre Vacio",Toast.LENGTH_LONG).show();
            return false;
        }

        if(isEmpty(identificacion)){
            Toast.makeText(this,"Campo Identificacion Vacio",Toast.LENGTH_LONG).show();
            return false;
        }

        if(isEmpty(telefono)) {
            Toast.makeText(this, "Campo Telefono Vacio", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean isEmpty(TextInputEditText edtText) {
        if (edtText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent().setClass(this,Menu.class);
        startActivity(intent);
        finish();
    }
}

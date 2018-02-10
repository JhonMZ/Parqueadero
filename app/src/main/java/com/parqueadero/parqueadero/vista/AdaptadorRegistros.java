package com.parqueadero.parqueadero.vista;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parqueadero.parqueadero.R;
import com.parqueadero.parqueadero.modelo.Registro;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by JhonMZ on 06/02/2018.
 */

public class AdaptadorRegistros extends ArrayAdapter<Registro> {
    List<Registro> registros;
    Context context;
    public AdaptadorRegistros(@NonNull Context context, @NonNull List<Registro> registros) {
        super(context, R.layout.layout_items, registros);
        this.registros = registros;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.layout_items, null);

        TextView txvPlaca = (TextView)item.findViewById(R.id.txvPlaca);
        TextView txvIdentificacion = (TextView)item.findViewById(R.id.txvIdentificacion);
        TextView txvNombre = (TextView)item.findViewById(R.id.txvNombre);
        TextView txvTelefono = (TextView)item.findViewById(R.id.txvTelefono);
        TextView txvFechaIngreso = (TextView)item.findViewById(R.id.txvFechaIngreso);
        TextView txvFechaSalida = (TextView)item.findViewById(R.id.txvFechaSalida);
        TextView txvObservacion = (TextView)item.findViewById(R.id.txvObservacion);

        txvPlaca.setText("Placa:"+registros.get(position).getPlaca());
        txvIdentificacion.setText("Identificacion:"+registros.get(position).getIdentificacion());
        txvNombre.setText("Nombre:"+registros.get(position).getNombre());
        txvTelefono.setText("Telefono:"+registros.get(position).getTelefono());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        txvFechaIngreso.setText("Fecha Ingreso: "+ format.format(registros.get(position).getFechaIngreso()));
        txvFechaSalida.setText("Fecha Salida: "+ format.format(registros.get(position).getFechaSalida()));
        txvObservacion.setText("Observacion:"+registros.get(position).getObservacion());

        return(item);
    }
}

package com.ubercom.root.ubercomv1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ubercom.root.ubercomv1.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends ArrayAdapter<Categoria> {

    private Context context;
    private List<Categoria> categorias;

    public CategoriaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Categoria> objects) {
        super(context, resource, objects);
        this.context = context;
        this.categorias = objects;
    }


    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_categoria, parent, false);

        TextView txtCategoriaId = (TextView) rowView.findViewById(R.id.txtCategoriaId);
        TextView txtCategoriaNome = (TextView) rowView.findViewById(R.id.txtCategoriaNome);

        txtCategoriaId.setText(String.format("ID: %d", categorias.get(pos).getId()));
        txtCategoriaNome.setText(String.format("Categoria : %s", categorias.get(pos).getNome()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Categoria Form
                Intent intent = new Intent(context, CategoriaActivity.class);
                intent.putExtra("categoria_id", String.valueOf(categorias.get(pos).getId()));
                intent.putExtra("categoria_nome", categorias.get(pos).getNome());
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
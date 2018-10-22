package com.ubercom.root.ubercomv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ubercom.root.ubercomv1.model.Categoria;
import com.ubercom.root.ubercomv1.service.APIUtils;
import com.ubercom.root.ubercomv1.service.CategoriaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    //public static final String API_URL = "https://ubercom.herokuapp.com/";
    public static final String API_URL = "http://10.0.2.2/";

    Button btnAddCategoria;
    Button btnGetCategoriasList;
    ListView listView;

    CategoriaService categoriaService;
    List<Categoria> list = new ArrayList<Categoria>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("UberCom");

        btnAddCategoria = (Button) findViewById(R.id.btnAddCategoria);
        btnGetCategoriasList = (Button) findViewById(R.id.btnGetCategoriasList);
        listView = (ListView) findViewById(R.id.listView);
        categoriaService = APIUtils.getCategoriaService();

        btnGetCategoriasList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get nas categorias
                getCategoriasList();
            }
        });

        btnAddCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
                intent.putExtra("nome_categoria", "");
                startActivity(intent);
            }
        });
    }
        public void getCategoriasList(){
            Call<List<Categoria>> call = categoriaService.getCategorias();
            call.enqueue(new Callback<List<Categoria>>() {
                @Override
                public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        listView.setAdapter(new CategoriaAdapter(MainActivity.this, R.layout.list_categoria, list));
                    }
                }

                @Override
                public void onFailure(Call<List<Categoria>> call, Throwable t) {
                    Log.e("ERROR: ", t.getMessage());
                }
            });

    }
}

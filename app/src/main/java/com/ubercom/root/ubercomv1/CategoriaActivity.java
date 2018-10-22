package com.ubercom.root.ubercomv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ubercom.root.ubercomv1.model.Categoria;
import com.ubercom.root.ubercomv1.service.APIUtils;
import com.ubercom.root.ubercomv1.service.CategoriaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaActivity extends Activity {

    CategoriaService categoriaService;
    EditText edtCId;
    EditText edtCategoriaNome;
    Button btnSalvar;
    Button btnDeletar;
    TextView txtCId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        setTitle("Categorias");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        txtCId = (TextView) findViewById(R.id.txtCId);
        edtCId = (EditText) findViewById(R.id.edtCId);
        edtCategoriaNome = (EditText) findViewById(R.id.edtCategoriaNome);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);


        categoriaService = APIUtils.getCategoriaService();

        Bundle extras = getIntent().getExtras();
        final String categoriaId = extras.getString("categoria_id");
        String categoriaNome = extras.getString("categoria_nome");

        edtCId.setText(categoriaId);
        edtCategoriaNome.setText(categoriaNome);

        if(categoriaId != null && categoriaId.trim().length() > 0 ){
            edtCId.setFocusable(false);
        } else {
            txtCId.setVisibility(View.INVISIBLE);
            edtCId.setVisibility(View.INVISIBLE);
            btnDeletar.setVisibility(View.INVISIBLE);
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Categoria cat = new Categoria();
                cat.setName(edtCategoriaNome.getText().toString());
                if(categoriaId != null && categoriaId.trim().length() > 0 ){
                    //update categoria
                    updateCategoria(Integer.parseInt(categoriaId), cat);
                } else {
                    //add categoria
                    addCategoria(cat);

                }
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategoria(Integer.parseInt(categoriaId));

                Intent intent = new Intent(CategoriaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addCategoria(Categoria cat){
        Call<Categoria> call = categoriaService.addCategoria(cat);
        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                Log.e("Mensagem : : : ", response.toString());
                if(response.isSuccessful()){
                    Toast.makeText(CategoriaActivity.this, "Categoria criada com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categoria> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateCategoria(int id, Categoria u){
        Call<Categoria> call = categoriaService.updateCategoria(id, u);
        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CategoriaActivity.this, "Categoria atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categoria> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteCategoria(int id){
        Call<Categoria> call = categoriaService.deleteCategoria(id);
        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CategoriaActivity.this, "Categoria deletada com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categoria> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

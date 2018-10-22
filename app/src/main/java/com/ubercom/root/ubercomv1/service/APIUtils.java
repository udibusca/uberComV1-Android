package com.ubercom.root.ubercomv1.service;

import com.ubercom.root.ubercomv1.model.Categoria;

import static com.ubercom.root.ubercomv1.MainActivity.API_URL;

public class APIUtils {

    private APIUtils(){
    }

    public static CategoriaService getCategoriaService(){
        return RetrofitClient.getClient(API_URL)
                .create(CategoriaService.class);
    }

}

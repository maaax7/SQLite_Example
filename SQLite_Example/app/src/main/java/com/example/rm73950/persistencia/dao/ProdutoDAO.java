package com.example.rm73950.persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.AndroidRuntimeException;
import android.widget.Toast;

import com.example.rm73950.persistencia.bean.Produto;
import com.example.rm73950.persistencia.util.PersistenciaHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rm73950 on 11/08/2016.
 */
public class ProdutoDAO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Loja.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase conn;
    private Cursor cursor;

    public ProdutoDAO(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE TBL_PRODUTO (" +
                "ID INTEGER PRIMARY KEY, " +
                "NM_PRODUTO TEXT" +
                "VL_PRECO REAL" +
                "DS_PRODUTO TEXT)";
        db.execSQL(sql);

        conn = getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String sql = "DROP TABLE IF EXISTS TBL_PRODUTO";
        db.execSQL(sql);
        onCreate(db);
    }

    //metodo que insere o produto na Base
    public void inserir(Produto produto){
        //Classe que encapsula os valores que serao passados como parametros
        ContentValues cv = pegaDados(produto);
        //INSERT INTO TBL_PRODUTO (NM_PRODUTO, VL_PRECO, DS_PRODUTO) VALUES (...,...,...)
        //null forma de tratamento de valores nulos
        conn.insert("TBL_PRODUTO", null, cv);
    }

    //metodo que atualiza o produto na Base
    public void atualizar(Produto produto){
        //classe que encapsula os valores que serao passados como parametros
        ContentValues cv = pegaDados(produto);
        String[] id = {produto.getId().toString().trim()};
        // UPDATE TBL_PRODUTO SET NM_PRODUTO = ?, VL_PRECO = ?, DS_PRODUTO = ? WHERE ID = ?
        // P3 Where P4 Parametro
        conn.update("TBL_PRODUTO", cv, "ID=?", id);
    }

    //metodo que exclui o produto na Base
    public void excluir(String strId){
        String[] id = {strId};
        //DELETE FROM TBL_PRODUTO WHERE ID=?
        //P2 Where P3 Parametro
        conn.delete("TBL_PRODUTO", "ID=?", id);
    }

    public List<Produto> buscaProdutos(){
        List<Produto> produtos = new ArrayList<>();
        //Select * from TBL_PRODUTO
        cursor = conn.query("TBL_PRODUTO", null, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            Produto produto = new Produto();
            //getColumnIndex retorna o indice e o getInt retorna o valor da coluna ID no cursor.
            produto.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            produto.setDescricao(cursor.getString(cursor.getColumnIndex("DS_PRODUTO")));
            produto.setNome(cursor.getString(cursor.getColumnIndex("NM_PRODUTO")));
            produto.setPreco(cursor.getDouble(cursor.getColumnIndex("VL_PRECO")));
            produtos.add(produto);
        }
        return produtos;
    }

    //Método que pega os valores dos EditTexts e encapsula no ContentValues
    private ContentValues pegaDados(Produto prod){
        ContentValues cv = new ContentValues();
        cv.put("NM_PRODUTO", prod.getNome());
        cv.put("VL_PRECO", prod.getPreco());
        cv.put("DS_PRODUTO", prod.getDescricao());
        return cv;
    }
}

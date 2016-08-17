package com.example.rm73950.persistencia.util;

import android.text.TextUtils;
import android.widget.EditText;

import com.example.rm73950.persistencia.ProdutoActivity;
import com.example.rm73950.persistencia.bean.Produto;
import com.example.rm73950.sqlite_example.R;

/**
 * Created by rm73950 on 10/08/2016.
 */
public class PersistenciaHelper {
    private Produto produto;
    private final EditText edtProduto, edtPreco, edtId, edtDescricao;

    public PersistenciaHelper(ProdutoActivity activity){
        edtProduto = (EditText)activity.findViewById(R.id.txtNomeProduto);
        edtPreco = (EditText)activity.findViewById(R.id.txtPreco);
        edtDescricao = (EditText)activity.findViewById(R.id.txtDescricao);
        edtId = (EditText)activity.findViewById(R.id.txtID);
        produto = new Produto();
    }

    //Metodo que valida se os editTexts estao preenchidos
    public boolean validaCampos (){
        String produto = edtProduto.getText().toString().trim();
        String preco = edtPreco.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();
        return (!TextUtils.isEmpty(produto) && !TextUtils.isEmpty(preco));
    }

    //Metodo que preenche os edits com os valores de um objeto Produto
    public void preencheEdts(Produto prod){
        if(prod != null){
            edtProduto.setText(prod.getNome().toString());
            edtPreco.setText(prod.getPreco().toString());
            edtDescricao.setText(prod.getDescricao().toString());
            edtId.setText(prod.getId().toString());
        }
    }

    //Metodo que pega os valores dos EditTexts e retorna um objeto Produto
    public Produto pegaProduto(){
        produto.setId(Integer.parseInt(edtId.getText().toString().trim()));
        produto.setNome(edtProduto.getText().toString().trim());
        produto.setDescricao(edtDescricao.getText().toString().trim());
        produto.setPreco(Double.parseDouble(edtPreco.getText().toString().trim()));
        return produto;
    }
}

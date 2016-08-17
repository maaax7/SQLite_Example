package com.example.rm73950.persistencia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rm73950.persistencia.bean.Produto;
import com.example.rm73950.persistencia.dao.ProdutoDAO;
import com.example.rm73950.persistencia.util.PersistenciaHelper;
import com.example.rm73950.sqlite_example.R;

import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {

    private EditText edtProduto, edtPreco, edtId, edtDescricao;
    private ListView listProdutos;
    private Button btnLimpar;
    private PersistenciaHelper helper;
    private ProdutoDAO db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        edtProduto = (EditText)findViewById(R.id.txtNomeProduto);
        edtPreco = (EditText)findViewById(R.id.txtPreco);
        edtId = (EditText)findViewById(R.id.txtID);
        edtDescricao = (EditText)findViewById(R.id.txtDescricao);
        btnLimpar = (Button)findViewById(R.id.btnNovo);
        listProdutos = (ListView)findViewById(R.id.listProdutos);

        //Instancia uma classe que extende SQLiteOpenHelper com a TBL_Produto
        db = new ProdutoDAO(this);

        helper = new PersistenciaHelper(this);

        //Abrindo o banco para DML em modo de escrita
        carregaLista();

        listProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Produto produto = (Produto)listProdutos.getItemAtPosition(position);
                helper.preencheEdts(produto);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Carrega a lista sempre que onResume é chamado
        carregaLista();
    }

    //metodo que insere ou atualiza o produto na base
    public void gravar (View v){
        //Pega o id do Edit edtId
        String strId = edtId.getText().toString().trim();

        //Valida se os campos estão preenchidos
        if(helper.validaCampos()){
            Produto produto = helper.pegaProduto();

            //Se o ID for vazio
            if(TextUtils.isEmpty(strId))
                db.inserir(produto);
            else
                db.atualizar(produto);
        }
        else {
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
        }
        limpaTela(v);
    }

    public void excluir(View v){
        //Pega o id do Edit edtId
        String strId = edtId.getText().toString().trim();

        if(helper.validaCampos()){
            db.excluir(strId);
            btnLimpar.callOnClick();
        }
        else {
            Toast.makeText(this, "Selecione um produto para excluir", Toast.LENGTH_LONG).show();
        }
        limpaTela(v);
    }

    //Metodo que limpa a tela e recarrega a lista
    private void limpaTela(View v){
        edtDescricao.setText("");
        edtProduto.setText("");
        edtPreco.setText("");
        edtId.setText("");
        edtProduto.requestFocus();
        carregaLista();
    }

    //método que carrega lista de produtos
    private void carregaLista(){
        List<Produto> produtos = db.buscaProdutos();

        //Popula o listProdutos
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produtos);
        listProdutos.setAdapter(adapter);
    }
}

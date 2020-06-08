package com.example.david.projetofinal;

//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: David Alan Moreira

//******************************************************

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class Cadastro extends AppCompatActivity {

    //Declaração das variáveis
    private DBHelper dh;
    EditText etn, etc, eti, ett, ete;
    Button  bi, bl, bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Inicialização das variáveis
        dh = new DBHelper (this);

        etn = (EditText) findViewById(R.id.etNome);
        etc = (EditText) findViewById(R.id.etCPF);
        eti = (EditText) findViewById(R.id.etIdade);
        ett = (EditText) findViewById(R.id.etTelefone);
        ete = (EditText) findViewById(R.id.etEmail);

        bi = (Button) findViewById(R.id.btInserir);
        bl = (Button) findViewById(R.id.btListar);
        bm = (Button) findViewById(R.id.btTelaInicial);

        //Objetivo: Inserção dos dados no banco de dados
        bi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etn.getText().length() > 0
                        && etc.getText().length() > 0
                        && eti.getText().length() > 0
                        && ett.getText().length() > 0
                        && ete.getText().length() > 0){

                    dh.insert(etn.getText().toString(),
                            Integer.parseInt(etc.getText().toString()),
                            Integer.parseInt(eti.getText().toString()),
                            Integer.parseInt(ett.getText().toString()),
                            ete.getText().toString());

                    AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
                    adb.setTitle("Sucesso!");
                    adb.setMessage("Registro inserido com sucesso!");
                    adb.show();

                    etn.setText("");
                    etc.setText("");
                    eti.setText("");
                    ett.setText("");
                    ete.setText("");

                }else{
                    AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
                    adb.setTitle("Erro!");
                    adb.setMessage("Todos os campos devem ser preenchidos!");
                    adb.show();

                    etn.setText("");
                    etc.setText("");
                    eti.setText("");
                    ett.setText("");
                    ete.setText("");
                }
            }
        });

        //Objetivo: Listagem dos elementos no banco de dados
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Pessoa> pessoas = dh.queryGetAll();

                if(pessoas==null){

                    AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);

                    adb.setTitle("Mensagem");
                    adb.setMessage("Não há registros cadastrados!");
                    adb.show();

                    return;
                }
                for(int i=0; i<pessoas.size(); i++){

                    Pessoa p = (Pessoa) pessoas.get(i);

                    AlertDialog.Builder adb = new AlertDialog.Builder(Cadastro.this);
                    adb.setTitle("Registro "+(i+1));

                    adb.setMessage("Nome: "+p.getNome()+
                            "\nCPF: "+p.getCpf()+
                            "\nIdade: "+p.getIdade()+
                            "\nTelefone: "+p.getTelefone()+
                            "\nE-mail: "+p.getEmail());

                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
                }
            }
        });


        //Objetivo: Transição de telas
        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Cadastro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }




}

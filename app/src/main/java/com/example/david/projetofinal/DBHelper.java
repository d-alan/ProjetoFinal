package com.example.david.projetofinal;

//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: David Alan Moreira

//******************************************************

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.List;

//Métodos para ciração e manutenção do banco de dados portátil
public class DBHelper {
    private static final String DATABASE_NAME = "bancodedados.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "pessoa";

    private Context context;
    private SQLiteDatabase db;

    private SQLiteStatement insertStnt;
    private static final String INSERT = "insert into "+TABLE_NAME+" (nome, cpf, idade, telefone, email) values(?,?,?,?,?)";

    public DBHelper(Context context){
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStnt = this.db.compileStatement(INSERT);
    }

    public long insert (String nome, int cpf, int idade, int telefone, String email){

        this.insertStnt.bindString(1, nome);
        this.insertStnt.bindString(2, Integer.toString(cpf));
        this.insertStnt.bindString(3, Integer.toString(idade));
        this.insertStnt.bindString(4, Integer.toString(telefone));
        this.insertStnt.bindString(5, email);

        return this.insertStnt.executeInsert();
    }

    public void deleteAll(){
        this.db.delete(TABLE_NAME, null, null);
    }

    public List<Pessoa> queryGetAll(){

        List<Pessoa> list = new ArrayList<Pessoa>();

        try{

            Cursor cursor = this.db.query(TABLE_NAME, new String[]{"nome","cpf","idade","telefone","email"}
                    ,null,null,null,null,null,null);
            int nregistros = cursor.getCount();

            if(nregistros!=0){
                cursor.moveToFirst();

                do{
                    Pessoa pessoa = new Pessoa(cursor.getString(0),
                            Integer.parseInt(cursor.getString(1)),
                            Integer.parseInt(cursor.getString(2)),
                            Integer.parseInt(cursor.getString(3)),
                            cursor.getString(4));

                    list.add(pessoa);
                }while(cursor.moveToNext());

                if(cursor != null && !cursor.isClosed())
                    cursor.close();

                return list;
            }else{

                return null;
            }

        }catch(Exception err){
            return null;
        }
    }

    private static class OpenHelper extends SQLiteOpenHelper{

        OpenHelper(Context context){
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db){
            String sql = "create table if not exists "+TABLE_NAME+" (id integer primary key autoincrement, nome text, cpf int, idade int, telefone int, email text);";
            db.execSQL(sql);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("drop table if exists "+getDatabaseName());
            onCreate(db);
        }
    }

}

package com.example.alouiomar.gestionlivres;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class AccessDB {
    Context context;
    public String[] allColumns = { MySQLiteOpenHelper.COL_ID, MySQLiteOpenHelper.COL_ISBN,
            MySQLiteOpenHelper.COL_TITRE };
    private SQLiteDatabase bdd;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    public AccessDB(Context context) {
        this.context = context;
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context,"livre",null,1);
    }
    public void open() {

        bdd = mySQLiteOpenHelper.getWritableDatabase();
    }
    public void close() {
        bdd.close();
    }
    public long ajouter(String isbn, String titre) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteOpenHelper.COL_ISBN, isbn);
        values.put(MySQLiteOpenHelper.COL_TITRE, titre);


        return bdd.insert(mySQLiteOpenHelper.TABLE_LIVRES, null, values);
    }
    public ArrayList<Livre> getall() {

        ArrayList<Livre> livres = new ArrayList<Livre>();
        Cursor cursor = bdd.rawQuery("SELECT * FROM " + MySQLiteOpenHelper.TABLE_LIVRES, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Livre article = cursorToLivre(cursor);
            livres.add(article);
            cursor.moveToNext();
        }

        cursor.close();

        return livres ;


    }
    private Livre cursorToLivre(Cursor cursor) {
        Livre livre = new Livre();

        livre.setId(cursor.getInt(0));
        livre.setIsbn(cursor.getString(1));
        livre.setTitre(cursor.getString(2));

        return livre;
    }
    public int supp(Livre l) {
        return bdd.delete(MySQLiteOpenHelper.TABLE_LIVRES,
                "id = ?", new String[]{l.getId() + ""});
    }
    public void suppTT() {
        bdd.execSQL("DELETE  FROM " + MySQLiteOpenHelper.TABLE_LIVRES);
    }



    public void modifier(Livre livre, String isbn, String titre) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteOpenHelper.COL_ISBN, isbn);
        values.put(MySQLiteOpenHelper.COL_TITRE, titre);

        long result = bdd.update(MySQLiteOpenHelper.TABLE_LIVRES, values,
                "id = ?",
                new String[]{String.valueOf(livre.getId())});
        Log.d("Update Result:", "=" + result);



    }
    public ArrayList<Livre> recherche(CharSequence text) {

        ArrayList<Livre> livre= new ArrayList<Livre>();
        Cursor cursor = bdd.rawQuery("SELECT * FROM " + MySQLiteOpenHelper.TABLE_LIVRES + " WHERE  " + MySQLiteOpenHelper.COL_TITRE + " = " + text, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Livre article = cursorToLivre(cursor);
            livre.add(article);
            cursor.moveToNext();
        }


        cursor.close();
        return livre;



    }
}
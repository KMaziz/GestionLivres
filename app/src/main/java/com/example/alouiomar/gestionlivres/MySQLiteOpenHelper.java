package com.example.alouiomar.gestionlivres;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static String TABLE_LIVRES="livre";
    public static String COL_ID ="id";
    public static String COL_ISBN ="isbn";
    public static String COL_TITRE="titre" ;
    public String CREATE_BDD;
    // Constructeur
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE "
                + TABLE_LIVRES + "(" + COL_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COL_ISBN + " TEXT, "
                + COL_TITRE + " TEXT "
                + ")");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRES);


    }
}

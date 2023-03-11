package com.example.spendingmanagement.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        //Create database
        super(context, "MySQLiteCreateForMyApp", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE PHANLOAI(MaLoai integer primary key autoincrement," + "TenLoai text, TrangThai text)";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO PHANLOAI VALUES(null,'salary', 'collect')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO PHANLOAI VALUES(null,'debt collection', 'collect')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO PHANLOAI VALUES(null,'tourism', 'spent')";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO PHANLOAI VALUES(null,'living', 'spent')";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE GIAODICH(MaGD integer primary key autoincrement," + "TieuDe text, Ngay text, Tien float, " +
                "MoTa text, MaLoai integer references PHANLOAI(MaLoai))";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO GIAODICH VALUES(null, 'Luong thang 3','2023-3-3', 500000 , 'Day la luong thang 3', 1 )";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO GIAODICH VALUES(null, 'Luong thang 3','2023-3-3', 500000 , 'Day la luong thang 3', 2 )";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHANLOAI");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS GIAODICH");
        onCreate(sqLiteDatabase);
    }
}

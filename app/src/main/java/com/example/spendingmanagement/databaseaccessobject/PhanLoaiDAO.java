package com.example.spendingmanagement.databaseaccessobject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.spendingmanagement.database.DBHelper;
import com.example.spendingmanagement.model.PhanLoai;

import java.util.ArrayList;
import java.util.Currency;

public class PhanLoaiDAO {
    DBHelper dbHelper;

    public PhanLoaiDAO(Context context) {
        dbHelper = new DBHelper(context);

    }

    public ArrayList<PhanLoai> getAll() {
        ArrayList<PhanLoai> arrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM PHANLOAI";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        // dua con tro ve dong dau tien
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String trangThai = cursor.getString(2);

            arrayList.add(new PhanLoai(ma, ten, trangThai));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return arrayList;
    }
    public ArrayList<PhanLoai> getAll(String trangThai) {
        ArrayList<PhanLoai> arrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM PHANLOAI WHERE TrangThai = '"+trangThai+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        // dua con tro ve dong dau tien
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            trangThai = cursor.getString(2);

            arrayList.add(new PhanLoai(ma, ten, trangThai));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return arrayList;
    }

    public boolean insert(PhanLoai phanLoai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        // create contentvaues
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai", phanLoai.getTenLoai());
        contentValues.put("Trangthai", phanLoai.getTrangThai());

        long row = sqLiteDatabase.insert("PHANLOAI", null, contentValues);
        boolean condition = row > 0;

        return condition;
    }

    public boolean insert(String tenLoai, String trangThai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        // create contentvaues
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai", tenLoai);
        contentValues.put("Trangthai", trangThai);

        long row = sqLiteDatabase.insert("PHANLOAI", null, contentValues);
        boolean condition = row > 0;

        return condition;
    }

    public boolean update(PhanLoai phanloai) {
        //String sql = "UPDATE PHANLOAI SET TenLoai = ?, TrangThai = ? WHERE MaLoai = ? ";

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai", phanloai.getTenLoai());
        contentValues.put("TrangThai", phanloai.getTrangThai());

        int row = sqLiteDatabase.update("PHANLOAI", contentValues,
                "MaLoai = ?",
                new String[]{phanloai.getMaLoai() + ""});

        return (row > 0);
    }

    public boolean delete(int maLoai) {
        //String sql = "DELETE FROM PHANLOAI WHERE MaLoai = ? ";
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        int row = sqLiteDatabase.delete("PHANLOAI", "MaLoai = ?", new String[]{maLoai + ""});

        return (row > 0);
    }
    public PhanLoai getMaLoaiById(int maLoai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM GIAODICH WHERE MaLoai = " + maLoai;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        // dua con tro ve dong dau tien
        cursor.moveToFirst();

        int ma = cursor.getInt(0);
        String ten = cursor.getString(1);
        String trangThai = cursor.getString(2);

        PhanLoai phanLoai = new PhanLoai(ma, ten, trangThai);

        cursor.close();
        sqLiteDatabase.close();
        return phanLoai;
    }
}

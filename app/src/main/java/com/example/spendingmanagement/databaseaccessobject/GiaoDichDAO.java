package com.example.spendingmanagement.databaseaccessobject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.spendingmanagement.database.DBHelper;
import com.example.spendingmanagement.model.GiaoDich;
import com.example.spendingmanagement.model.PhanLoai;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GiaoDichDAO {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DBHelper helperGiaoDich;

    public GiaoDichDAO(Context context) {
        helperGiaoDich = new DBHelper(context);
    }

    public ArrayList<GiaoDich> getAll() {
        ArrayList<GiaoDich> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helperGiaoDich.getReadableDatabase();
        String sql = "SELECT * FROM GIAODICH";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        // dua con tro ve dong dau tien
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int maGD = cursor.getInt(0);
            String tieuDe = cursor.getString(1);

            Date ngay;
            try {
                ngay = simpleDateFormat.parse(cursor.getString(2));
            } catch (Exception e) {
                ngay = new Date("1111-11-11");
            }

            double tien = cursor.getDouble(3);
            String moTa = cursor.getString(4);
            int maLoai = cursor.getInt(5);

            arrayList.add(new GiaoDich(maGD, tieuDe, ngay, tien, moTa, maLoai));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return arrayList;
    }

    public ArrayList<GiaoDich> getAll(String trangThai) {
        ArrayList<GiaoDich> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helperGiaoDich.getReadableDatabase();
        String sql = "SELECT * FROM GIAODICH";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        // dua con tro ve dong dau tien
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int maGD = cursor.getInt(0);
            String tieuDe = cursor.getString(1);

            Date ngay;
            try {
                ngay = simpleDateFormat.parse(cursor.getString(2));
            } catch (Exception e) {
                ngay = new Date("1900-11-11");
            }

            double tien = cursor.getDouble(3);
            String moTa = cursor.getString(4);
            int maLoai = cursor.getInt(5);

            GiaoDich giaoDich = new GiaoDich(maGD, tieuDe, ngay, tien, moTa, maLoai);
            arrayList.add(giaoDich);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return arrayList;
    }

    public boolean insert(GiaoDich giaoDich) {
        SQLiteDatabase sqLiteDatabase = helperGiaoDich.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("TieuDe",giaoDich.getTieuDe());
        contentValues.put("Ngay", simpleDateFormat.format(giaoDich.getNgay()));
        contentValues.put("Tien",giaoDich.getTien());
        contentValues.put("MoTa",giaoDich.getMaGD());
        contentValues.put("MaLoai",giaoDich.getMaGD());

        long row = sqLiteDatabase.insert("GIAODICH", null, contentValues);
        boolean condition = row > 0;

        return condition;
    }

    public void update() {
    }

    public boolean delete(int maLoai) {
        SQLiteDatabase sqLiteDatabase = helperGiaoDich.getReadableDatabase();
        int row = sqLiteDatabase.delete("GIAODICH", "MaLoai = ?", new String[]{maLoai + ""});

        return (row > 0);
    }
}

package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cr424s.hothithaovy.studentmanager.Deparment;

public class SinhVienDB extends SQLiteOpenHelper {
    String khoaTable="CREATE TABLE Department (\n" +
            "            id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "            department_name TEXT NOT NULL,\n" +
            "            office_location TEXT,\n" +
            "            phone_number TEXT\n" +
            "    )\n" +
            "    ";
    String sv1 = "CREATE TABLE Student (\n" +
            "            id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "            student_code TEXT NOT NULL UNIQUE,\n" +
            "            full_name TEXT NOT NULL,\n" +
            "            birth_date TEXT,\n" +
            "            address TEXT,\n" +
            "            phone_number TEXT,\n" +
            "            department_id INTEGER,\n" +
            "            FOREIGN KEY (department_id) REFERENCES Department(id)\n" +
            "            )";

    public SinhVienDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(khoaTable);
        db.execSQL(sv1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long themKhoa(String ten, String diaChi, String sdt) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("department_name",ten);
        contentValues.put("office_location",diaChi);
        contentValues.put("phone_number",sdt);
        long i = db.insert("Department",null,contentValues);
        db.close();
        return i;
    }

}

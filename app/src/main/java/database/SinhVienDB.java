package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cr424s.hothithaovy.studentmanager.Department;

public class SinhVienDB extends SQLiteOpenHelper {

    // Bảng Khoa
    String khoaTable = "CREATE TABLE Khoa (\n" +
            "    maKhoa TEXT PRIMARY KEY,\n" +
            "    tenKhoa TEXT NOT NULL,\n" +
            "    diaChiKhoa TEXT,\n" +
            "    soDienThoaiKhoa TEXT\n" +
            ")";

    // Bảng Sinh viên
    String sinhVienTable = "CREATE TABLE SinhVien (\n" +
            "    maSV TEXT PRIMARY KEY,\n" +
            "    hoTen TEXT NOT NULL,\n" +
            "    ngaySinh TEXT,\n" +
            "    gioiTinh TEXT,\n" +
            "    diaChi TEXT,\n" +
            "    soDienThoai TEXT,\n" +
            "    soThich TEXT,\n" +
            "    maKhoa TEXT,\n" +
            "    FOREIGN KEY (maKhoa) REFERENCES Khoa(maKhoa)\n" +
            ")";

    public SinhVienDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(khoaTable);
        db.execSQL(sinhVienTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    // Thêm Khoa bằng tham số
    public long themKhoa(String maKhoa, String tenKhoa, String diaChiKhoa, String soDienThoaiKhoa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maKhoa", maKhoa);
        values.put("tenKhoa", tenKhoa);
        values.put("diaChiKhoa", diaChiKhoa);
        values.put("soDienThoaiKhoa", soDienThoaiKhoa);
        long i = db.insert("Khoa", null, values);
        db.close();
        return i;
    }

    // Thêm Khoa bằng đối tượng Department
    public long themKhoa(Department department) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maKhoa", department.getMaKhoa());
        values.put("tenKhoa", department.getTenKhoa());
        values.put("diaChiKhoa", department.getDiaChi());
        values.put("soDienThoaiKhoa", department.getSdt());
        long i = db.insert("Khoa", null, values);
        db.close();
        return i;
    }

    // Sửa thông tin Khoa
    public void suaKhoa(Department department) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soDienThoaiKhoa", department.getSdt());
        db.update("Khoa", values, "maKhoa = ?", new String[]{department.getMaKhoa()});
        db.close();
    }

    // Lấy tất cả Khoa
    public List<Department> getAllKhoa() {
        SQLiteDatabase db = getReadableDatabase();
        List<Department> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT * FROM Khoa", null);
        Log.i("DB_DEBUG", "Tổng số khoa: " + cs.getCount());

        if (cs.moveToFirst()) {
            do {
                Department khoa = new Department(
                        cs.getString(0),
                        cs.getString(1),
                        cs.getString(2),
                        cs.getInt(3)
                );
                list.add(khoa);
            } while (cs.moveToNext());
        }
        cs.close();
        db.close();
        return list;
    }

    // Xóa khoa theo mã
    public boolean xoaKhoaTheoMa(String maKhoa) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete("Khoa", "maKhoa = ?", new String[]{maKhoa});
        db.close();
        return rowsDeleted > 0;
    }

    // Xóa toàn bộ khoa
    public void xoaTatCaKhoa() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Khoa", null, null);
        db.close();
    }
}

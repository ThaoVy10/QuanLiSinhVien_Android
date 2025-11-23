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
import cr424s.hothithaovy.studentmanager.SinhVien;

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
            "    email TEXT,\n" +
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
                        cs.getString(0), //maKhoa
                        cs.getString(1), //tenKhoa
                        cs.getString(2), //diaChi
                        cs.getString(3)  //sdt
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

    public long themSV(SinhVien sv) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSV", sv.getMaSV());
        values.put("hoTen", sv.getHoTen());
        values.put("ngaySinh", sv.getNgaySinh());
        values.put("gioiTinh", sv.getGioitinh());
        values.put("email", sv.getEmail());
        values.put("soDienThoai", sv.getSdt());
        values.put("soThich", sv.getSothich());
        values.put("maKhoa", sv.getKhoa());
        long i = db.insert("SinhVien", null, values);
        db.close();
        return i;
    }
    public void suaTTSV(SinhVien sv) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSV", sv.getMaSV());
        values.put("hoTen", sv.getHoTen());
        values.put("ngaySinh", sv.getNgaySinh());
        values.put("gioiTinh", sv.getGioitinh());
        values.put("email", sv.getEmail());
        values.put("soDienThoai", sv.getSdt());
        values.put("soThich", sv.getSothich());
        values.put("maKhoa", sv.getKhoa());
        int rows = db.update("SinhVien", values, "maSV = ?", new String[]{sv.getMaSV()});
        db.close();
    }
    public List<SinhVien> getAllSinhVien() {
        SQLiteDatabase db = getReadableDatabase();
        List<SinhVien> list = new ArrayList<>();

        Cursor cs = db.rawQuery("SELECT * FROM SinhVien", null);
        Log.i("DB_DEBUG", "Tổng số sinh viên: " + cs.getCount());

        if (cs.moveToFirst()) {
            do {
                SinhVien sv = new SinhVien(
                        cs.getString(0), // maSV
                        cs.getString(1), // hoTen
                        cs.getString(5), // soDienThoai
                        cs.getString(4), // email
                        cs.getString(2), // ngaySinh
                        cs.getString(7), // maKhoa
                        cs.getString(3), // gioiTinh
                        cs.getString(6)  // soThich
                );
                list.add(sv);
            } while (cs.moveToNext());
        }
        cs.close();
        db.close();
        return list;
    }

    // Lấy sinh viên theo mã
    public SinhVien getSinhVienTheoMa(String maSV) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM SinhVien WHERE maSV = ?", new String[]{maSV});

        SinhVien sv = null;
        if (cs.moveToFirst()) {
            sv = new SinhVien(
                    cs.getString(0), // maSV
                    cs.getString(1), // hoTen
                    cs.getString(5), // soDienThoai
                    cs.getString(4), // email
                    cs.getString(2), // ngaySinh
                    cs.getString(7), // maKhoa
                    cs.getString(3), // gioiTinh
                    cs.getString(6)  // soThich
            );
        }
        cs.close();
        db.close();
        return sv;
    }
    public List<SinhVien> getSinhVienTheoKhoa(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        List<SinhVien> list = new ArrayList<>();

        Cursor cs = db.rawQuery(
                "SELECT sv.* FROM SinhVien sv LEFT JOIN Khoa k ON sv.maKhoa = k.maKhoa " +
                        "WHERE sv.maKhoa LIKE ? OR k.tenKhoa LIKE ?",
                new String[]{"%" + keyword + "%", "%" + keyword + "%"}
        );

        if(cs.moveToFirst()) {
            do {
                SinhVien sv = new SinhVien(
                        cs.getString(0), // maSV
                        cs.getString(1), // hoTen
                        cs.getString(5), // soDienThoai
                        cs.getString(4), // email
                        cs.getString(2), // ngaySinh
                        cs.getString(7), // maKhoa
                        cs.getString(3), // gioiTinh
                        cs.getString(6)  // soThich
                );
                list.add(sv);
            } while(cs.moveToNext());
        }

        cs.close();
        db.close();
        return list;
    }

    // Xóa toàn bộ sinh viên
    public void xoaTatCaSinhVien() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("SinhVien", null, null);
        db.close();
    }
    // Xóa sinh viên theo mã
    public boolean xoaSinhVienTheoMa(String maSV) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete("SinhVien", "maSV = ?", new String[]{maSV});
        db.close();
        return rowsDeleted > 0;
    }
    public boolean kiemTraMaSV(String maSV) {
        return getSinhVienTheoMa(maSV) != null;
    }

}

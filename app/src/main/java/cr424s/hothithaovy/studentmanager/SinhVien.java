package cr424s.hothithaovy.studentmanager;

import java.io.Serializable;

public class SinhVien implements Serializable {
    private String hoTen, maSV, sdt, email, ngaySinh, khoa, gioitinh, sothich;


    public SinhVien(String hoTen, String maSV, String sdt, String email, String ngaySinh, String khoa, String gioitinh, String sothich) {
        this.hoTen = hoTen;
        this.maSV = maSV;
        this.sdt = sdt;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.khoa = khoa;
        this.gioitinh = gioitinh;
        this.sothich = sothich;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getSothich() {
        return sothich;
    }

    public void setSothich(String sothich) {
        this.sothich = sothich;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "hoTen='" + hoTen + '\'' +
                ", maSV='" + maSV + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", khoa='" + khoa + '\'' +
                ", gioitinh='" + gioitinh + '\'' +
                ", sothich='" + sothich + '\'' +
                '}';
    }
}

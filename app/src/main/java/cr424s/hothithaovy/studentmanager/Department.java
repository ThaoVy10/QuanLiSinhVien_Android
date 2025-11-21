package cr424s.hothithaovy.studentmanager;

import java.io.Serializable;

public class Department implements Serializable {
    String maKhoa,tenKhoa, diaChi,sdt;


    public Department(String maKhoa, String tenKhoa, String diaChi, String sdt) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "Department{" +
                "maKhoa='" + maKhoa + '\'' +
                ", tenKhoa='" + tenKhoa + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", sdt=" + sdt +
                '}';
    }
}




package cr424s.hothithaovy.studentmanager;

import java.io.Serial;
import java.io.Serializable;

public class Deparment implements Serializable {
    String tenKhoa, diaChi;
    int sdt;

    public Deparment(String tenKhoa, String diaChi, int sdt) {
        this.tenKhoa = tenKhoa;
        this.diaChi = diaChi;
        this.sdt = sdt;
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

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }
}




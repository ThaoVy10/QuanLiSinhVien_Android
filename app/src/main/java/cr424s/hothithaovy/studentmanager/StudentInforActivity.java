package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import database.SinhVienDB;

public class StudentInforActivity extends AppCompatActivity {
    EditText edtHoTen, edtSdt, edtMaSV,edtNgaySinh ,edtMail;
    Spinner spKhoa;
    RadioButton rbtNam, rbtNu;
    CheckBox cbMusic,cbReadBook,cbSport,cbGame;
    Button btnCapNhat, btnXoa;
    String[] KHOA = {
            "Công Nghệ Phần Mềm",
            "Khoa Học Máy Tính",
            "Big Data",
            "Trí Tuệ Nhân Tạo",
            "Hệ Thống Thông Tin",
            "Mạng Máy Tính",
            "An Toàn Thông Tin",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_infor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtHoTen = findViewById(R.id.hoten);
        edtMaSV = findViewById(R.id.masv);
        edtSdt = findViewById(R.id.sdt);
        edtNgaySinh = findViewById(R.id.ngaysinh);
        edtMail = findViewById(R.id.email);

        cbMusic = findViewById(R.id.amnhac);
        cbGame = findViewById(R.id.choigame);
        cbSport = findViewById(R.id.thethao);
        cbReadBook = findViewById(R.id.docsach);

        rbtNam = findViewById(R.id.nam);
        rbtNu = findViewById(R.id.nu);

        spKhoa = findViewById(R.id.khoa);
        ArrayAdapter<String> adapterKhoa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, KHOA);
        spKhoa.setAdapter(adapterKhoa);

        SinhVien sinhVien = (SinhVien) getIntent().getSerializableExtra("k_sinhvien");
        if (sinhVien != null) {
            edtHoTen.setText(sinhVien.getHoTen());
            edtSdt.setText(sinhVien.getSdt());
            edtMaSV.setText(sinhVien.getMaSV());
            edtNgaySinh.setText(sinhVien.getNgaySinh());
            edtMail.setText(sinhVien.getEmail());
            for (int i = 0; i < KHOA.length; i++) {
                if (KHOA[i].equals(sinhVien.getKhoa())) {
                    spKhoa.setSelection(i);
                    break;
                }
            }
            spKhoa.setEnabled(false);

            if ("Nam".equalsIgnoreCase(sinhVien.getGioitinh())) rbtNam.setChecked(true);
            else if ("Nữ".equalsIgnoreCase(sinhVien.getGioitinh())) rbtNu.setChecked(true);

            String st = sinhVien.getSothich() == null ? "" : sinhVien.getSothich();
            cbMusic.setChecked(st.contains("Âm nhạc"));
            cbReadBook.setChecked(st.contains("Đọc sách"));
            cbSport.setChecked(st.contains("Thể thao"));
            cbGame.setChecked(st.contains("Chơi game"));
        }
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SinhVienDB db = new SinhVienDB(StudentInforActivity.this, "QLSinhVien", null, 1);
                String maSV = edtMaSV.getText().toString().trim();

                if (maSV.isEmpty()) {
                    Toast.makeText(StudentInforActivity.this, "Mã SV trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!db.kiemTraMaSV(maSV)) {
                    Toast.makeText(StudentInforActivity.this, "Mã SV không tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String hoTen = edtHoTen.getText().toString().trim();
                String ngaySinh = edtNgaySinh.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();
                String email = edtMail.getText().toString().trim();
                String gioiTinh = rbtNam.isChecked() ? "Nam" : "Nữ";
                String khoa = spKhoa.getSelectedItem().toString();

                String soThich = "";
                if (cbMusic.isChecked()) soThich += "Âm nhạc, ";
                if (cbReadBook.isChecked()) soThich += "Đọc sách, ";
                if (cbSport.isChecked()) soThich += "Thể thao, ";
                if (cbGame.isChecked()) soThich += "Chơi game, ";
                if (soThich.endsWith(", ")) soThich = soThich.substring(0, soThich.length() - 2);

                SinhVien sv = new SinhVien(maSV, hoTen, sdt, email, ngaySinh, khoa, gioiTinh, soThich);

                db.suaTTSV(sv);
                Toast.makeText(StudentInforActivity.this, "Cập nhật sinh viên thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("sv_updated", sv);  // gửi đối tượng sinh viên sau khi sửa
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        btnXoa = findViewById(R.id.btnXoa);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SinhVienDB db = new SinhVienDB(StudentInforActivity.this, "QLSinhVien", null, 1);
                String maSV = edtMaSV.getText().toString().trim();

                if (maSV.isEmpty()) {
                    Toast.makeText(StudentInforActivity.this, "Vui lòng nhập mã SV để xóa!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra sinh viên có tồn tại không
                if (!db.kiemTraMaSV(maSV)) {
                    Toast.makeText(StudentInforActivity.this, "Mã SV không tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Xóa sinh viên
                boolean ok = db.xoaSinhVienTheoMa(maSV);
                if (ok) {
                    Toast.makeText(StudentInforActivity.this, "Xóa sinh viên thành công!", Toast.LENGTH_SHORT).show();

                    // Xóa các trường trong giao diện
                    edtMaSV.setText("");
                    edtHoTen.setText("");
                    edtSdt.setText("");
                    edtMail.setText("");
                    edtNgaySinh.setText("");
                    rbtNam.setChecked(false);
                    rbtNu.setChecked(false);
                    cbMusic.setChecked(false);
                    cbReadBook.setChecked(false);
                    cbSport.setChecked(false);
                    cbGame.setChecked(false);
                } else {
                    Toast.makeText(StudentInforActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
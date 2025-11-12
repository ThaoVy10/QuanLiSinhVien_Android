package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

public class StudentInforActivity extends AppCompatActivity {
    EditText edtHoTen, edtSdt, edtMaSV,edtNgaySinh ,edtMail;
    Spinner spKhoa;
    String[] KHOA = {
            "Công Nghệ Phần Mềm",
            "Khoa Học Máy Tính",
            "Big Data",
            "Trí Tuệ Nhân Tạo",
            "Hệ Thống Thông Tin",
            "Mạng Máy Tính",
            "An Toàn Thông Tin",
    };
    RadioButton rbtNam, rbtNu;
    CheckBox cbMusic,cbReadBook,cbSport,cbGame;
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
        spKhoa = findViewById(R.id.khoa);
        cbMusic = findViewById(R.id.amnhac);
        cbGame = findViewById(R.id.choigame);
        cbSport = findViewById(R.id.thethao);
        cbReadBook = findViewById(R.id.docsach);
        rbtNam = findViewById(R.id.nam);
        rbtNu = findViewById(R.id.nu);
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
    }
}
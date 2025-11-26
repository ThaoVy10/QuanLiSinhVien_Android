package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import database.SinhVienDB;

public class AddDepartment extends AppCompatActivity {
    EditText edtMaKhoa, edtTenKhoa, edtDiaChi, edtSDT;
    Button btnTMKhoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_department);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtMaKhoa = findViewById(R.id.edtMaKhoa);
        edtTenKhoa = findViewById(R.id.edtTenKhoa);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtSDT = findViewById(R.id.edtSdt);
        btnTMKhoa = findViewById(R.id.btnTMKhoa);
        btnTMKhoa.setOnClickListener(v -> {

            SinhVienDB db = new SinhVienDB(AddDepartment.this,
                    "QLKhoa", null, 1);

            Department khoa = new Department();

            String maKhoa = edtMaKhoa.getText().toString().trim();
            String tenKhoa = edtTenKhoa.getText().toString().trim();
            String diaChi = edtDiaChi.getText().toString().trim();
            String sdt = edtSDT.getText().toString().trim();

            if (maKhoa.isEmpty() || tenKhoa.isEmpty() || diaChi.isEmpty() || sdt.isEmpty()) {
                Toast.makeText(AddDepartment.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gán dữ liệu vào đối tượng Khoa
            khoa.setMaKhoa(maKhoa);
            khoa.setTenKhoa(tenKhoa);
            khoa.setDiaChi(diaChi);
            khoa.setSdt(sdt);

            // Thêm vào DB
            long kq = db.themKhoa(khoa);

            if (kq > 0) {
                Toast.makeText(AddDepartment.this, "Thêm khoa thành công!", Toast.LENGTH_SHORT).show();
                Intent result = new Intent();
                result.putExtra("newKhoa", khoa);
                setResult(RESULT_OK, result);
                finish();
            } else {
                Toast.makeText(AddDepartment.this, "Thêm thất bại! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
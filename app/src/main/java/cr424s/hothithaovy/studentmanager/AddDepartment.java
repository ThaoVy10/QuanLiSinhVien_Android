package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import database.SinhVienDB;

public class AddDepartment extends AppCompatActivity {
    EditText edtMaKhoa, edtTenKhoa, edtDiaChi, edtSDT;
    Button btnTMKhoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);

        edtMaKhoa = findViewById(R.id.edtMaKhoa);
        edtTenKhoa = findViewById(R.id.edtTenKhoa);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtSDT = findViewById(R.id.edtSdt);
        btnTMKhoa = findViewById(R.id.btnTMKhoa);

        btnTMKhoa.setOnClickListener(v -> {
            String maKhoa = edtMaKhoa.getText().toString().trim();
            String tenKhoa = edtTenKhoa.getText().toString().trim();
            String diaChi = edtDiaChi.getText().toString().trim();
            String sdt = edtSDT.getText().toString().trim();

            if (maKhoa.isEmpty() || tenKhoa.isEmpty() || diaChi.isEmpty() || sdt.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            Department khoa = new Department(maKhoa, tenKhoa, diaChi, sdt);
            SinhVienDB db = new SinhVienDB(AddDepartment.this, "QLKhoa", null, 1);
            long kq = db.themKhoa(khoa);

            if (kq > 0) {
                Toast.makeText(this, "Thêm khoa thành công!", Toast.LENGTH_SHORT).show();
                Intent result = new Intent();
                result.putExtra("newDept", khoa); // key trùng với DepartmentList
                setResult(RESULT_OK, result);
                finish();
            } else {
                Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
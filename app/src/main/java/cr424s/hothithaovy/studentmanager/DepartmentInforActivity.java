package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import database.SinhVienDB;

public class DepartmentInforActivity extends AppCompatActivity {

    EditText edtMa_Khoa, edtTen_Khoa, edtDiaChi_Khoa, edtSDT_Khoa;
    Button btnSua_Khoa, btnXoa_Khoa, btnGoiK;
    SinhVienDB db;
    Department khoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_infor);

        edtMa_Khoa = findViewById(R.id.edtMa_Khoa);
        edtTen_Khoa = findViewById(R.id.edtTen_Khoa);
        edtDiaChi_Khoa = findViewById(R.id.edtDiaChi_Khoa);
        edtSDT_Khoa = findViewById(R.id.edtSDT_Khoa);

        btnSua_Khoa = findViewById(R.id.btnSua_Khoa);
        btnXoa_Khoa = findViewById(R.id.btnXoa_Khoa);
        btnGoiK = findViewById(R.id.btnGoiK);

        db = new SinhVienDB(this, "QLSinhVien", null, 1);
        khoa = (Department) getIntent().getSerializableExtra("k_khoa");

        if (khoa != null) {
            edtMa_Khoa.setText(khoa.getMaKhoa());
            edtTen_Khoa.setText(khoa.getTenKhoa());
            edtDiaChi_Khoa.setText(khoa.getDiaChi());
            edtSDT_Khoa.setText(khoa.getSdt());

            edtMa_Khoa.setEnabled(false);
            edtTen_Khoa.setEnabled(false);
            edtDiaChi_Khoa.setEnabled(false);
        }
        btnGoiK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_DIAL);
                myIntent.setData(Uri.parse("tel:"+edtSDT_Khoa.getText().toString()));
                startActivity(myIntent);
            }
        });
        btnSua_Khoa.setOnClickListener(v -> {
            String sdt = edtSDT_Khoa.getText().toString().trim();
            if (sdt.isEmpty()) {
                Toast.makeText(this, "Số điện thoại không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            khoa.setSdt(sdt);
            db.suaKhoa(khoa);
            Toast.makeText(this, "Cập nhật khoa thành công!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.putExtra("khoa_updated", khoa);
            setResult(RESULT_OK, intent);
            finish();
        });

        btnXoa_Khoa.setOnClickListener(v -> {
            String maKhoa = khoa.getMaKhoa();

            // Kiểm tra xem khoa có sinh viên không
            if (!db.getSinhVienTheoKhoa(maKhoa).isEmpty()) {
                Toast.makeText(this, "Không thể xóa khoa này vì có sinh viên!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = db.xoaKhoaTheoMa(maKhoa);
            if (ok) {
                Toast.makeText(this, "Xóa khoa thành công!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
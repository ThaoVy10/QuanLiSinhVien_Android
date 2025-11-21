package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import database.SinhVienDB;

public class DepartmentList extends AppCompatActivity {

    ListView lvKhoa;
    TextView edtTKKhoa;
    SinhVienDB db;
    private ArrayList<Department> danhSachKhoa;
    ActivityResultLauncher<Intent> addDepartmentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_department_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtTKKhoa = findViewById(R.id.edtTimKhoa);
        edtTKKhoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        lvKhoa = findViewById(R.id.lvKhoa);
        db = new SinhVienDB(this, "QLKhoa", null, 1);
        danhSachKhoa = new ArrayList<>(db.getAllKhoa());
        if (danhSachKhoa.isEmpty()) {
            khoiTaoDuLieu();
            danhSachKhoa = new ArrayList<>(db.getAllKhoa());
        }
        addDepartmentLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Toast.makeText(this, "Đã thêm khoa mới!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_moi_khoa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemTMKhoa) {
            Intent intent = new Intent(DepartmentList.this, AddDepartment.class);
            addDepartmentLauncher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void khoiTaoDuLieu() {
        Department[] defaultDepartments = new Department[]{
                new Department("CNTT", "Công Nghệ Thông Tin", "Tầng 1, Tòa A", "0123456789"),
                new Department("KHMT", "Khoa Học Máy Tính", "Tầng 3, Tòa C", "0112233445"),
                new Department("BD", "Big Data", "Tầng 4, Tòa D", "0223344556"),
                new Department("TTNT", "Trí Tuệ Nhân Tạo", "Tầng 5, Tòa E", "0334455667"),
                new Department("HTTT", "Hệ Thống Thông Tin", "Tầng 6, Tòa F", "0445566778"),
                new Department("MTT", "Mạng Máy Tính", "Tầng 7, Tòa G", "0556677889"),
                new Department("ATTT", "An Toàn Thông Tin", "Tầng 2, Tòa B", "0667788990")
        };
        for (Department d : defaultDepartments) {
            db.themKhoa(d);
        }
    }
}
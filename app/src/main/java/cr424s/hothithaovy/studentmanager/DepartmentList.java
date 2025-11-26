package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import database.SinhVienDB;

public class DepartmentList extends AppCompatActivity {

    ListView lvKhoa;
    TextView edtTKKhoa;
    SinhVienDB db;

    private ArrayList<Department> danhSachKhoa;
    DepartmentAdapter adapter;

    ActivityResultLauncher<Intent> addDepartmentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_department_list);


        lvKhoa = findViewById(R.id.lvKhoa);
        edtTKKhoa = findViewById(R.id.edtTimKhoa);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);


        db = new SinhVienDB(this, "QLKhoa", null, 1);


        danhSachKhoa = new ArrayList<>(db.getAllKhoa());
        if (danhSachKhoa.isEmpty()) {
            khoiTaoDuLieu();
            danhSachKhoa = new ArrayList<>(db.getAllKhoa());
        }


        adapter = new DepartmentAdapter(this, R.layout.cell_department_layout, danhSachKhoa);
        lvKhoa.setAdapter(adapter);


        edtTKKhoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString().trim().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        addDepartmentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Toast.makeText(this, "Đã thêm khoa mới!", Toast.LENGTH_SHORT).show();

                        danhSachKhoa = new ArrayList<>(db.getAllKhoa());
                        adapter.refresh();
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

        } else if (item.getItemId() == R.id.itemXoaKhoa) {

            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa tất cả khoa?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        db.xoaTatCaKhoa();
                        danhSachKhoa.clear();
                        adapter.refresh();
                        Toast.makeText(DepartmentList.this, "Đã xóa tất cả khoa!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void khoiTaoDuLieu() {
        Department[] defaultDepartments = new Department[]{
                new Department("CNTT", "Cong Nghe Phan Mem", "Tang 1, Toa A", "0123456789"),
                new Department("KHMT", "Khoa Hoc May Tinh", "Tang 3, Toa C", "0112233445"),
                new Department("BD", "Big Data", "Tang 4, Toa D", "0223344556"),
                new Department("TTNT", "Tri Tue Nhan Tao", "Tang 5, Toa E", "0334455667"),
                new Department("HTTT", "He Thong Thong Tin", "Tang 6, Toa F", "0445566778"),
                new Department("MTT", "Mang May Tinh", "Tang 7, Toa G", "0556677889"),
                new Department("ATTT", "An Toan Thong Tin", "Tang 2, Toa B", "0667788990")
        };

        for (Department d : defaultDepartments) {
            db.themKhoa(d);
        }
    }
}
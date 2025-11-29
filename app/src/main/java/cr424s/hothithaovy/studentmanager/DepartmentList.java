package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import database.SinhVienDB;

public class DepartmentList extends AppCompatActivity {

    private ListView lvKhoa;
    private EditText edtTimKhoa;
    private ArrayList<Department> danhSachKhoa;
    private DepartmentAdapter adapter;
    private SinhVienDB db;

    private ActivityResultLauncher<Intent> addDepartmentLauncher;
    private ActivityResultLauncher<Intent> updateDepartmentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        // Toolbar và padding hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            MaterialToolbar toolbar = findViewById(R.id.topAppBar);
            setSupportActionBar(toolbar);
            return insets;
        });

        lvKhoa = findViewById(R.id.lvKhoa);
        edtTimKhoa = findViewById(R.id.edtTimKhoa);
        db = new SinhVienDB(this, "QLKhoa", null, 1);

        // Load danh sách khoa
        danhSachKhoa = new ArrayList<>(db.getAllKhoa());
        if (danhSachKhoa.isEmpty()) {
            khoiTaoDuLieu();
            danhSachKhoa = new ArrayList<>(db.getAllKhoa());
        }

        adapter = new DepartmentAdapter(this, R.layout.cell_department_layout, danhSachKhoa);
        lvKhoa.setAdapter(adapter);

        // Thêm mới khoa
        addDepartmentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Department newDept = (Department) result.getData().getSerializableExtra("newDept");
                        if (newDept != null) {
                            db.themKhoa(newDept);
                            danhSachKhoa.add(newDept);
                            adapter.refreshData();
                            edtTimKhoa.getText().clear();
                        }
                    }
                }
        );

        // Cập nhật khoa
        updateDepartmentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Department updatedDept = (Department) result.getData().getSerializableExtra("khoa_updated");
                        if (updatedDept != null) {
                            for (int i = 0; i < danhSachKhoa.size(); i++) {
                                if (danhSachKhoa.get(i).getMaKhoa().equals(updatedDept.getMaKhoa())) {
                                    danhSachKhoa.set(i, updatedDept);
                                    db.suaKhoa(updatedDept);
                                    break;
                                }
                            }
                            adapter.refreshData();
                        }
                    }
                }
        );

        lvKhoa.setOnItemClickListener((parent, view, position, id) -> {
            Department dept = danhSachKhoa.get(position);
            Intent intent = new Intent(DepartmentList.this, DepartmentInforActivity.class);
            intent.putExtra("k_khoa", dept);
            updateDepartmentLauncher.launch(intent);
        });

        // Filter theo EditText
        edtTimKhoa.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString().trim().toLowerCase());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.them_moi_khoa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.itemTMKhoa) {
            Intent intent = new Intent(this, AddDepartment.class);
            addDepartmentLauncher.launch(intent);
            return true;
        } else if (item.getItemId() == R.id.itemXoaKhoa) {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa tất cả khoa?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        db.xoaTatCaKhoa();
                        danhSachKhoa.clear();
                        adapter.refreshData();
                        Toast.makeText(this, "Đã xóa tất cả khoa!", Toast.LENGTH_SHORT).show();
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
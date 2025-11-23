package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.List;

import database.SinhVienDB;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText edtTimKiem;
    private ArrayList<SinhVien> danhSachSinhVien;
    private SinhVienAdapter sinhVienAdapter;
    private SinhVienDB db;

    private ActivityResultLauncher<Intent> addStudentLauncher;
    private ActivityResultLauncher<Intent> updateStudentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            MaterialToolbar toolbar = findViewById(R.id.topAppBar);
            setSupportActionBar(toolbar);
            return insets;
        });

        listView = findViewById(R.id.listView);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        db = new SinhVienDB(this, "QLSinhVien", null, 1);

        // Lấy dữ liệu từ database
        danhSachSinhVien = new ArrayList<>(db.getAllSinhVien());

        // Nếu database rỗng, thêm dữ liệu mặc định
        if (danhSachSinhVien.isEmpty()) {
            khoiTaoDuLieu();
            danhSachSinhVien = new ArrayList<>(db.getAllSinhVien());
        }

        // Tạo adapter
        sinhVienAdapter = new SinhVienAdapter(this, R.layout.cell_student_layout, danhSachSinhVien);
        listView.setAdapter(sinhVienAdapter);

        // Launcher thêm sinh viên
        addStudentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        SinhVien newSV = (SinhVien) result.getData().getSerializableExtra("newSV");
                        if (newSV != null) {
                            danhSachSinhVien.add(newSV);
                            sinhVienAdapter.refresh();
                            edtTimKiem.getText().clear();
                        }
                    }
                }
        );

        // Launcher cập nhật sinh viên
        updateStudentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Load lại dữ liệu từ DB
                        danhSachSinhVien.clear();
                        danhSachSinhVien.addAll(db.getAllSinhVien());
                        sinhVienAdapter.refresh();
                    }
                }
        );

        // Click vào 1 sinh viên -> mở StudentInforActivity
        listView.setOnItemClickListener((parent, view, position, id) -> {
            SinhVien sv = danhSachSinhVien.get(position);
            Intent intent = new Intent(MainActivity.this, StudentInforActivity.class);
            intent.putExtra("k_sinhvien", sv);
            updateStudentLauncher.launch(intent);
        });

        // Search filter
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sinhVienAdapter.filter(s.toString().toLowerCase().trim());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_moi_sv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemThemMoiSV) {
            // Thêm sinh viên mới
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            addStudentLauncher.launch(intent);
            return true;
        }

        else if (item.getItemId() == R.id.itemXoaTatCa) {
            // Xóa tất cả sinh viên
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa tất cả sinh viên?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        db.xoaTatCaSinhVien();        // Xóa toàn bộ trong DB
                        danhSachSinhVien.clear();      // Xóa danh sách trong MainActivity
                        sinhVienAdapter.refresh();     // Cập nhật adapter
                        Toast.makeText(MainActivity.this, "Đã xóa tất cả sinh viên!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
            return true;
        }
        else if(item.getItemId() == R.id.itemAllSV) {
            // Lay tat ca sinh vien tu DB
            danhSachSinhVien.clear();
            danhSachSinhVien.addAll(db.getAllSinhVien());
            sinhVienAdapter.refresh();
        }

        else if (item.getItemId() == R.id.itemLocKhoa) {
            // Hiển thị hộp thoại nhập tên/mã khoa
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lọc theo khoa");

            final EditText input = new EditText(this);
            input.setHint("Nhập tên hoặc mã khoa");
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String keyword = input.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    List<SinhVien> filteredList = db.getSinhVienTheoKhoa(keyword);
                    danhSachSinhVien.clear();
                    danhSachSinhVien.addAll(filteredList);
                    sinhVienAdapter.refresh();
                } else {
                    Toast.makeText(this, "Bạn chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> {
                // Nếu hủy, hiển thị lại toàn bộ danh sách
                danhSachSinhVien.clear();
                danhSachSinhVien.addAll(db.getAllSinhVien());
                sinhVienAdapter.refresh();
            });

            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Thêm dữ liệu mặc định vào DB
    private void khoiTaoDuLieu() {
        SinhVien[] defaultSVs = new SinhVien[]{
                new SinhVien("SV001", "Nguyen Van A", "0901234567", "vana01@gmail.com", "01/01/2003", "Cong Nghe Phan Mem", "Nam", "Âm nhạc"),
                new SinhVien("SV002", "Tran Thi B", "0902345678", "thib02@gmail.com", "05/02/2003", "Khoa Hoc May Tinh", "Nữ", "Đọc sách"),
                new SinhVien("SV003", "Le Van C", "0903456789", "vanc03@gmail.com", "12/03/2002", "Big Data", "Nam", "Thể thao"),
                new SinhVien("SV004", "Pham Thi D", "0904567890", "thid04@gmail.com", "25/04/2003", "Tri Tue Nhan Tao", "Nữ", "Chơi game"),
                new SinhVien("SV005", "Hoang Van E", "0905678901", "vane05@gmail.com", "30/05/2002", "He Thong Thong Tin", "Nam", "Âm nhạc"),
                new SinhVien("SV006", "Vo Thi F", "0906789012", "thif06@gmail.com", "14/06/2003", "Mang May Tinh", "Nữ", "Đọc sách"),
                new SinhVien("SV007", "Ngo Van G", "0907890123", "vang07@gmail.com", "20/07/2003", "An Toan Thong Tin", "Nam", "Thể thao"),
                new SinhVien("SV008", "Do Thi H", "0908901234", "thih08@gmail.com", "18/08/2002", "Khoa Hoc May Tinh", "Nữ", "Chơi game"),
                new SinhVien("SV009", "Huynh Van I", "0909012345", "vani09@gmail.com", "10/09/2003", "Cong Nghe Phan Mem", "Nam", "Âm nhạc"),
                new SinhVien("SV010", "Phan Thi K", "0900123456", "thik10@gmail.com", "11/10/2002", "Cong Nghe Phan Mem", "Nữ", "Đọc sách")
        };
        for (SinhVien sv : defaultSVs) {
            db.themSV(sv);
        }
    }
}
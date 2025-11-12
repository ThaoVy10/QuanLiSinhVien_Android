package cr424s.hothithaovy.studentmanager;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import database.SinhVienDB;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<SinhVien> danhSachSinhVien;
    EditText edtTimKiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtTimKiem = findViewById(R.id.edtTimKiem);
        khoiTaoDuLieu();
        listView = findViewById(R.id.listView);
        SinhVienAdapter sinhVienAdapter = new SinhVienAdapter(MainActivity.this,R.layout.cell_student_layout,danhSachSinhVien);
        listView.setAdapter(sinhVienAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SinhVien sinhVien = (SinhVien) sinhVienAdapter.getItem(position);
                Toast.makeText(MainActivity.this, sinhVien.getHoTen(),LENGTH_SHORT);
                Intent intent = new Intent(MainActivity.this, StudentInforActivity.class);
                intent.putExtra("k_sinhvien",sinhVien);
                startActivity(intent);
            }
        });
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().toLowerCase().trim();
                ArrayList<SinhVien> danhSachLoc = new ArrayList<>();

                for (SinhVien sv : danhSachSinhVien) {
                    if (sv.getHoTen().toLowerCase().contains(keyword)
                            || sv.getMaSV().toLowerCase().contains(keyword)
                            || sv.getKhoa().toLowerCase().contains(keyword)) {
                        danhSachLoc.add(sv);
                    }
                }

                SinhVienAdapter adapterMoi = new SinhVienAdapter(MainActivity.this,
                        R.layout.cell_student_layout, danhSachLoc);
                listView.setAdapter(adapterMoi);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_moi,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selected = item.getItemId();
        if(selected == R.id.itemThemMoi) {
            Toast.makeText(MainActivity.this, "Tinh tong", LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void khoiTaoDuLieu() {
        danhSachSinhVien = new ArrayList<>();

        danhSachSinhVien.add(new SinhVien("Nguyễn Văn A", "SV001", "0901234567", "vana01@gmail.com", "01/01/2003", "Công Nghệ Phần Mềm", "Nam", "Âm nhạc"));
        danhSachSinhVien.add(new SinhVien("Trần Thị B", "SV002", "0902345678", "thib02@gmail.com", "05/02/2003", "Khoa Học Máy Tính", "Nữ", "Đọc sách"));
        danhSachSinhVien.add(new SinhVien("Lê Văn C", "SV003", "0903456789", "vanc03@gmail.com", "12/03/2002", "Big Data", "Nam", "Thể thao"));
        danhSachSinhVien.add(new SinhVien("Phạm Thị D", "SV004", "0904567890", "thid04@gmail.com", "25/04/2003", "Trí Tuệ Nhân Tạo", "Nữ", "Chơi game"));
        danhSachSinhVien.add(new SinhVien("Hoàng Văn E", "SV005", "0905678901", "vane05@gmail.com", "30/05/2002", "Hệ Thống Thông Tin", "Nam", "Âm nhạc"));
        danhSachSinhVien.add(new SinhVien("Võ Thị F", "SV006", "0906789012", "thif06@gmail.com", "14/06/2003", "Mạng Máy Tính", "Nữ", "Đọc sách"));
        danhSachSinhVien.add(new SinhVien("Ngô Văn G", "SV007", "0907890123", "vang07@gmail.com", "20/07/2003", "An Toàn Thông Tin", "Nam", "Thể thao"));
        danhSachSinhVien.add(new SinhVien("Đỗ Thị H", "SV008", "0908901234", "thih08@gmail.com", "18/08/2002", "Khoa Học Máy Tính", "Nữ", "Chơi game"));
        danhSachSinhVien.add(new SinhVien("Huỳnh Văn I", "SV009", "0909012345", "vani09@gmail.com", "10/09/2003", "Công Nghệ Phần Mềm", "Nam", "Âm nhạc"));
        danhSachSinhVien.add(new SinhVien("Phan Thị K", "SV010", "0900123456", "thik10@gmail.com", "11/10/2002", "Công Nghệ Phần Mềm", "Nữ", "Đọc sách"));
    }


}
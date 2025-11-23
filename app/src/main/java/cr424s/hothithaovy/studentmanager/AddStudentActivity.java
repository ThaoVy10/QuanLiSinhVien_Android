package cr424s.hothithaovy.studentmanager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import database.SinhVienDB;

public class AddStudentActivity extends AppCompatActivity {

    EditText edtHoTen, edtSDT, edtMaSV, edtEmail, edtNgaySinh;
    CheckBox cbAmNhac, cbDocSach, cbTheThao, cbChoiGame;
    RadioGroup rdGroup;
    RadioButton rdNam, rdNu;
    Spinner spnKhoa;
    ImageView image;
    private static final int PICK_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;

    String[] khoa = new String[]{
            "Cong nghe phan mem",
            "An ninh mang",
            "Tri tue nhan tao",
            "Big Data"
    };

    Button bntNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom);
            return insets;
        });

        spnKhoa = findViewById(R.id.spinnerKhoa);
        // Hình ảnh
        image = findViewById(R.id.imgUser);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] options = {"Chọn từ Thư Viện", "Chụp Ảnh"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddStudentActivity.this);
                builder.setTitle("Chọn ảnh đại diện");
                builder.setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        pickImage();
                    } else {
                        takePhoto();
                    }
                })
                        .show();
            }
            private void pickImage() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }

            private void takePhoto() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        edtHoTen = findViewById(R.id.edtHoTen);
        edtMaSV = findViewById(R.id.edtMaSV);
        edtSDT = findViewById(R.id.edtSDT);
        edtEmail = findViewById(R.id.edtEmail);
        edtNgaySinh = findViewById(R.id.edtDate);

        rdNam = findViewById(R.id.rdNam);
        rdNu = findViewById(R.id.rdNu);

        cbAmNhac = findViewById(R.id.cbAmNhac);
        cbDocSach = findViewById(R.id.cbDocSach);
        cbTheThao = findViewById(R.id.cbTheThao);
        cbChoiGame = findViewById(R.id.cbChoiGame);

        bntNhap = findViewById(R.id.btnNhap);

        ArrayAdapter<String> adapterKhoa = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                khoa
        );
        adapterKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKhoa.setAdapter(adapterKhoa);

        bntNhap.setOnClickListener(v -> {SinhVienDB db = new SinhVienDB(AddStudentActivity.this,
                    "QLSinhVien", null, 1);

            SinhVien sv = new SinhVien();

            String maSV = edtMaSV.getText().toString().trim();
            String hoTen = edtHoTen.getText().toString().trim();
            String ngaySinh = edtNgaySinh.getText().toString().trim();
            String sdt = edtSDT.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String gioiTinh = rdNam.isChecked() ? "Nam" : "Nữ";
            String tenKhoa = spnKhoa.getSelectedItem().toString();

            String soThich = "";
            if (cbAmNhac.isChecked()) soThich += "Âm nhạc, ";
            if (cbDocSach.isChecked()) soThich += "Đọc sách, ";
            if (cbTheThao.isChecked()) soThich += "Thể thao, ";
            if (cbChoiGame.isChecked()) soThich += "Chơi game, ";

            if (soThich.endsWith(", ")) {
                soThich = soThich.substring(0, soThich.length() - 2);
            }

            if (maSV.isEmpty() || hoTen.isEmpty() || ngaySinh.isEmpty() || sdt.isEmpty() || email.isEmpty() ||
                gioiTinh.isEmpty() || tenKhoa.isEmpty() || soThich.isEmpty()) {
                Toast.makeText(AddStudentActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra mã SV đã tồn tại
            if (db.kiemTraMaSV(maSV)) {
                Toast.makeText(AddStudentActivity.this,
                        "Mã SV đã tồn tại. Vui lòng nhập lại!",
                        Toast.LENGTH_SHORT).show();
                edtMaSV.setText(""); // xóa ô Mã SV
                edtMaSV.requestFocus(); // focus vào ô Mã SV
                return;
            }

            // Gán dữ liệu vào đối tượng SinhVien
            sv.setMaSV(maSV);
            sv.setHoTen(hoTen);
            sv.setNgaySinh(ngaySinh);
            sv.setSdt(sdt);
            sv.setEmail(email);
            sv.setGioitinh(gioiTinh);
            sv.setKhoa(tenKhoa);
            sv.setSothich(soThich);

            // THÊM VÀO DATABASE
            long kq = db.themSV(sv);

            if (kq > 0) {
                Toast.makeText(AddStudentActivity.this, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show();
                Intent result = new Intent();
                result.putExtra("newSV", sv); // gửi đối tượng qua
                setResult(RESULT_OK, result);
                finish();
            } else {
                Toast.makeText(AddStudentActivity.this, "Thêm thất bại! Mã SV có thể đã tồn tại.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
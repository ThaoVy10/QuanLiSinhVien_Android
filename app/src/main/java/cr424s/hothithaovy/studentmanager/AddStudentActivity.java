package cr424s.hothithaovy.studentmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddStudentActivity extends AppCompatActivity {

    Spinner spinner;
    Button bntNhap;
    EditText edtHoTen,edtSDT,edtMaSV,edtEmail,edtDate;
    CheckBox cbAmNhac, cbDocSach, cbTheThao, cbChoiGame;
    RadioGroup rdGroup;

    String[] khoa = new String[] {
            "Cong nghe phan mem",
            "An ninh mang",
            "Tri tue nhan tao",
            "Big Data"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
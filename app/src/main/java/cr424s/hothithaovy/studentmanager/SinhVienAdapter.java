package cr424s.hothithaovy.studentmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SinhVienAdapter extends BaseAdapter {
    Context context;
    int resouceLayout;
    ArrayList<SinhVien> danhSachSinhVien;

    public SinhVienAdapter(Context context, int resouceLayout, ArrayList<SinhVien> danhSachSinhVien) {
        this.context = context;
        this.resouceLayout = resouceLayout;
        this.danhSachSinhVien = danhSachSinhVien;
    }

    @Override
    public int getCount() {
        return danhSachSinhVien.size();
    }

    @Override
    public Object getItem(int position) {
        return danhSachSinhVien.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SinhVienView sinhVienViewv = new SinhVienView();
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cell_student_layout,null);
            sinhVienViewv.tvHoTen = convertView.findViewById(R.id.tvHoten);
            sinhVienViewv.tvSdt = convertView.findViewById(R.id.tvSDT);
            sinhVienViewv.tvMaSV = convertView.findViewById(R.id.tvMaSV);
            convertView.setTag(sinhVienViewv);

        }
        else {
            convertView.getTag();
        }
        SinhVien sinhVien = danhSachSinhVien.get(position);
        sinhVienViewv.tvHoTen.setText(sinhVien.getHoTen());
        sinhVienViewv.tvSdt.setText(sinhVien.getSdt());
        sinhVienViewv.tvMaSV.setText(sinhVien.getMaSV());
        return convertView;
    }

    class SinhVienView {
        TextView tvHoTen;
        TextView tvSdt;
        TextView tvMaSV;
    }
}

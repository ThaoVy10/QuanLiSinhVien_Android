package cr424s.hothithaovy.studentmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class DepartmentAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<SinhVien> displayList;
    private List<SinhVien> fullList;

    public DepartmentAdapter(Context context, int layout, ArrayList<SinhVien> list) {
        this.context = context;
        this.layout = layout;

        this.fullList = list;
        this.displayList = new ArrayList<>(list);
    }
    @Override
    public int getCount() {
        return displayList.size();
    }

    @Override
    public Object getItem(int position) {
        return displayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView tvTenKhoa, tvMaKhoa, tvSDTKhoa;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DepartmentAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);
            holder = new DepartmentAdapter.ViewHolder();
            holder.tvTenKhoa = convertView.findViewById(R.id.tenKhoa);
            holder.tvMaKhoa = convertView.findViewById(R.id.maKhoa);
            holder.tvSDTKhoa = convertView.findViewById(R.id.sdtKhoa);
            convertView.setTag(holder);
        } else {
            holder = (DepartmentAdapter.ViewHolder) convertView.getTag();
        }

        SinhVien sv = displayList.get(position);
        holder.tvTenKhoa.setText(sv.getHoTen());
        holder.tvMaKhoa.setText(sv.getMaSV());
        holder.tvSDTKhoa.setText(sv.getSdt());

        return convertView;
    }

    public void refresh() {
        displayList.clear();
        displayList.addAll(fullList);
        notifyDataSetChanged();
    }
}

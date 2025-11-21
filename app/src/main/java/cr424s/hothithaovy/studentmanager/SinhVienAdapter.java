package cr424s.hothithaovy.studentmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SinhVienAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<SinhVien> displayList;   // danh sách đang hiển thị
    private List<SinhVien> fullList;      // danh sách gốc

    public SinhVienAdapter(Context context, int layout, ArrayList<SinhVien> list) {
        this.context = context;
        this.layout = layout;

        this.fullList = list;                     // trỏ trực tiếp danh sách ngoài Main
        this.displayList = new ArrayList<>(list); // tạo bản copy để hiển thị + filter
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
        TextView tvHoTen, tvMaSV, tvSDT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.tvHoTen = convertView.findViewById(R.id.tvHoten);
            holder.tvMaSV = convertView.findViewById(R.id.tvMaSV);
            holder.tvSDT = convertView.findViewById(R.id.tvSDT);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SinhVien sv = displayList.get(position);
        holder.tvHoTen.setText(sv.getHoTen());
        holder.tvMaSV.setText(sv.getMaSV());
        holder.tvSDT.setText(sv.getSdt());

        return convertView;
    }

    // Filter
    public void filter(String keyword) {
        displayList.clear();

        if (keyword.isEmpty()) {
            displayList.addAll(fullList);
        } else {
            keyword = keyword.toLowerCase();
            for (SinhVien sv : fullList) {
                if (sv.getMaSV().toLowerCase().contains(keyword)
                        || sv.getHoTen().toLowerCase().contains(keyword)) {
                    displayList.add(sv);
                }
            }
        }

        notifyDataSetChanged();
    }

    public void refresh() {
        displayList.clear();
        displayList.addAll(fullList);
        notifyDataSetChanged();
    }
}
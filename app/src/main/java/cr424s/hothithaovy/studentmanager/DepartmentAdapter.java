package cr424s.hothithaovy.studentmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Department> displayList;
    private List<Department> fullList;

    public DepartmentAdapter(Context context, int layout, ArrayList<Department> list) {
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.tvTenKhoa = convertView.findViewById(R.id.tenKhoa);
            holder.tvMaKhoa = convertView.findViewById(R.id.maKhoa);
            holder.tvSDTKhoa = convertView.findViewById(R.id.sdtKhoa);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Department dp = displayList.get(position);
        holder.tvTenKhoa.setText(dp.getTenKhoa());
        holder.tvMaKhoa.setText(dp.getMaKhoa());
        holder.tvSDTKhoa.setText(dp.getSdt());

        return convertView;
    }

    public void filter(String keyword) {
        displayList.clear();

        if (keyword.isEmpty()) {
            displayList.addAll(fullList);
        } else {
            keyword = keyword.toLowerCase();
            for (Department dp : fullList) {
                if (dp.getMaKhoa().toLowerCase().contains(keyword)
                        || dp.getTenKhoa().toLowerCase().contains(keyword)) {
                    displayList.add(dp);
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
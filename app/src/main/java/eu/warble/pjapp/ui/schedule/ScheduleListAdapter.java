package eu.warble.pjapp.ui.schedule;


import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.ZajeciaItem;

public class ScheduleListAdapter extends BaseAdapter {
    private List<ZajeciaItem> lessons;
    private LayoutInflater inflater;
    private Context context;

    public ScheduleListAdapter(Context context, List<ZajeciaItem> lessons){
        this.context = context;
        this.lessons = new ArrayList<>(8);
        this.lessons.addAll(lessons);
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateList(List<ZajeciaItem> update){
        this.lessons.clear();
        lessons.addAll(update);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public ZajeciaItem getItem(int i) {
        return lessons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.list_item_schedule, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.typZajec = view.findViewById(R.id.schedule_lessonType);
            viewHolder.time = view.findViewById(R.id.schedule_lessonTime);
            viewHolder.name = view.findViewById(R.id.schedule_lessonName);
            viewHolder.location = view.findViewById(R.id.schedule_lessonLocation);
            viewHolder.typeDot = view.findViewById(R.id.schedule_type_dot);
            viewHolder.leftCornerColor = view.findViewById(R.id.left_corner_color);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ZajeciaItem item = getItem(i);

        String time = item.getDataRoz().substring(11, item.getDataRoz().length()) + " - "
                + item.getDataZak().substring(11, item.getDataRoz().length());
        String name = item.getKod() + " - " + item.getNazwa();
        String location = context.getString(R.string.building) + " " + item.getBudynek() + ", " + item.getNazwaSali();
        int color = getTypeColor(item.getTypZajec());

        viewHolder.time.setText(time);
        viewHolder.name.setText(name);
        viewHolder.location.setText(location);
        viewHolder.typZajec.setText(item.getTypZajec().toUpperCase());
        viewHolder.typZajec.setTextColor(color);
        viewHolder.typeDot.setColorFilter(color, PorterDuff.Mode.SRC);
        viewHolder.leftCornerColor.setBackgroundColor(color);

        return view;
    }

    @Override
    public boolean isEmpty() {
        return lessons.isEmpty();
    }

    private int getTypeColor(String lessonType){
        switch (lessonType){
            case "Ćwiczenia":
                return context.getResources().getColor(R.color.lessonType1);
            case "Wykład":
                return context.getResources().getColor(R.color.lessonType2);
            case "Egzamin":
                return context.getResources().getColor(R.color.lessonType3);
            default:
                return context.getResources().getColor(R.color.lessonType4);
        }
    }

    private static class ViewHolder {
        View leftCornerColor;
        TextView typZajec;
        TextView time;
        TextView name;
        TextView location;
        ImageView typeDot;
    }
}

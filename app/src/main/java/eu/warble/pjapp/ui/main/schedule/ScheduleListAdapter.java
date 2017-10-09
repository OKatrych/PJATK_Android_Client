package eu.warble.pjapp.ui.main.schedule;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            viewHolder.typZajec = (TextView) view.findViewById(R.id.schedule_lessonType);
            viewHolder.time = (TextView) view.findViewById(R.id.schedule_lessonTime);
            viewHolder.name = (TextView) view.findViewById(R.id.schedule_lessonName);
            viewHolder.location = (TextView) view.findViewById(R.id.schedule_lessonLocation);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ZajeciaItem item = getItem(i);

        String time = item.getDataRoz().substring(11, item.getDataRoz().length()) + " - "
                + item.getDataZak().substring(11, item.getDataRoz().length());
        String name = item.getKod() + " - " + item.getNazwa();
        String location = context.getString(R.string.building) + " " + item.getBudynek() + ", " + item.getNazwaSali();

        viewHolder.typZajec.setText(item.getTypZajec());
        viewHolder.time.setText(time);
        viewHolder.name.setText(name);
        viewHolder.location.setText(location);

        return view;
    }

    @Override
    public boolean isEmpty() {
        return lessons.isEmpty();
    }

    private static class ViewHolder {
        TextView typZajec;
        TextView time;
        TextView name;
        TextView location;
        int position;
    }
}

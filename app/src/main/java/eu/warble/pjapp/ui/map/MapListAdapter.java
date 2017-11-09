package eu.warble.pjapp.ui.map;


import android.content.Context;
import android.graphics.PorterDuff;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.MapListItem;

public class MapListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private SparseArray<MapListItem> maps;

    MapListAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initMaps();
    }

    @Override
    public int getCount() {
        return maps.size();
    }

    @Override
    public MapListItem getItem(int i) {
        return maps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.list_item_maps, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mapName = view.findViewById(R.id.map_name);
            viewHolder.tag = view.findViewById(R.id.building_tag);
            viewHolder.buildingTagColor = view.findViewById(R.id.round_view);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        MapListItem item = getItem(i);
        viewHolder.mapName.setText(item.getName());
        viewHolder.tag.setText(item.getBuildingTag());
        viewHolder.buildingTagColor.getBackground().setColorFilter(getBuildingColor(item.getBuildingTag()), PorterDuff.Mode.SRC);
        return view;
    }

    private void initMaps(){
        maps = new SparseArray<>();
        maps.put(0, new MapListItem(context.getString(R.string.basement), "A", "_hfEEWbSOT0", "eFKBL1M2G_g"));
        maps.put(1, new MapListItem(context.getString(R.string.ground_floor), "A", "_hfEEWbSOT0", "eFKBL1M2G_g"));
        maps.put(2, new MapListItem(context.getString(R.string.first_floor), "A", "_hfEEWbSOT0", "eFKBL1M2G_g"));
        maps.put(3, new MapListItem(context.getString(R.string.second_floor), "A", "_hfEEWbSOT0", "eFKBL1M2G_g"));
        maps.put(4, new MapListItem(context.getString(R.string.third_floor), "A", "_hfEEWbSOT0", "eFKBL1M2G_g"));
        maps.put(5, new MapListItem(context.getString(R.string.basement), "C", "_hfEEWbSOT0", "eFKBL1M2G_g"));
        maps.put(6, new MapListItem(context.getString(R.string.first_floor), "C", "_hfEEWbSOT0", "eFKBL1M2G_g"));
        maps.put(7, new MapListItem(context.getString(R.string.third_floor), "G", "_hfEEWbSOT0", "eFKBL1M2G_g"));
    }

    private int getBuildingColor(String buildingTag){
        switch (buildingTag){
            case "A":
                return context.getResources().getColor(R.color.buildingA);
            case "C":
                return context.getResources().getColor(R.color.buildingC);
            case "G":
                return context.getResources().getColor(R.color.buildingG);
            default:
                return context.getResources().getColor(R.color.building);
        }
    }

    private static class ViewHolder {
        View buildingTagColor;
        TextView tag;
        TextView mapName;
    }
}

package eu.warble.pjapp.ui.map;


import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.indoorway.android.common.sdk.model.IndoorwayBuildingParameters;
import com.indoorway.android.common.sdk.model.IndoorwayObjectId;

import java.util.ArrayList;
import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.MapListItem;

public class MapListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<IndoorwayBuildingParameters> buildingList;
    private List<MapListItem> mapList;

    MapListAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        buildingList = new ArrayList<>();
        mapList = new ArrayList<>();
    }

    public void updateList(List<IndoorwayBuildingParameters> newData) {
        buildingList.clear();
        buildingList.addAll(newData);
        loadMaps();
    }

    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public MapListItem getItem(int i) {
        return mapList.get(i);
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

    private void loadMaps(){
        if (mapList != null) mapList = null;
        mapList = new ArrayList<>();
        for (IndoorwayBuildingParameters building : buildingList) {
            for (IndoorwayObjectId map : building.getMaps()){
                MapListItem item = new MapListItem(
                        map.getName(), building.getName(), map.getUuid(), building.getUuid());
                mapList.add(item);
            }
        }
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

package eu.warble.pjapp.ui.map;


import android.content.Context;

import com.indoorway.android.common.sdk.model.IndoorwayObjectParameters;
import com.indoorway.android.map.sdk.config.MapViewConfig;

import org.jetbrains.annotations.NotNull;

import eu.warble.pjapp.R;

public class PjMapViewConfig extends MapViewConfig {

    public PjMapViewConfig(@NotNull Context applicationContext) {
        super(applicationContext);
    }

    /*@NotNull
    @Override
    public MapViewConfig setCustomBackgroundColor(String roomType, int color) {
        if (roomType.equals("inaccessible")){
            return super.setCustomBackgroundColor(
                    roomType, getApplicationContext().getResources().getColor(R.color.indoorway_inaccessible_color));
        }
        return super.setCustomBackgroundColor(roomType, color);
    }*/

    @Override
    public int getRoomBackgroundColor(@NotNull IndoorwayObjectParameters obj) {
        if ("inaccessible".equals(obj.getType())){
            return getApplicationContext().getResources().getColor(R.color.indoorway_inaccessible_color);
        }
        return getApplicationContext().getResources().getColor(R.color.indoorway_map_default_room_background);
    }

    @Override
    public int getRoomOutlineColor(@NotNull IndoorwayObjectParameters obj) {
        return getApplicationContext().getResources().getColor(R.color.indoorway_map_default_room_outline);
    }
}

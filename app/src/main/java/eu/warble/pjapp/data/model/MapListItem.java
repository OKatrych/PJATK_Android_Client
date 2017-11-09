package eu.warble.pjapp.data.model;


public class MapListItem {

    private String name;
    private String buildingTag;
    private String mapUUID;
    private String buildingUUID;

    public MapListItem(String name, String buildingTag, String mapUUID, String buildingUUID) {
        this.name = name;
        this.buildingTag = buildingTag;
        this.mapUUID = mapUUID;
        this.buildingUUID = buildingUUID;
    }

    public String getName() {
        return name;
    }

    public String getBuildingTag() {
        return buildingTag;
    }

    public String getMapUUID() {
        return mapUUID;
    }

    public String getBuildingUUID() {
        return buildingUUID;
    }
}

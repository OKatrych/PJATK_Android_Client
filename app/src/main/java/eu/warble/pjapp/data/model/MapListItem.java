package eu.warble.pjapp.data.model;


public class MapListItem {

    private String name;
    private String buildingTag;
    private String mapUUID;
    private String buildingUUID;

    public MapListItem(String name, String buildingName, String mapUUID, String buildingUUID) {
        this.name = name;
        this.buildingTag = createBuildingTag(buildingName);
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

    private String createBuildingTag(String buildingName){
        return buildingName.substring(buildingName.length()-1, buildingName.length());
    }
}

package gr.teilar.scrooge.Core;

/**
 * Created by Mitsos on 18/12/16.
 */

public class ExpenseLocation {
    private long locationId;
    private String locationName;
    private String locationLatitude;
    private String locationLongitude;

    public ExpenseLocation(String locationName, String locationLatitude, String locationLongitude) {
        this.locationName = locationName;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public long getLocationId() {
        return locationId;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationId(long id) {
        this.locationId = id;
    }
}

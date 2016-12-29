package gr.teilar.scrooge.Core;

/**
 * Created by Mitsos on 18/12/16.
 */

public class ExpenseLocation {
    private long locationId;
    private String locationName;
    private double locationLatitude;
    private double locationLongitude;

    public ExpenseLocation(String locationName, double locationLatitude, double locationLongitude) {
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

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationId(long id) {
        this.locationId = id;
    }
}

package hk.edu.hkmu.fitnesswalkingtracksapps;

public class Data {
    private String title ;
    private String district ;
    private String Route ;
    private String accessway ;
    private String mapurl ;
    private String latitude ;
    private String longitude ;

    public Data(String title, String district, String Route, String accessway, String mapurl, String latitude, String longitude) {
        this.title = title;
        this.district = district;
        this.Route = Route;
        this.accessway = accessway;
        this.mapurl = mapurl;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Data(String title, String district, String Route) {
        this.title = title;
        this.district = district;
        this.Route = Route;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public String getAccessway() {
        return accessway;
    }

    public void setAccessway(String accessway) {
        this.accessway = accessway;
    }

    public String getMapurl() {
        return mapurl;
    }

    public void setMapurl(String mapurl) {
        this.mapurl = mapurl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

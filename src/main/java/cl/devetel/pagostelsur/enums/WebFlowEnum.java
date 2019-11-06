package cl.devetel.pagostelsur.enums;

public enum WebFlowEnum {
    REGION_CREATE("/region/create"),
    REGION_UPDATE("/region/update"),
    REGION_DELETE("/region/delete"),
    REGION_LIST("region/list"),
    REGION_FIND("region/find");

    private String url;

    WebFlowEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

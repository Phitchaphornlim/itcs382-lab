package bikeshare;

public enum Station {
    LIBRARY("Library"),
    CANTEEN("Canteen"),
    DORM("Dorm");

    private final String displayName;

    Station(String displayName) {
        this.displayName = displayName;
    }

    public static Station fromInput(String value) {
        for (Station station : values()) {
            if (station.displayName.equalsIgnoreCase(value.trim())) {
                return station;
            }
        }
        throw new IllegalArgumentException("Unknown station.");
    }

    @Override
    public String toString() {
        return displayName;
    }
}

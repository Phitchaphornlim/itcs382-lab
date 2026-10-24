package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {
    public static final String[] BIKE_IDS = {
            "Library-1", "Library-2", "Canteen-1", "Canteen-2", "Dorm-1", "Dorm-2"
    };
    public static final String[] BIKE_STATIONS = {
            "Library", "Library", "Canteen", "Canteen", "Dorm", "Dorm"
    };
    public static final String[] RENTED_BY = new String[BIKE_IDS.length];
    public static final int[] MEMBER_TYPES = new int[BIKE_IDS.length];

    private static final String[] STATIONS = {"Library", "Canteen", "Dorm"};
    private static final Path RIDES_FILE = Path.of("rides.csv");
    private static final String CSV_HEADER =
            "member_id,member_type,bike_id,from_station,to_station,minutes,fee_baht";

    private final Scanner input;

    private Main(Scanner input) {
        this.input = input;
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            new Main(input).run();
        }
    }

    private void run() {
        while (true) {
            System.out.println("1. Rent bike  2. Return bike  3. Quit");
            String command = ask("Choice: ");
            if (command == null || command.equalsIgnoreCase("quit") || command.equals("3")) {
                return;
            }
            if (command.equals("1") || command.equalsIgnoreCase("rent")) {
                handleBike(true);
            } else if (command.equals("2") || command.equalsIgnoreCase("return")) {
                handleBike(false);
            } else {
                System.out.println("Choose rent, return or quit.");
            }
        }
    }

    private void handleBike(boolean renting) {
        String memberId = ask("Member ID: ");
        if (memberId == null || memberId.isBlank()) {
            System.out.println("Member ID cannot be empty.");
            return;
        }

        if (renting) {
            rentBike(memberId);
        } else {
            returnBike(memberId);
        }
    }

    private void rentBike(String memberId) {
        String memberType = ask("Member type (student/staff): ");
        if (memberType == null) {
            return;
        }
        int memberTypeCode;
        if (memberType.equalsIgnoreCase("student")) {
            memberTypeCode = 1;
        } else if (memberType.equalsIgnoreCase("staff")) {
            memberTypeCode = 2;
        } else {
            System.out.println("Member type must be student or staff.");
            return;
        }

        String station = readStation("Station (Library/Canteen/Dorm): ");
        if (station == null) {
            return;
        }
        for (int i = 0; i < BIKE_IDS.length; i++) {
            if (BIKE_STATIONS[i].equals(station) && RENTED_BY[i] == null) {
                RENTED_BY[i] = memberId;
                MEMBER_TYPES[i] = memberTypeCode;
                System.out.println("Rented bike " + BIKE_IDS[i] + ".");
                return;
            }
        }
        System.out.println("No bikes available at " + station + ".");
    }

    private void returnBike(String memberId) {
        int bikeIndex = -1;
        for (int i = 0; i < RENTED_BY.length; i++) {
            if (memberId.equals(RENTED_BY[i])) {
                bikeIndex = i;
                break;
            }
        }
        if (bikeIndex < 0) {
            System.out.println("No bike is rented by that member.");
            return;
        }

        String toStation = readStation("Return station (Library/Canteen/Dorm): ");
        if (toStation == null) {
            return;
        }
        String rideTime = ask("Ride time in minutes: ");
        if (rideTime == null) {
            return;
        }
        int minutes;
        try {
            minutes = Integer.parseInt(rideTime);
            if (minutes < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Enter a positive whole number of minutes.");
            return;
        }

        String memberType = MEMBER_TYPES[bikeIndex] == 1 ? "student" : "staff";
        long fee = (long) (MEMBER_TYPES[bikeIndex] == 1 ? 2 : 3) * minutes;
        try {
            saveRide(memberId, memberType, BIKE_IDS[bikeIndex], BIKE_STATIONS[bikeIndex],
                    toStation, minutes, fee);
        } catch (IOException exception) {
            System.err.println("Could not save ride: " + exception.getMessage());
            return;
        }

        System.out.println("Receipt: bike " + BIKE_IDS[bikeIndex] + ", minutes " + minutes
                + ", fee " + fee + " baht.");
        BIKE_STATIONS[bikeIndex] = toStation;
        RENTED_BY[bikeIndex] = null;
        MEMBER_TYPES[bikeIndex] = 0;
    }

    private String readStation(String prompt) {
        String value = ask(prompt);
        if (value == null) {
            return null;
        }
        for (String station : STATIONS) {
            if (station.equalsIgnoreCase(value)) {
                return station;
            }
        }
        System.out.println("Unknown station.");
        return null;
    }

    private void saveRide(String memberId, String memberType, String bikeId, String fromStation,
                          String toStation, int minutes, long fee) throws IOException {
        boolean headerNeeded = !Files.exists(RIDES_FILE) || Files.size(RIDES_FILE) == 0;
        try (BufferedWriter writer = Files.newBufferedWriter(RIDES_FILE,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (headerNeeded) {
                writer.write(CSV_HEADER);
                writer.newLine();
            }
            writer.write(csv(memberId) + "," + memberType + "," + csv(bikeId) + ","
                    + csv(fromStation) + "," + csv(toStation) + "," + minutes + "," + fee);
            writer.newLine();
        }
    }

    private String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        return input.hasNextLine() ? input.nextLine().trim() : null;
    }
}

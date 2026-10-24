package bikeshare;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private final Scanner input;
    private final BikeShareService service;

    private Main(Scanner input, BikeShareService service) {
        this.input = input;
        this.service = service;
    }

    public static void main(String[] args) {
        RideRepository rides = new CsvRideRepository(Path.of("rides.csv"));
        try (Scanner input = new Scanner(System.in)) {
            new Main(input, new BikeShareService(rides)).run();
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
                rentBike();
            } else if (command.equals("2") || command.equalsIgnoreCase("return")) {
                returnBike();
            } else {
                System.out.println("Choose 1, 2 or quit.");
            }
        }
    }

    private void rentBike() {
        String memberId = ask("Member ID: ");
        if (memberId == null) {
            return;
        }
        MemberType memberType = readMemberType();
        if (memberType == null) {
            return;
        }
        Station station = readStation("Station (Library/Canteen/Dorm): ");
        if (station == null) {
            return;
        }
        try {
            Bike bike = service.rentBike(memberId, memberType, station);
            System.out.println("Rented bike " + bike.id() + ".");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void returnBike() {
        String memberId = ask("Member ID: ");
        if (memberId == null) {
            return;
        }
        Station station = readStation("Return station (Library/Canteen/Dorm): ");
        if (station == null) {
            return;
        }
        Integer minutes = readMinutes();
        if (minutes == null) {
            return;
        }
        try {
            Ride ride = service.returnBike(memberId, station, minutes);
            System.out.println("Receipt: bike " + ride.bikeId() + ", minutes " + ride.minutes()
                    + ", fee " + ride.feeBaht() + " baht.");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println(exception.getMessage());
        } catch (IOException exception) {
            System.err.println("Could not save ride: " + exception.getMessage());
        }
    }

    private MemberType readMemberType() {
        String value = ask("Member type (student/staff): ");
        if (value == null) {
            return null;
        }
        try {
            return MemberType.fromInput(value);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    private Station readStation(String prompt) {
        String value = ask(prompt);
        if (value == null) {
            return null;
        }
        try {
            return Station.fromInput(value);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    private Integer readMinutes() {
        String value = ask("Ride time in minutes: ");
        if (value == null) {
            return null;
        }
        try {
            int minutes = Integer.parseInt(value);
            if (minutes > 0) {
                return minutes;
            }
        } catch (NumberFormatException ignored) {
            // Report invalid input below.
        }
        System.out.println("Enter a positive whole number of minutes.");
        return null;
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        return input.hasNextLine() ? input.nextLine().trim() : null;
    }
}

package bikeshare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class BikeShareService {
    private final List<Bike> bikes = new ArrayList<>();
    private final RideRepository rides;

    BikeShareService(RideRepository rides) {
        this.rides = rides;
        for (Station station : Station.values()) {
            for (int number = 1; number <= 2; number++) {
                bikes.add(new Bike(station + "-" + number, station));
            }
        }
    }

    Bike rentBike(String memberId, MemberType memberType, Station station) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("Member ID cannot be empty.");
        }
        if (memberType == null || station == null) {
            throw new IllegalArgumentException("Member type and station are required.");
        }
        if (findRentedBike(memberId) != null) {
            throw new IllegalStateException("This member already has a bike.");
        }
        Bike bike = bikes.stream()
                .filter(candidate -> candidate.station() == station && candidate.isAvailable())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No bikes available at " + station + "."));
        bike.rentTo(memberId, memberType);
        return bike;
    }

    Ride returnBike(String memberId, Station station, int minutes) throws IOException {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("Member ID cannot be empty.");
        }
        if (station == null || minutes < 1) {
            throw new IllegalArgumentException("Station and a positive ride time are required.");
        }
        Bike bike = findRentedBike(memberId);
        if (bike == null) {
            throw new IllegalStateException("No bike is rented by that member.");
        }
        Ride ride = new Ride(memberId, bike.memberType(), bike.id(), bike.station(), station,
                minutes, bike.memberType().feeFor(minutes));
        rides.save(ride);
        bike.returnTo(station);
        return ride;
    }

    private Bike findRentedBike(String memberId) {
        return bikes.stream()
                .filter(bike -> !bike.isAvailable() && bike.isRentedBy(memberId))
                .findFirst()
                .orElse(null);
    }
}

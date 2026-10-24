package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

final class CsvRideRepository implements RideRepository {
    private static final String HEADER =
            "member_id,member_type,bike_id,from_station,to_station,minutes,fee_baht";
    private final Path file;

    CsvRideRepository(Path file) {
        this.file = file;
    }

    @Override
    public void save(Ride ride) throws IOException {
        boolean headerNeeded = !Files.exists(file) || Files.size(file) == 0;
        try (BufferedWriter writer = Files.newBufferedWriter(file,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (headerNeeded) {
                writer.write(HEADER);
                writer.newLine();
            }
            writer.write(csv(ride.memberId()) + "," + ride.memberType() + ","
                    + csv(ride.bikeId()) + "," + csv(ride.fromStation().toString()) + ","
                    + csv(ride.toStation().toString()) + "," + ride.minutes() + ","
                    + ride.feeBaht());
            writer.newLine();
        }
    }

    private String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}

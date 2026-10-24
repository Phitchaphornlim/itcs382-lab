package bikeshare;

import java.io.IOException;

interface RideRepository {
    void save(Ride ride) throws IOException;
}

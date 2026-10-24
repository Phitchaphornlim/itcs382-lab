package bikeshare;

record Ride(
        String memberId,
        MemberType memberType,
        String bikeId,
        Station fromStation,
        Station toStation,
        int minutes,
        long feeBaht) {
}

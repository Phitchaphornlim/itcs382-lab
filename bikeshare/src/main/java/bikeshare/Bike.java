package bikeshare;

final class Bike {
    private final String id;
    private Station station;
    private String memberId;
    private MemberType memberType;

    Bike(String id, Station station) {
        this.id = id;
        this.station = station;
    }

    String id() {
        return id;
    }

    Station station() {
        return station;
    }

    boolean isAvailable() {
        return memberId == null;
    }

    boolean isRentedBy(String candidateMemberId) {
        return candidateMemberId.equals(memberId);
    }

    MemberType memberType() {
        return memberType;
    }

    void rentTo(String newMemberId, MemberType newMemberType) {
        memberId = newMemberId;
        memberType = newMemberType;
    }

    void returnTo(Station returnStation) {
        station = returnStation;
        memberId = null;
        memberType = null;
    }
}

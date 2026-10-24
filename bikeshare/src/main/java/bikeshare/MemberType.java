package bikeshare;

public enum MemberType {
    STUDENT(2),
    STAFF(3);

    private final int feePerMinute;

    MemberType(int feePerMinute) {
        this.feePerMinute = feePerMinute;
    }

    public long feeFor(int minutes) {
        return (long) feePerMinute * minutes;
    }

    public static MemberType fromInput(String value) {
        return switch (value.trim().toLowerCase()) {
            case "student" -> STUDENT;
            case "staff" -> STAFF;
            default -> throw new IllegalArgumentException("Member type must be student or staff.");
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

package ru.avantys.sportClubAttendance.model;

public enum MembershipType {
    STANDARD("Стандартный"),
    PREMIUM("Премиум"),
    UNLIMITED("Безлимитный"),
    TIME_LIMITED("Временной"),
    VISIT_LIMITED("Посещаемый");

    private final String description;

    MembershipType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
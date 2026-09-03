package dev.vulnalert.domain;

public enum Severity {
    LOW(1), MEDIUM(2), HIGH(3), CRITICAL(4), UNKNOWN(0);
    private final int rank;
    Severity(int rank) { this.rank = rank; }
    public boolean meets(Severity minimum) { return rank >= minimum.rank; }
}


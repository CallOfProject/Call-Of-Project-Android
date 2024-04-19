package callofproject.dev.androidapp.domain.dto;

public enum NotificationType {
    INFORMATION("Information"),
    WARNING("Warning"),
    ERROR("Error"),
    REQUEST("Request"),
    SUCCESS("Success");

    private final String m_value;

    private NotificationType(String value) {
        this.m_value = value;
    }

    public String getValue() {
        return this.m_value;
    }
}

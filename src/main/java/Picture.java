import java.time.LocalDate;
import java.time.LocalTime;

public class Picture {
    private LocalTime localTime;

    public Picture(LocalTime localTime) throws ChildrenGardenException {
        this.setLocalTime(localTime);
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) throws ChildrenGardenException {
        if (localTime != null) {
            this.localTime = localTime;
        }
        else {
            throw new ChildrenGardenException("Invalid local time");
        }
    }
}

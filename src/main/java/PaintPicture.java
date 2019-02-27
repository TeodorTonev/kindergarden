import java.time.LocalTime;

public class PaintPicture extends Picture {
    public PaintPicture(LocalTime localTime) throws ChildrenGardenException {
        super(localTime);
    }

    @Override
    public String toString() {
        return "[Paint picture " + getLocalTime() + "]";
    }
}

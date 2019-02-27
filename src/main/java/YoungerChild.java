import java.time.LocalTime;
import java.util.Random;

public class YoungerChild extends Child {

    private static final int MAX_TIME_FOR_PAINTING = 3000;
    private static final int MIN_TIME_FOR_PAINTING = 1000;
    public static final int CONSTANT_IN_FORMULA = 1000;

    public YoungerChild(String name, TypeGroup typeGroup, ChildrenGarden childrenGarden) throws PersonException, ChildrenGardenException {
        super(name, typeGroup, childrenGarden);
    }


    public TypeGroup setGroup() throws PersonException {
        return TypeGroup.getTypeGroupForYoungerChildren();
    }

    private void paintPicture() throws ChildrenGardenException {
        try {
            Thread.sleep((new Random().nextInt(MAX_TIME_FOR_PAINTING - MIN_TIME_FOR_PAINTING + CONSTANT_IN_FORMULA) +
                    MIN_TIME_FOR_PAINTING));
            PaintPicture pic = new PaintPicture(LocalTime.now());
            this.getChildrenGarden().addPaintPicture(pic);
            System.out.println(this + " I left a colored picture");
            Teacher teacher = this.getChildrenGarden().getTeacherOfGroup(this.getTypeGroup());
            teacher.updateStatistics(this, pic);
        } catch (InterruptedException e) {
            System.out.println("[Younger child] I will not color");
            return;
        }
    }

    public void run() {
        while (true) {
            try {
                this.startSinging();
                this.paintPicture();
            } catch (ChildrenGardenException e) {
                System.out.println("[" + this + " finished for today]");
                return;
            }
        }
    }
}

import java.time.LocalTime;

public class OlderChildrеn extends Child {

    private int countDrowPicture;

    public OlderChildrеn(String name, TypeGroup typeGroup, ChildrenGarden childrenGarden) throws PersonException, ChildrenGardenException {
        super(name, typeGroup, childrenGarden);
    }


    TypeGroup setGroup() throws PersonException {
        return TypeGroup.getTypeGroupForOlderChildren();
    }

    public void drawPicture() throws ChildrenGardenException {
        System.out.println(this + " Draw picture");
        Picture pic = new Picture(LocalTime.now());
        this.countDrowPicture++;
        Teacher teacher = this.getChildrenGarden().getTeacherOfGroup(this.getTypeGroup());
        teacher.updateStatistics(this, pic);
    }

    public void run() {
        while (true) {
            try {
                this.startSinging();
                this.drawPicture();
            } catch (ChildrenGardenException e) {
                System.out.println("[" + this + " finished for today]");
                e.printStackTrace();
            }
        }
    }





















}

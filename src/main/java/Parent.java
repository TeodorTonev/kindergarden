
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Parent extends Person {

    public static final int MAX_CHILDREN_IN_FAMILY = 3;
    public static final int MAX_TIME_TO_BRING = 3000;
    public static final int MIN_TIME_TO_BRING = 1000;
    public static final int CONSTANT_IN_FORMULA = 1000;
    private ChildrenGarden childrenGarden;
    private List<Child> children;

    private final String[] childrenFirstName = {"Ivan", "Petur", "Georgi", "Strahil", "Guro", "Simo",
            "Simeon", "Vasko", "Vasil", "Christo", "Igor", "Eleonor", "Kosio", "Todor",
            "Brahil", "Vladi", "Svetlio", "Ivailo", "Marin", "Filio"};
    private final String[] childrenLastName = {"Ivanov", "Petrov", "Vladislavov", "Todorov", "Beshirov",
            "Kaloqnov", "Ivailov", "Vasilev", "Vladimirov", "Christov"};

    public Parent(String name, ChildrenGarden childrenGarden) throws PersonException, ChildrenGardenException {
        super(name);
        this.setChildrenGarden(childrenGarden);
        this.children = new ArrayList<Child>(new Random().nextInt(MAX_CHILDREN_IN_FAMILY));
        for (int children = 0; children < new Random().nextInt(MAX_CHILDREN_IN_FAMILY); children++) {
            Child ch = (Math.random() > 0.5) ? new OlderChildrеn(childrenFirstName[new Random().nextInt(childrenFirstName.length)] + " " +
                    childrenLastName[new Random().nextInt(childrenLastName.length)], TypeGroup.getTypeGroupForOlderChildren(),
                    this.childrenGarden) :
                    new YoungerChild(childrenFirstName[new Random().nextInt(childrenFirstName.length)] + " " +
                    childrenLastName[new Random().nextInt(childrenLastName.length)], TypeGroup.getTypeGroupForYoungerChildren(),
                            this.childrenGarden);
            if (this.children.size() < MAX_CHILDREN_IN_FAMILY) {
                this.children.add(ch);
            } else {
                System.out.println(this +  " We are many, we will not multiply");
            }
        }
    }

    public void run() {
        try {
            this.bringAChildren();
        } catch (PersonException | ChildrenGardenException e) {
            e.printStackTrace();
        }
    }

    public void bringAChildren() throws PersonException, ChildrenGardenException {
        try {
            Thread.sleep(new Random().nextInt(MAX_TIME_TO_BRING - MIN_TIME_TO_BRING + CONSTANT_IN_FORMULA) +
                    MIN_TIME_TO_BRING);
            Teacher teacher = this.childrenGarden.getTeacher();
            teacher.distributeChild(new ArrayList<Child>(this.children));
            System.out.println(this + " I took the kids");
        } catch (InterruptedException e) {
            System.out.println(this + " We crash with the kids");
            return;
        }
    }

    @Override
    public String toString() {
        return "[" + this.getName() + "]";
    }

    public void setChildrenGarden(ChildrenGarden childrenGarden) throws PersonException {
        if (childrenGarden != null) {
            this.childrenGarden = childrenGarden;
         } else {
            throw new PersonException("Invalid kinder garden by parent");
        }
    }
}












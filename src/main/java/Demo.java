import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Demo {

    public static final int NUMBER_OF_PARENTS = 60;

    public static void main(String[] args) throws PersonException, ChildrenGardenException {

        try {

            Set<Thread> threads = new HashSet<Thread>();
            ChildrenGarden sunshine = ChildrenGarden.getInstance("Sunshine");
            threads.add(new Thread(sunshine));

            String[] parentFirstNames = {"Ivan", "Petur", "Georgi", "Strahil", "Guro", "Simo",
                    "Simeon", "Vasko", "Vasil", "Christo", "Igor", "Eleonor", "Kosio", "Todor",
                    "Brahil", "Vladi", "Svetlio", "Ivailo", "Marin", "Filio"};
            String[] parentLastNames = {"Ivanov", "Petrov", "Vladislavov", "Todorov", "Beshirov",
                    "Kaloqnov", "Ivailov", "Vasilev", "Vladimirov", "Christov"};

            Set<Parent> parents = new HashSet<Parent>();

            for (int parent = 0; parent < NUMBER_OF_PARENTS; parent++) {
                parents.add (new Parent(parentFirstNames[new Random().nextInt(parentFirstNames.length)] + " " +
                        parentLastNames[new Random().nextInt(parentLastNames.length)], sunshine));
            }

            for (Parent parent : parents) {
                threads.add(new Thread(parent));
            }

            threads.stream().forEach(thread -> thread.start());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

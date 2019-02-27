import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.BlockingDeque;

public class Teacher extends Person {

    public static final int TIME_FOR_STATISTICS = 1000;
    private ChildrenGarden childrenGarden;
    private TypeGroup itsGroup;
    private Set<Statistics> statistics;

    public Teacher(String name, TypeGroup typeGroup, ChildrenGarden childrenGarden) throws PersonException, ChildrenGardenException {
        super(name);
        this.statistics = new HashSet<Statistics>();
        this.setItsGroup(typeGroup);
        this.setChildrenGarden(childrenGarden);
    }

    public ChildrenGarden getChildrenGarden() {
        return childrenGarden;
    }

    public void setChildrenGarden(ChildrenGarden childrenGarden) throws ChildrenGardenException {
        if (childrenGarden != null) {
            this.childrenGarden = childrenGarden;
        } else {
            throw new ChildrenGardenException("Invalid kinder garden");
        }
    }

    public void setItsGroup(TypeGroup itsGroup) throws ChildrenGardenException {
        if (itsGroup != null) {
            this.itsGroup = itsGroup;
        } else {
            throw new ChildrenGardenException("Invalid type group");
        }
    }

    public void updateStatistics(Child child, Picture picture) throws ChildrenGardenException {
        Statistics stat = new Statistics(child.getName(),
                child.getTypeGroup(), child.getId());
        if (this.statistics.contains(stat)) {
            this.statistics.forEach(statistics1 -> {
                if (statistics1.equals(stat)) {
                    try {
                        statistics1.addPicture(picture);
                    } catch (ChildrenGardenException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void distributeChild(List<Child> children) {
        children.forEach(child -> this.childrenGarden.addInChildrenGardenGroup(child));
    }

    public void run() {

        while (true) {
            try {
                Thread.sleep(TIME_FOR_STATISTICS);
                this.createFileWithStatistics();
            } catch (InterruptedException | IOException e) {
                System.out.println(this + " bye bye it's time to go home");
                return;
            }
        }

    }

    private void createFileWithStatistics() throws IOException {
        File dir = new File("src" + File.separator + "files");
        dir.mkdir();
        Set<Statistics> sortedStatistics = new TreeSet<Statistics>(this.statistics);
        try(Writer writer = new FileWriter(new File(dir, this.getName() + " " + this.getItsGroup() +
                ".json"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(sortedStatistics, writer);
        }
    }

    @Override
    public String toString() {
        return "[Teacher: " + this.getName() + " " + this.getItsGroup() + "]";
    }

    public TypeGroup getItsGroup() {
        return itsGroup;
    }
}

import jdk.nashorn.internal.ir.IfNode;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ChildrenGarden implements Runnable{

    public static final int CAPACITY_CHILDREN_GARDEN = 40;
    public static final int LENGTH_OF_SCHOOL_DAY = 5000;
    public static final int PICTURE_CAPACITY = 100;
    private static final int TEACHER_THREAD_CAPACITY = 50;
    private String name;
    private List<Teacher> teachers = new ArrayList<Teacher>();
    private BlockingQueue<Child> childInGarden;
    private Map<TypeGroup, BlockingQueue<Child>> childrenInChildrenGardenGroup;
    private Set<Thread> childrenThreads;
    private BlockingQueue<Picture> drowPictures;
    private BlockingQueue<Picture> paintPictures;
    private BlockingQueue<Thread> teachersThreads;
    private Child lastChild;
    private static ChildrenGarden instance = null;

    private String[] teachersName = {"Ljubljana", "Sofia", "Moskwa", "Varshava", "Lisa", "Atina"};

    private ChildrenGarden(String name) throws PersonException, ChildrenGardenException, InterruptedException {
        this.childrenInChildrenGardenGroup = new HashMap<TypeGroup, BlockingQueue<Child>>();
        this.childInGarden = new ArrayBlockingQueue<Child>(CAPACITY_CHILDREN_GARDEN);
        this.createGroups();
        this.childrenThreads = new HashSet<Thread>();
        this.teachersThreads = new ArrayBlockingQueue<Thread>(TEACHER_THREAD_CAPACITY);
        this.paintPictures = new ArrayBlockingQueue<Picture>(PICTURE_CAPACITY);
        this.drowPictures = new ArrayBlockingQueue<Picture>(PICTURE_CAPACITY);
        this.createTeachers();

    }

//    Singleton class ChildrenGarden -> only one
    public static synchronized ChildrenGarden getInstance(String name) throws PersonException, ChildrenGardenException, InterruptedException {
        if (instance == null && name != null && name.trim().length() > 0) {
            return new ChildrenGarden(name);
        }
        else {
            return instance;
        }
    }

    private void createGroups() {
        for (TypeGroup group : TypeGroup.values()) {
            this.childrenInChildrenGardenGroup.
                    put(group, new LinkedBlockingDeque<Child>());
        }
    }

    private void createTeachers() throws PersonException, ChildrenGardenException, InterruptedException {
        for (TypeGroup group : TypeGroup.values()) {
            Teacher teacher = new Teacher(this.teachersName[new Random().nextInt(this.teachersName.length)],
                    group, this);
            this.teachers.add(teacher);
            this.teachersThreads.put(new Thread(teacher));
        }
    }

    public void addChild(Child child) throws ChildrenGardenException {
        if (child != null) {
            try {
                this.childInGarden.put(child);
            } catch (InterruptedException e) {
                return;
            }
        }
        else {
            throw new ChildrenGardenException("Invalid child");
        }
    }

    public void addInChildrenGardenGroup(Child child) {
        TypeGroup group = child.getTypeGroup();
        try {
            this.childrenInChildrenGardenGroup.get(group).put(child);
        } catch (InterruptedException e) {
            return;
        }
        this.childrenThreads.add(new Thread(child));
        if (this.numChildren() == 1) {
            this.lastChild = child;
        }
    }

    public void addDrawPicture(Picture pic) {
        try {
            this.drowPictures.put(pic);
        } catch (InterruptedException e) {
            System.out.println("[Kinder garden] I can not add draw pic, I will wait");
            return;
        }
    }

    public Picture getDrawPicture() throws InterruptedException {
            return this.drowPictures.take();
    }

    public void addPaintPicture(Picture picture) {
            this.paintPictures.add(picture);
    }

    private int numChildren() {
        int count = 0;
        for (Map.Entry<TypeGroup, BlockingQueue<Child>> entry : this.childrenInChildrenGardenGroup.entrySet()) {
            count += entry.getValue().size();
        }
        return count;
    }

    private boolean isFullWithChildren() {
        return this.childInGarden.size() == CAPACITY_CHILDREN_GARDEN;
    }

    private void startTrainingDay() throws InterruptedException {
        this.childrenThreads.forEach(thread -> thread.start());
        this.teachersThreads.forEach(thread -> {
            thread.setDaemon(true);
            thread.start();
        });
    }

    private void endTrainingDay() {
        this.childrenThreads.forEach(thread -> thread.interrupt());
        this.teachersThreads.forEach(thread -> thread.interrupt());
        System.out.println("Last go out: " + this.lastChild);
    }

     Teacher getTeacherOfGroup(TypeGroup typeGroup) {
        Teacher teacher = null;
        for (Teacher t : this.teachers) {
            if (t.getItsGroup().equals(typeGroup)) {
                teacher = t;
            }
        }
        return teacher;
    }

    public Child getChild() {
        try {
            return this.childInGarden.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public Set<Teacher> getTeachers() {
        Set<Teacher> tech = new HashSet<Teacher>(this.teachers);
        return tech;
    }

    public Teacher getTeacher() {
        return this.teachers.get(new Random().nextInt(this.teachers.size()));
    }

    public Child getLastChild() {
        return lastChild;
    }

    public void run() {
        while (!this.isFullWithChildren()) {
            try {
            this.startTrainingDay();
                Thread.sleep(LENGTH_OF_SCHOOL_DAY);
            this.endTrainingDay();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

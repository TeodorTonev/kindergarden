import java.util.Queue;

public abstract class Person implements Runnable {

    private String name;

    protected Person(String name) throws PersonException {
        setName(name);
    }

    public void setName(String name) throws PersonException {
        if (name != null && name.trim().length() > 0) {
            this.name = name;
        }
        else {
            throw new PersonException("Invalid person name!");
        }
    }

    public String getName() {
        return name;
    }

    public abstract void run();
}

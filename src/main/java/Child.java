public abstract class Child extends Person {
    private static final long TIME_FOR_SING = 2000;

    private TypeGroup typeGroup;
    private ChildrenGarden childrenGarden;
    private static int id;
    private static int increaseByOne = 0;

    public Child(String name, TypeGroup typeGroup, ChildrenGarden childrenGarden) throws PersonException, ChildrenGardenException {
        super(name);
        this.setTypeGroup(typeGroup);
        this.setChildrenGarden(childrenGarden);
        id = increaseByOne + 1;
    }

    public void startSinging() {
        try {
            Thread.sleep(TIME_FOR_SING);
            System.out.println("I'm child: " + this + " and sing...");
        } catch (InterruptedException e) {
            System.out.println("I will never sing again!");
            return;
        }
    }

    public TypeGroup getTypeGroup() {

        return typeGroup;
    }

    private void setTypeGroup(TypeGroup typeGroup) {

        this.typeGroup = typeGroup;
    }

    public ChildrenGarden getChildrenGarden() {

        return childrenGarden;
    }

    private void setChildrenGarden(ChildrenGarden childrenGarden) throws ChildrenGardenException {
        if (childrenGarden != null) {
            this.childrenGarden = childrenGarden;
        }
        else {
            throw new ChildrenGardenException("Invalid kinder garden");
        }
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[ " + this.getName() + " ]";
    }
}

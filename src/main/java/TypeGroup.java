
public enum TypeGroup {
    DUCKLING, PINGUINES, FLOGS, LADYBIRDS;

    public static TypeGroup getTypeGroupForOlderChildren() {
        return (Math.random() > 0.5) ? TypeGroup.FLOGS : TypeGroup.LADYBIRDS;
    }

    public static TypeGroup getTypeGroupForYoungerChildren() {
        return (Math.random() > 0.5) ? TypeGroup.DUCKLING : TypeGroup.PINGUINES;
    }

    public static TypeGroup getTypeGroupForTeacher() {
        double random = Math.random();
        if (random < 0.25) {
            return DUCKLING;
        }
        if (random >= 0.25 && random < 0.5) {
            return PINGUINES;
        }
        if (random >= 0.5 && random < 0.75) {
            return FLOGS;
        }
        return LADYBIRDS;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Statistics implements Comparable<Statistics> {

    private String name;
    private TypeGroup typeGroup;
    private long id;
    private List<Picture> pictures;

    public Statistics(String name, TypeGroup typeGroup, long id) throws ChildrenGardenException {
        this.pictures = new ArrayList<Picture>();
        if (name != null && name.trim().length() > 0) {
            this.name = name;
        } else {
            throw new ChildrenGardenException("Invalid name by statistics");
        }
        if (typeGroup != null) {
            this.typeGroup = typeGroup;
        } else {
            throw new ChildrenGardenException("Invalid type group by statistics");
        }
        this.id = id;
    }

    public int getCountPictures() {
        return this.pictures.size();
    }

    public void addPicture(Picture picture) throws ChildrenGardenException {
        if (picture != null) {
            this.pictures.add(picture);
        } else {
            throw new ChildrenGardenException("Invalid picture");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Statistics) {
            return ((Statistics) object).name.equalsIgnoreCase(this.name) &&
                    ((Statistics)object).id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (this.name.hashCode() * this.id);
    }

    @Override
    public int compareTo(Statistics statistics) {
        return (this.pictures.size() - statistics.pictures.size()) == 0 ?
                1 : this.pictures.size() - statistics.pictures.size();
    }
}

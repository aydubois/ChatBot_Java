package chatrabia.bot;

import java.util.Objects;

public class AssocNameLike {

    private String name = "";
    private String like = "";

    public AssocNameLike() {
        super();

        this.name = "";
        this.like = "";
    }

    public AssocNameLike(String name, String like) {
        super();

        this.name = name;
        this.like = like;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssocNameLike assocNameLike = (AssocNameLike) o;
        return name.equals(assocNameLike.name) && like.equals(assocNameLike.like);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, like);
    }
}

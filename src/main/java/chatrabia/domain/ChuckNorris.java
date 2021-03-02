package chatrabia.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ChuckNorris implements Serializable {

    public class Value {
        private Integer id;
        private String joke;
        private List<String> categories;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getJoke() {
            return joke;
        }

        public void setJoke(String joke) {
            this.joke = joke;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }
    }

    private String type;

    private Value value;

    public String getJoke() {
        return value.getJoke();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}

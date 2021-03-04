package chatrabia.domain;

import java.io.Serializable;
import java.util.List;

public class Citation implements Serializable {

    public class Value {
        private Integer id;
        private String citation;
        private String category;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCitation() {
            return citation;
        }

        public void setCitation(String citation) {
            this.citation = citation;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    private String type;

    private Citation.Value value;

    public String getCitation() {
        return value.getCitation();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Citation.Value getValue() {
        return value;
    }

    public void setValue(Citation.Value value) {
        this.value = value;
    }
}

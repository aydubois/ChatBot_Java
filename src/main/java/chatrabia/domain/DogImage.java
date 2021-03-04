package chatrabia.domain;

import java.io.Serializable;

public class DogImage implements Serializable {

    private Integer fileSizeBytes;
    private String url = "";

    public Integer getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Integer fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


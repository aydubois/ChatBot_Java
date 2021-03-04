package chatrabia.service;

import chatrabia.domain.ChuckNorris;
import chatrabia.exception.ExternalAPIException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DogImageService {

    public class DogImageDTO {
        private Integer fileSizeBytes;
        private String url;

        public Integer getFileSizeBytes() {
            return fileSizeBytes;
        }

        public DogImageDTO setFileSizeBytes(Integer fileSizeBytes) {
            this.fileSizeBytes = fileSizeBytes;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public DogImageDTO setUrl(String url) {
            this.url = url;
            return this;
        }
    }

    private static final String pattern = "[Dd]og|[Cc]hien|[Ww]oof|[Ww]af";

    private HttpService httpService;
    private static final String dogImageApiRandomUrl = "https://random.dog/woof.json";

    public DogImageService(@Qualifier("httpService") HttpService httpService) {
        this.httpService = httpService;
    }

    public String getRandomDogImage() {
        try {
            DogImageDTO dogImageDTO = httpService.sendGetRequest(dogImageApiRandomUrl, DogImageDTO.class, null);

            return dogImageDTO.getUrl();

        } catch (ExternalAPIException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPattern() {
        return pattern;
    }
}

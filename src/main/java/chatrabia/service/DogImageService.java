package chatrabia.service;

import chatrabia.domain.DogImage;
import chatrabia.exception.ExternalAPIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DogImageService extends GetHttp {

    private static Logger log = LogManager.getRootLogger();

    private static final String pattern = ".*([Dd]og|[Cc]hien|[Ww]oof|[Ww]af).*";
    private static final String imagePattern = ".*\\.(jpg|jpeg|png|gif|JPG).*";

    private static final String dogImageApiRandomUrl = "https://random.dog/woof.json";

    public DogImageService(@Qualifier("httpService") HttpService httpService) {
        super(httpService);
    }

    @Override
    public String get() {

            DogImage dogImage = new DogImage();

            int count = 0;
            boolean isMatch = false;

            // while parce que l'API renvoie aussi des vidéos et on en veut pas :p
            while (count++ < 20 && !isMatch) {
                try {
                    dogImage = httpService.sendGetRequest(dogImageApiRandomUrl, DogImage.class, null);
                    isMatch = dogImage.getUrl().matches(imagePattern);

                    log.warn("dogImage  "+dogImage.getUrl());
                    if(!isMatch) Thread.sleep(1500);

                } catch (ExternalAPIException | InterruptedException e) {
                    log.error(e.toString());
                    return "";
                }
            }

            return dogImage.getUrl();
    }

    public static String getPattern() {
        return pattern;
    }
}

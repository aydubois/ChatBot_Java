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

    private static final String pattern = "[Dd]og|[Cc]hien|[Ww]oof|[Ww]af";
    private static final String imagePattern = ".*\\.(jpg|jpeg|png|gif).*";

    private static final String dogImageApiRandomUrl = "https://random.dog/woof.json";

    public DogImageService(@Qualifier("httpService") HttpService httpService) {
        super(httpService);
    }

    @Override
    public String get() {

            DogImage dogImage;
            int count = 0;

            //Do while parce que l'API renvoie aussi des vidéos et on en veut pas :p
            do {
                try {
                    dogImage = httpService.sendGetRequest(dogImageApiRandomUrl, DogImage.class, null);
                    log.warn("dogImage  "+dogImage.getUrl());
                } catch (ExternalAPIException e) {
                    e.printStackTrace();
                    return "";
                }
            }
            while (count++ < 20 && !dogImage.getUrl().matches(imagePattern));

            return dogImage.getUrl();
    }

    public static String getPattern() {
        return pattern;
    }
}

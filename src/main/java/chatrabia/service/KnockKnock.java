package chatrabia.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class KnockKnock {

    private HttpService httpService;

    public KnockKnock(@Qualifier("httpService") HttpService httpService) {

    }
}

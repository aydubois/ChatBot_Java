package chatrabia.service;

import chatrabia.exception.ExternalAPIException;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;

public abstract class GetHttp {
    protected HttpService httpService;
    private String urlAPI;

    public GetHttp(@Qualifier("httpService")HttpService httpService){this.httpService = httpService;}

    public abstract String get();


}

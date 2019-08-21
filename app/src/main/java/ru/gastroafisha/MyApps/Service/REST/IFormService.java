package ru.gastroafisha.MyApps.Service.REST;


import io.reactivex.Completable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.gastroafisha.MyApps.Proxy.FormDataProxy;

/**
 * Created by alex on 22.12.2017.
 */

public interface IFormService {

    @POST("client/form")
    Completable sendForm(@Body FormDataProxy data);
}

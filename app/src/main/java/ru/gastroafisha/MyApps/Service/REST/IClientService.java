package ru.gastroafisha.MyApps.Service.REST;

import io.reactivex.Completable;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import ru.gastroafisha.MyApps.Proxy.ClientDataProxy;

/**
 * Created by alex on 22.12.2017.
 */

public interface IClientService {

    @PUT("client/data")
    Completable sendClientData(@Body ClientDataProxy data);

}

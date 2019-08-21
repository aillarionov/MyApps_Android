package ru.gastroafisha.MyApps.Service.REST;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.gastroafisha.MyApps.Proxy.CatalogImageProxy;
import ru.gastroafisha.MyApps.Proxy.CatalogItemProxy;
import ru.gastroafisha.MyApps.Proxy.CatalogMemberProxy;
import ru.gastroafisha.MyApps.Proxy.CatalogNewsProxy;
import ru.gastroafisha.MyApps.Utils.Classes.GMDate;

/**
 * Created by alex on 22.12.2017.
 */

public interface ICatalogService {

    @GET("catalog/{id}")
    Single<Response<List<CatalogItemProxy>>> getCatalogItems(@Path("id") int id);

    @GET("catalog/{id}")
    Single<Response<List<CatalogMemberProxy>>> getCatalogMembers(@Path("id") int id);

    @GET("catalog/{id}")
    Single<Response<List<CatalogNewsProxy>>> getCatalogNews(@Path("id") int id);

    @GET("catalog/{id}")
    Single<Response<List<CatalogImageProxy>>> getCatalogImages(@Path("id") int id);

    @GET("cache/catalog/{id}")
    Single<ResponseBody> getCatalogLastChange(@Path("id") int id);

}

package ru.gastroafisha.MyApps.Service.REST;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.gastroafisha.MyApps.Proxy.CityProxy;
import ru.gastroafisha.MyApps.Proxy.OrgProxy;

/**
 * Created by alex on 22.12.2017.
 */

public interface IOrgService {

    @GET("cities")
    Single<List<CityProxy>> getCitiesList();

    @GET("orgs/{cityId}")
    Single<List<OrgProxy>> getOrgsList(@Path("cityId") int cityId);

    @GET("org/{id}")
    Single<OrgProxy> getOrg(@Path("id") int id);

    @GET("org/code/{code}")
    Single<OrgProxy> getOrg(@Path("code") String code);
}

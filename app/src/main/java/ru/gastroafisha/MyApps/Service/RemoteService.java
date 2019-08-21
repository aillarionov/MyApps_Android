package ru.gastroafisha.MyApps.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ru.gastroafisha.MyApps.Model.ClientDataModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogImageModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogNewsModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.Proxy.CatalogImageProxy;
import ru.gastroafisha.MyApps.Proxy.CatalogItemProxy;
import ru.gastroafisha.MyApps.Proxy.CatalogMemberProxy;
import ru.gastroafisha.MyApps.Proxy.CatalogNewsProxy;
import ru.gastroafisha.MyApps.Proxy.CityProxy;
import ru.gastroafisha.MyApps.Proxy.FormDataProxy;
import ru.gastroafisha.MyApps.Proxy.OrgTuple;
import ru.gastroafisha.MyApps.Service.REST.RESTDataProvider;
import ru.gastroafisha.MyApps.Storage.DTO.CatalogImageDTO;
import ru.gastroafisha.MyApps.Storage.DTO.CatalogItemDTO;
import ru.gastroafisha.MyApps.Storage.DTO.CatalogMemberDTO;
import ru.gastroafisha.MyApps.Storage.DTO.CatalogNewsDTO;
import ru.gastroafisha.MyApps.Storage.DTO.ClientDataDTO;
import ru.gastroafisha.MyApps.Storage.DTO.OrgDTO;
import ru.gastroafisha.MyApps.Utils.Classes.GMDate;

/**
 * Created by alex on 19.01.2018.
 */

public class RemoteService {

    public static Single<List<CityProxy>> listCities() {
        return new RESTDataProvider().getOrgService().getCitiesList()
                .onErrorReturnItem(new ArrayList<>())
                .subscribeOn(Schedulers.io());
    }

    public static Single<List<OrgModel>> listOrgs(Integer cityId) {
        return new RESTDataProvider().getOrgService().getOrgsList(cityId)
                .map(OrgDTO::proxiesToModels)
                .onErrorReturnItem(new ArrayList<>())
                .subscribeOn(Schedulers.io());
    }

    public static Single<OrgTuple> getOrg(Integer orgId) {
        return new RESTDataProvider().getOrgService().getOrg(orgId)
                .map(OrgDTO::proxyToTuple)
                .onErrorReturnItem(new OrgTuple())
                .subscribeOn(Schedulers.io());
    }

    public static Single<OrgModel> getOrg(String code) {
        return new RESTDataProvider().getOrgService().getOrg(code)
                .map(OrgDTO::proxyToModel)
                .subscribeOn(Schedulers.io());
    }

    public static Single<Response<List<CatalogItemProxy>>> getCatalogItems(Integer orgId, Integer catalogId) {
        return new RESTDataProvider().getCatalogService().getCatalogItems(catalogId)
                .subscribeOn(Schedulers.io());
    }

    public static Single<Response<List<CatalogMemberProxy>>> getCatalogMembers(Integer orgId, Integer catalogId) {
        return new RESTDataProvider().getCatalogService().getCatalogMembers(catalogId)
                .subscribeOn(Schedulers.io());
    }

    public static Single<Response<List<CatalogNewsProxy>>> getCatalogNews(Integer orgId, Integer catalogId) {
        return new RESTDataProvider().getCatalogService().getCatalogNews(catalogId)
                .subscribeOn(Schedulers.io());
    }

    public static Single<Response<List<CatalogImageProxy>>> getCatalogImages(Integer orgId, Integer catalogId) {
        return new RESTDataProvider().getCatalogService().getCatalogImages(catalogId)
                .subscribeOn(Schedulers.io());
    }

    public static Single<GMDate> getCatalogLastChange(Integer orgId, Integer catalogId) {
        return new RESTDataProvider().getCatalogService().getCatalogLastChange(catalogId)
                .map(GMDate::fromISO8601)
                .onErrorReturnItem(new GMDate())
                .subscribeOn(Schedulers.io());
    }

    public static Completable sendForm(FormDataProxy data) {
        return new RESTDataProvider().getFormService().sendForm(data)
                .subscribeOn(Schedulers.io());
    }

    public static Completable sendConfig(ClientDataModel clientDataModel) {
        return new RESTDataProvider().getClientService().sendClientData(ClientDataDTO.modelToProxy(clientDataModel))
                .subscribeOn(Schedulers.io());
    }


}

package ru.gastroafisha.MyApps.Storage;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableConcatMapCompletable;
import io.reactivex.schedulers.Schedulers;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Catalog.CatalogModel;
import ru.gastroafisha.MyApps.Model.ClientDataModel;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogImageModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogNewsModel;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.Proxy.OrgTuple;
import ru.gastroafisha.MyApps.Service.RemoteService;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogDAO;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogImageDAO;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogItemDAO;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogMemberDAO;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogNewsDAO;
import ru.gastroafisha.MyApps.Storage.DAO.FavoriteDAO;
import ru.gastroafisha.MyApps.Storage.DAO.FormDAO;
import ru.gastroafisha.MyApps.Storage.DAO.FormItemDAO;
import ru.gastroafisha.MyApps.Storage.DAO.MenuItemDAO;
import ru.gastroafisha.MyApps.Storage.DAO.OrgConfigDAO;
import ru.gastroafisha.MyApps.Storage.DAO.OrgDAO;
import ru.gastroafisha.MyApps.Storage.DAO.TicketDAO;
import ru.gastroafisha.MyApps.Storage.DTO.CatalogImageDTO;
import ru.gastroafisha.MyApps.Storage.DTO.CatalogItemDTO;
import ru.gastroafisha.MyApps.Storage.DTO.CatalogMemberDTO;
import ru.gastroafisha.MyApps.Storage.DTO.CatalogNewsDTO;
import ru.gastroafisha.MyApps.Storage.Migrations.Migration_1_2;
import ru.gastroafisha.MyApps.Storage.Migrations.Migration_2_3;
import ru.gastroafisha.MyApps.Utils.ADUtils;
import ru.gastroafisha.MyApps.Utils.Classes.GMDate;
import ru.gastroafisha.MyApps.Utils.Classes.Holder;
import ru.gastroafisha.MyApps.Utils.Classes.Progress;
import ru.gastroafisha.MyApps.Utils.Services.InformerFirebaseInstanceIDService;

/**
 * Created by alex on 24.12.2017.
 */

public class LocalDataStorage {

    private final LocalDatabase database;

    public LocalDataStorage(Context context) {
        try {
            database = Room.databaseBuilder(context, LocalDatabase.class, "local.db")
                    .addMigrations(
                            new Migration_1_2(),
                            new Migration_2_3()
                    )
                    .build();
        } catch (Exception e) {
            Crashlytics.logException(e);
            throw e;
        }
    }

    public OrgDAO getOrgs() {
        return database.getOrgDAO();
    }

    public TicketDAO getTickets() {
        return database.getTicketDAO();
    }

    public OrgConfigDAO getOrgConfigs() {
        return database.getOrgConfigDAO();
    }

    public MenuItemDAO getMenuItems() {
        return database.getMenuItemDAO();
    }

    public FormDAO getForms() {
        return database.getFormDAO();
    }

    public FormItemDAO getFormItems() {
        return database.getFormItemDAO();
    }

    public CatalogDAO getCatalogs() {
        return database.getCatalogDAO();
    }

    public CatalogItemDAO getCatalogItems() {
        return database.getCatalogItemDAO();
    }

    public CatalogMemberDAO getCatalogMembers() {
        return database.getCatalogMemberDAO();
    }

    public CatalogImageDAO getCatalogImages() {
        return database.getCatalogImageDAO();
    }

    public CatalogNewsDAO getCatalogNews() {
        return database.getCatalogNewsDAO();
    }

    public FavoriteDAO getFavorites() {
        return database.getFavoritesDAO();
    }


    public Completable updateOrg(Integer orgId) {
        return RemoteService.getOrg(orgId)
                .flatMapCompletable(orgTuple -> Completable.merge(Arrays.asList(
                        storeOrg(orgTuple),
                        storeTicket(orgTuple),
                        storeMenu(orgTuple),
                        storeForms(orgTuple),
                        storeCatalogs(orgTuple),
                        storeCatalogItems(orgTuple)
                )));
    }

    private Completable updateCatalogItems(CatalogModel catalog) {
        return RemoteService.<CatalogItemModel>getCatalogItems(catalog.getOrgId(), catalog.getId())
                .flatMapCompletable(items -> Completable.fromAction(() -> {
                    getCatalogItems().clear(catalog.getOrgId(), catalog.getId());
                    getCatalogItems().insertAll(CatalogItemDTO.proxiesToModels(catalog.getOrgId(), catalog.getId(), items.body()));
                    GMDate lastChange = GMDate.fromLastModified(items.headers().get("Last-Modified"));
                    catalog.setLastChange(lastChange.isEmpty() ? GMDate.min() : lastChange.getDate());
                    getCatalogs().insert(catalog);
                }));
    }

    private Completable updateCatalogMembers(CatalogModel catalog) {
        return RemoteService.<CatalogMemberModel>getCatalogMembers(catalog.getOrgId(), catalog.getId())
                .flatMapCompletable(items -> Completable.fromAction(() -> {
                    getCatalogMembers().clear(catalog.getOrgId(), catalog.getId());
                    getCatalogMembers().insertAll(CatalogMemberDTO.proxiesToModels(catalog.getOrgId(), catalog.getId(), items.body()));
                    GMDate lastChange = GMDate.fromLastModified(items.headers().get("Last-Modified"));
                    catalog.setLastChange(lastChange.isEmpty() ? GMDate.min() : lastChange.getDate());
                    getCatalogs().insert(catalog);
                }));
    }

    private Completable updateCatalogImages(CatalogModel catalog) {
        return RemoteService.<CatalogImageModel>getCatalogImages(catalog.getOrgId(), catalog.getId())
                .flatMapCompletable(items -> Completable.fromAction(() -> {
                    getCatalogImages().clear(catalog.getOrgId(), catalog.getId());
                    getCatalogImages().insertAll(CatalogImageDTO.proxiesToModels(catalog.getOrgId(), catalog.getId(), items.body()));
                    GMDate lastChange = GMDate.fromLastModified(items.headers().get("Last-Modified"));
                    catalog.setLastChange(lastChange.isEmpty() ? GMDate.min() : lastChange.getDate());
                    getCatalogs().insert(catalog);
                }));
    }

    private Completable updateCatalogNews(CatalogModel catalog) {
        return RemoteService.<CatalogNewsModel>getCatalogNews(catalog.getOrgId(), catalog.getId())
                .flatMapCompletable(items -> Completable.fromAction(() -> {
                    getCatalogNews().clear(catalog.getOrgId(), catalog.getId());
                    getCatalogNews().insertAll(CatalogNewsDTO.proxiesToModels(catalog.getOrgId(), catalog.getId(), items.body()));
                    GMDate lastChange = GMDate.fromLastModified(items.headers().get("Last-Modified"));
                    catalog.setLastChange(lastChange.isEmpty() ? GMDate.min() : lastChange.getDate());
                    getCatalogs().insert(catalog);
                }));
    }

    private Completable storeOrg(OrgTuple orgTuple) {
        return Completable.fromAction(() ->
                getOrgs().insert(orgTuple.getOrgModel())
        ).subscribeOn(Schedulers.io());
    }

    private Completable storeTicket(OrgTuple orgTuple) {
        return Completable.fromAction(() -> {
            getTickets().delete(orgTuple.getOrgModel().getId());
            if (orgTuple.getTicketModel() != null) {
                getTickets().insert(orgTuple.getTicketModel());
            }
        }).subscribeOn(Schedulers.io());
    }

    private Completable storeMenu(OrgTuple orgTuple) {
        return Completable.fromAction(() -> {
            getMenuItems().clear(orgTuple.getOrgModel().getId());
            getMenuItems().insertAll(orgTuple.getMenuItems());
        }).subscribeOn(Schedulers.io());
    }

    private Completable storeCatalogs(OrgTuple orgTuple) {
        return Completable.fromAction(() -> {
            List<CatalogModel> catalogs = orgTuple.getCatalogs();

            for (CatalogModel newcatalog: catalogs) {
                CatalogModel catalog = getCatalogs().getSync(newcatalog.getOrgId(), newcatalog.getId());
                if (catalog != null) {
                    newcatalog.setLastChange(catalog.getLastChange());
                }
            }

            getCatalogs().clear(orgTuple.getOrgModel().getId());
            getCatalogs().insertAll(catalogs);
        }).subscribeOn(Schedulers.io());
    }

    public Completable reloadCatalogItems(Integer orgId) {
        return Observable.fromIterable(getCatalogs().list(orgId))
                .flatMapCompletable(this::updateCatalogItemList)
                .subscribeOn(Schedulers.io());
    }

    private Completable storeCatalogItems(OrgTuple orgTuple) {
        return Observable.fromIterable(orgTuple.getCatalogs())
                .flatMapCompletable(this::updateCatalogItemList)
                .subscribeOn(Schedulers.io());
    }

    public Completable refreshCatalogItems(Integer orgId, Integer catalogId) {
        return getCatalogs().get(orgId, catalogId).firstOrError()
                .flatMapCompletable(this::updateCatalogItemList)
                .subscribeOn(Schedulers.io());
    }

    private Completable updateCatalogItemList(CatalogModel catalog) throws Exception {
        return RemoteService.getCatalogLastChange(catalog.getOrgId(), catalog.getId())
                .flatMapCompletable(date -> {
                   if (date.differ(catalog.getLastChange())) {
                       switch (catalog.getType()) {
                           case Item:
                               return updateCatalogItems(catalog);

                           case Member:
                               return updateCatalogMembers(catalog);

                           case News:
                               return updateCatalogNews(catalog);

                           case Image:
                               return updateCatalogImages(catalog);

                           default:
                               throw new Exception("Unknown class [" + catalog.getType().toString() + "]");
                       }
                   } else {
                       return Completable.fromAction(() -> {});
                   }
                });
    }

    private Completable storeForms(OrgTuple orgTuple) {
        return Completable.fromAction(() -> {
            getFormItems().clear(orgTuple.getOrgModel().getId());
            getForms().clear(orgTuple.getOrgModel().getId());
            getForms().insertAll(orgTuple.getForms());
            getFormItems().insertAll(orgTuple.getFormItems());
        }).subscribeOn(Schedulers.io());
    }

    public Flowable<Progress> preloadImages(Integer orgId) {
        Progress progress = new Progress(0, 0);
        Holder<Disposable> d = new Holder<>();

        return Flowable.create((FlowableOnSubscribe<Progress>) e -> {
            d.setValue(Flowable.zip(
                    getCatalogNews().list(orgId),
                    getCatalogMembers().list(orgId),
                    getCatalogItems().list(orgId),
                    getCatalogImages().list(orgId),
                    (t1, t2, t3, t4) -> {
                        List<CatalogItemModel> list = new ArrayList<>();
                        list.addAll(t1);
                        list.addAll(t2);
                        list.addAll(t3);
                        list.addAll(t4);
                        return list;
                    }
            ).subscribe(catalogItemModels -> {
                progress.setTotal(catalogItemModels.size());
                e.onNext(progress);

                Integer i = 0;

                for (CatalogItemModel catalogItem : catalogItemModels) {
                    if (catalogItem.getPhoto() != null && !MainApplication.getInstance().getLocalImageStore().isTemporaryFileExists(orgId, catalogItem.getPhoto().getUrl())) {
                        MainApplication.getInstance().getLocalImageStore().storeTemporaryImage(orgId, catalogItem.getPhoto().getUrl());
                    }

                    for (ImageModel image : catalogItem.getPhotos()) {
                        if (!MainApplication.getInstance().getLocalImageStore().isTemporaryFileExists(orgId, image.getUrl())) {
                            MainApplication.getInstance().getLocalImageStore().storeTemporaryImage(orgId, image.getUrl());
                        }
                    }

                    progress.setCurrent(++i);
                    e.onNext(progress);
                }

                e.onComplete();
            }));


        }, BackpressureStrategy.LATEST)
                .doFinally(() -> {
                    d.getValue().dispose();
                })
                .subscribeOn(Schedulers.io());
    }


    public Completable removeOrg(Integer orgId) {
        return Completable.mergeArray(

                // Clean org tables
                Completable.fromAction(() -> {

                    // Menu
                    getMenuItems().clear(orgId);

                    // Forms
                    getFormItems().clear(orgId);
                    getForms().clear(orgId);

                    // Catalogs
                    getCatalogNews().clear(orgId);
                    getCatalogMembers().clear(orgId);
                    getCatalogItems().clear(orgId);
                    getCatalogImages().clear(orgId);

                    getFavorites().clear(orgId);
                    getCatalogs().clear(orgId);

                    // Org
                    getOrgConfigs().delete(orgId);
                    getTickets().delete(orgId);
                    getOrgs().delete(orgId);

                }).subscribeOn(Schedulers.io()),

                // Clean org images
                MainApplication.getInstance().getLocalImageStore().cleanOrgImages(orgId)

        ).subscribeOn(Schedulers.io());
    }

    public Completable updateOrgConfig(OrgConfigModel orgConfig) {
        return Completable.fromAction(() -> {
            getOrgConfigs().insertOrUpdate(orgConfig);
        }).subscribeOn(Schedulers.io());
    }

    public Completable uploadClientData() {
        return getOrgConfigs().list()
                .firstOrError()
                .flatMapCompletable(orgConfigModels -> {

                    String tokenId = InformerFirebaseInstanceIDService.getToken();
                    String adId = ADUtils.getADId();

                    ClientDataModel clientData = new ClientDataModel();
                    clientData.setTokenId(tokenId);
                    clientData.setAdId(adId);
                    clientData.setOrgConfigs(orgConfigModels);

                    return RemoteService.sendConfig(clientData);
                })
                .subscribeOn(Schedulers.io());
    }
}

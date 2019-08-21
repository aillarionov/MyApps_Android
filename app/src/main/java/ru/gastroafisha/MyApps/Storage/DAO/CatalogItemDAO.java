package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemFavoriteModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface CatalogItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CatalogItemModel> item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CatalogItemModel item);

    @Delete
    void delete(CatalogItemModel item);

    @Query("DELETE FROM catalogitems;")
    void clear();

    @Query("DELETE FROM catalogitems WHERE orgId = :orgId;")
    void clear(Integer orgId);

    @Query("DELETE FROM catalogitems WHERE orgId = :orgId AND catalogId = :catalogId;")
    void clear(Integer orgId, Integer catalogId);

    @Query("SELECT * FROM catalogitems WHERE orgId = :orgId ORDER BY id DESC;")
    Flowable<List<CatalogItemModel>> list(Integer orgId);

    @Query("SELECT * " +
            "FROM catalogitems " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogitems.orgId " +
            "               AND favorites.catalogIdF = catalogitems.catalogId " +
            "               AND favorites.idF = catalogitems.id " +
            "WHERE catalogitems.orgId = :orgId AND catalogitems.catalogId = :catalogId AND catalogitems.id = :id " +
            "ORDER BY catalogitems.ord ASC;")
    Flowable<CatalogItemFavoriteModel> get(Integer orgId, Integer catalogId, Integer id);

    @Query("SELECT * " +
            "FROM catalogitems " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogitems.orgId " +
            "               AND favorites.catalogIdF = catalogitems.catalogId " +
            "               AND favorites.idF = catalogitems.id " +
            "WHERE catalogitems.orgId = :orgId AND catalogitems.catalogId = :catalogId " +
            "ORDER BY catalogitems.ord ASC;")
    Flowable<List<CatalogItemFavoriteModel>> list(Integer orgId, Integer catalogId);


    @Query("SELECT * " +
            "FROM catalogitems " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogitems.orgId " +
            "               AND favorites.catalogIdF = catalogitems.catalogId " +
            "               AND favorites.idF = catalogitems.id " +
            "WHERE catalogitems.orgId = :orgId AND catalogitems.raw like :mask " +
            "ORDER BY catalogitems.ord ASC;")
    Flowable<List<CatalogItemFavoriteModel>> search(Integer orgId, String mask);

    @Query("SELECT * " +
            "FROM catalogitems " +
            "INNER JOIN favorites " +
            "           ON favorites.orgIdF = catalogitems.orgId " +
            "               AND favorites.catalogIdF = catalogitems.catalogId " +
            "               AND favorites.idF = catalogitems.id " +
            "WHERE catalogitems.orgId = :orgId AND catalogitems.raw like :mask " +
            "ORDER BY catalogitems.ord ASC;")
    Flowable<List<CatalogItemFavoriteModel>> favorites(Integer orgId, String mask);
}

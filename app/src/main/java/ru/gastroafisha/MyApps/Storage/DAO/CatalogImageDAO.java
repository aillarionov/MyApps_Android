package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Item.CatalogImageModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogImageFavoriteModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface CatalogImageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CatalogImageModel> item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CatalogImageModel item);

    @Delete
    void delete(CatalogImageModel item);

    @Query("DELETE FROM catalogimages;")
    void clear();

    @Query("DELETE FROM catalogimages WHERE orgId = :orgId;")
    void clear(Integer orgId);

    @Query("DELETE FROM catalogimages WHERE orgId = :orgId AND catalogId = :catalogId;")
    void clear(Integer orgId, Integer catalogId);

    @Query("SELECT * FROM catalogimages WHERE orgId = :orgId ORDER BY catalogimages.ord ASC;")
    Flowable<List<CatalogImageModel>> list(Integer orgId);

    @Query("SELECT * " +
            "FROM catalogimages " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogimages.orgId " +
            "               AND favorites.catalogIdF = catalogimages.catalogId " +
            "               AND favorites.idF = catalogimages.id " +
            "WHERE catalogimages.orgId = :orgId AND catalogimages.catalogId = :catalogId AND catalogimages.id = :id " +
            "ORDER BY catalogimages.ord ASC;")
    Flowable<CatalogImageFavoriteModel> get(Integer orgId, Integer catalogId, Integer id);


    @Query("SELECT * " +
            "FROM catalogimages " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogimages.orgId " +
            "               AND favorites.catalogIdF = catalogimages.catalogId " +
            "               AND favorites.idF = catalogimages.id " +
            "WHERE catalogimages.orgId = :orgId AND catalogimages.catalogId = :catalogId " +
            "ORDER BY catalogimages.ord ASC;")
    Flowable<List<CatalogImageFavoriteModel>> list(Integer orgId, Integer catalogId);

    @Query("SELECT * " +
            "FROM catalogimages " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogimages.orgId " +
            "               AND favorites.catalogIdF = catalogimages.catalogId " +
            "               AND favorites.idF = catalogimages.id " +
            "WHERE catalogimages.orgId = :orgId AND catalogimages.raw like :mask " +
            "ORDER BY catalogimages.ord ASC;")
    Flowable<List<CatalogImageFavoriteModel>> search(Integer orgId, String mask);

    @Query("SELECT * " +
            "FROM catalogimages " +
            "INNER JOIN favorites " +
            "           ON favorites.orgIdF = catalogimages.orgId " +
            "               AND favorites.catalogIdF = catalogimages.catalogId " +
            "               AND favorites.idF = catalogimages.id " +
            "WHERE catalogimages.orgId = :orgId AND catalogimages.raw like :mask " +
            "ORDER BY catalogimages.ord ASC;")
    Flowable<List<CatalogImageFavoriteModel>> favorites(Integer orgId, String mask);

}

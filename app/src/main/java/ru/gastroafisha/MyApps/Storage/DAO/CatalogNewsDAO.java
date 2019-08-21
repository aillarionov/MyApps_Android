package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Item.CatalogNewsFavoriteModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogNewsModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface CatalogNewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CatalogNewsModel> item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CatalogNewsModel item);

    @Delete
    void delete(CatalogNewsModel item);

    @Query("DELETE FROM catalognews;")
    void clear();

    @Query("DELETE FROM catalognews WHERE orgId = :orgId;")
    void clear(Integer orgId);

    @Query("DELETE FROM catalognews WHERE orgId = :orgId AND catalogId = :catalogId;")
    void clear(Integer orgId, Integer catalogId);

    @Query("SELECT * FROM catalognews WHERE orgId = :orgId ORDER BY catalognews.ord ASC;")
    Flowable<List<CatalogNewsModel>> list(Integer orgId);

    @Query("SELECT * " +
            "FROM catalognews " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalognews.orgId " +
            "               AND favorites.catalogIdF = catalognews.catalogId " +
            "               AND favorites.idF = catalognews.id " +
            "WHERE catalognews.orgId = :orgId AND catalognews.catalogId = :catalogId AND catalognews.id = :id " +
            "ORDER BY catalognews.ord ASC;")
    Flowable<CatalogNewsFavoriteModel> get(Integer orgId, Integer catalogId, Integer id);

    @Query("SELECT * " +
            "FROM catalognews " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalognews.orgId " +
            "               AND favorites.catalogIdF = catalognews.catalogId " +
            "               AND favorites.idF = catalognews.id " +
            "WHERE catalognews.orgId = :orgId AND catalognews.catalogId = :catalogId " +
            "ORDER BY catalognews.ord ASC;")
    Flowable<List<CatalogNewsFavoriteModel>> list(Integer orgId, Integer catalogId);

    @Query("SELECT * " +
            "FROM catalognews " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalognews.orgId " +
            "               AND favorites.catalogIdF = catalognews.catalogId " +
            "               AND favorites.idF = catalognews.id " +
            "WHERE catalognews.orgId = :orgId AND catalognews.raw like :mask " +
            "ORDER BY catalognews.ord ASC;")
    Flowable<List<CatalogNewsFavoriteModel>> search(Integer orgId, String mask);

    @Query("SELECT * " +
            "FROM catalognews " +
            "INNER JOIN favorites " +
            "           ON favorites.orgIdF = catalognews.orgId " +
            "               AND favorites.catalogIdF = catalognews.catalogId " +
            "               AND favorites.idF = catalognews.id " +
            "WHERE catalognews.orgId = :orgId AND catalognews.raw like :mask " +
            "ORDER BY catalognews.ord ASC;")
    Flowable<List<CatalogNewsFavoriteModel>> favorites(Integer orgId, String mask);
}

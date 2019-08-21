package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberFavoriteModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface CatalogMemberDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CatalogMemberModel> item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CatalogMemberModel item);

    @Delete
    void delete(CatalogMemberModel item);

    @Query("DELETE FROM catalogmembers;")
    void clear();

    @Query("DELETE FROM catalogmembers WHERE orgId = :orgId;")
    void clear(Integer orgId);

    @Query("DELETE FROM catalogmembers WHERE orgId = :orgId AND catalogId = :catalogId;")
    void clear(Integer orgId, Integer catalogId);

    @Query("SELECT * FROM catalogmembers WHERE orgId = :orgId ORDER BY catalogmembers.ord ASC;")
    Flowable<List<CatalogMemberModel>> list(Integer orgId);

    @Query("SELECT * " +
            "FROM catalogmembers " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogmembers.orgId " +
            "               AND favorites.catalogIdF = catalogmembers.catalogId " +
            "               AND favorites.idF = catalogmembers.id " +
            "WHERE catalogmembers.orgId = :orgId AND catalogmembers.catalogId = :catalogId AND catalogmembers.id = :id " +
            "ORDER BY catalogmembers.ord ASC;")
    Flowable<CatalogMemberFavoriteModel> get(Integer orgId, Integer catalogId, Integer id);

    @Query("SELECT * " +
            "FROM catalogmembers " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogmembers.orgId " +
            "               AND favorites.catalogIdF = catalogmembers.catalogId " +
            "               AND favorites.idF = catalogmembers.id " +
            "WHERE catalogmembers.orgId = :orgId AND catalogmembers.catalogId = :catalogId " +
            "ORDER BY catalogmembers.ord ASC;")
    Flowable<List<CatalogMemberFavoriteModel>> list(Integer orgId, Integer catalogId);

    @Query("SELECT * " +
            "FROM catalogmembers " +
            "WHERE catalogmembers.orgId = :orgId AND catalogmembers.catalogId = :catalogId " +
            "ORDER BY catalogmembers.ord ASC;")
    Flowable<List<CatalogMemberModel>> listForCategories(Integer orgId, Integer catalogId);

    @Query("SELECT * " +
            "FROM catalogmembers " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogmembers.orgId " +
            "               AND favorites.catalogIdF = catalogmembers.catalogId " +
            "               AND favorites.idF = catalogmembers.id " +
            "WHERE catalogmembers.orgId = :orgId AND catalogmembers.raw like :mask " +
            "ORDER BY catalogmembers.ord ASC;")
    Flowable<List<CatalogMemberFavoriteModel>> search(Integer orgId, String mask);

    @Query("SELECT * " +
            "FROM catalogmembers " +
            "LEFT JOIN favorites " +
            "           ON favorites.orgIdF = catalogmembers.orgId " +
            "               AND favorites.catalogIdF = catalogmembers.catalogId " +
            "               AND favorites.idF = catalogmembers.id " +
            "WHERE catalogmembers.orgId = :orgId AND catalogmembers.catalogId = :catalogId AND catalogmembers.raw like :mask " +
            "ORDER BY catalogmembers.ord ASC;")
    Flowable<List<CatalogMemberFavoriteModel>> search(Integer orgId, Integer catalogId, String mask);

    @Query("SELECT * " +
            "FROM catalogmembers " +
            "INNER JOIN favorites " +
            "           ON favorites.orgIdF = catalogmembers.orgId " +
            "               AND favorites.catalogIdF = catalogmembers.catalogId " +
            "               AND favorites.idF = catalogmembers.id " +
            "WHERE catalogmembers.orgId = :orgId AND catalogmembers.raw like :mask " +
            "ORDER BY catalogmembers.ord ASC;")
    Flowable<List<CatalogMemberFavoriteModel>> favorites(Integer orgId, String mask);
}

package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Catalog.CatalogModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface CatalogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CatalogModel> expositions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CatalogModel item);

    @Delete
    void delete(CatalogModel item);

    @Query("SELECT * FROM catalogs")
    Flowable<List<CatalogModel>> listAll();

    @Query("SELECT * FROM catalogs WHERE orgId = :orgId;")
    List<CatalogModel> list(Integer orgId);

    @Query("DELETE FROM catalogs WHERE orgId = :orgId;")
    void clear(Integer orgId);

    @Query("SELECT * FROM catalogs WHERE orgId = :orgId AND id = :id")
    Flowable<CatalogModel> get(Integer orgId, int id);

    @Query("SELECT * FROM catalogs WHERE orgId = :orgId AND id = :id")
    CatalogModel getSync(Integer orgId, int id);

}

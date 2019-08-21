package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Item.FavoriteModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FavoriteModel> item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteModel item);

    @Delete
    void delete(FavoriteModel item);

    @Query("DELETE FROM favorites WHERE orgIdF = :orgId AND catalogIdF = :catalogId AND idF = :id;")
    void delete(Integer orgId, Integer catalogId, Integer id);

    @Query("DELETE FROM favorites;")
    void clear();

    @Query("DELETE FROM favorites WHERE orgIdF = :orgId;")
    void clear(Integer orgId);

    @Query("DELETE FROM favorites WHERE orgIdF = :orgId AND catalogIdF = :catalogId;")
    void clear(Integer orgId, Integer catalogId);

    @Query("SELECT * FROM favorites WHERE orgIdF = :orgId AND catalogIdF = :catalogId AND idF = :id;")
    Flowable<FavoriteModel> get(Integer orgId, Integer catalogId, Integer id);

    @Query("SELECT * FROM favorites")
    Flowable<List<FavoriteModel>> list();

    @Query("SELECT * FROM favorites WHERE orgIdF = :orgId AND catalogIdF = :catalogId;")
    Flowable<List<FavoriteModel>> list(Integer orgId, Integer catalogId);

    @Query("SELECT * FROM favorites WHERE orgIdF = :orgId;")
    Flowable<List<FavoriteModel>> list(Integer orgId);
}

package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Form.FormModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface FormDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FormModel> expositions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FormModel item);

    @Delete
    void delete(FormModel item);

    @Query("SELECT * FROM forms")
    Flowable<List<FormModel>> listAll();

    @Query("DELETE FROM forms WHERE orgId = :orgId;")
    void clear(Integer orgId);

    @Query("SELECT * FROM forms WHERE orgId = :orgId AND id = :id;")
    Flowable<FormModel> get(Integer orgId, int id);
}

package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Form.FormItemModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface FormItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FormItemModel> expositions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FormItemModel item);

    @Delete
    void delete(FormItemModel item);

    @Query("SELECT * FROM formitems WHERE orgId = :orgId AND formId = :formId ORDER BY order_ ASC;")
    Flowable<List<FormItemModel>> list(Integer orgId, Integer formId);

    @Query("DELETE FROM formitems WHERE orgId = :orgId;")
    void clear(Integer orgId);

}

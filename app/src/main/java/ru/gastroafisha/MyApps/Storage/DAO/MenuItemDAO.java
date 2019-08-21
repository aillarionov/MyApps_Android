package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface MenuItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MenuItemModel> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MenuItemModel item);

    @Delete
    void delete(MenuItemModel item);

    @Query("SELECT * FROM menuitems WHERE orgId = :orgId ORDER BY order_ ASC;")
    Flowable<List<MenuItemModel>> listAll(int orgId);

    @Query("DELETE FROM menuitems WHERE orgId = :orgId;")
    void clear(Integer orgId);
}

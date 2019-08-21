package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface OrgDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OrgModel> orgs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrgModel org);

    @Delete
    void delete(OrgModel org);

    @Query("DELETE FROM orgs WHERE id = :id;")
    void delete(Integer id);

    @Query("SELECT * FROM orgs")
    Flowable<List<OrgModel>> list();

    @Query("SELECT * FROM orgs WHERE id = :id;")
    Flowable<OrgModel> get(int id);
}

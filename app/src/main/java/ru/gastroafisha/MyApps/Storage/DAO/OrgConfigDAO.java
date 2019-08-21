package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigOrgModel;

/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface OrgConfigDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrIgnore(OrgConfigModel orgConfigModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(OrgConfigModel orgConfigModel);

    @Delete
    void delete(OrgConfigModel orgConfigModel);

    @Query("DELETE FROM orgconfigs WHERE orgId = :id;")
    void delete(Integer id);

    @Query("SELECT * FROM orgconfigs LEFT JOIN orgs ON orgs.id = orgconfigs.orgId;")
    Flowable<List<OrgConfigOrgModel>> listWOrgs();

    @Query("SELECT * FROM orgconfigs;")
    Flowable<List<OrgConfigModel>> list();

    @Query("SELECT * FROM orgconfigs LEFT JOIN orgs ON orgs.id = orgconfigs.orgId WHERE orgconfigs.orgId = :orgId;")
    Flowable<OrgConfigOrgModel> getWOrgs(int orgId);


}

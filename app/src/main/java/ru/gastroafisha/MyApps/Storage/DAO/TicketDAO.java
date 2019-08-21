package ru.gastroafisha.MyApps.Storage.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Model.Org.TicketModel;


/**
 * Created by alex on 24.12.2017.
 */

@Dao
public interface TicketDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TicketModel> tickets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TicketModel ticket);

    @Delete
    void delete(TicketModel ticket);

    @Query("DELETE FROM tickets WHERE orgId = :orgId;")
    void delete(Integer orgId);

    @Query("SELECT * FROM tickets")
    Flowable<List<TicketModel>> list();

    @Query("SELECT * FROM Tickets WHERE orgId = :orgId;")
    Flowable<TicketModel> get(Integer orgId);
}

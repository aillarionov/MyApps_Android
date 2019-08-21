package ru.gastroafisha.MyApps.Storage.Migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

/**
 * Created by alex on 24.12.2017.
 */

public class Migration_1_2 extends Migration {

    public Migration_1_2() {
        super(1, 2);
    }

    @Override
    public void migrate(SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE catalogs ADD COLUMN lastChange INTEGER NOT NULL DEFAULT 0;");
    }
}

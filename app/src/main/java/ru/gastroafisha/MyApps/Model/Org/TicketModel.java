package ru.gastroafisha.MyApps.Model.Org;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ru.gastroafisha.MyApps.Model.ImageModel;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "tickets")
public class TicketModel {

    @PrimaryKey @ColumnInfo(name = "orgId") // SQLite
    private Integer orgId;

    @ColumnInfo(name = "type") // SQLite
    private TicketType type;

    @ColumnInfo(name = "source") // SQLite
    private String source;

    @ColumnInfo(name = "text") // SQLite
    private String text;

    @ColumnInfo(name = "button") // SQLite
    private String button;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}

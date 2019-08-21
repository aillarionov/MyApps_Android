package ru.gastroafisha.MyApps.Model.Item;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import java.util.List;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "catalogmembers")
public class CatalogMemberModel extends CatalogItemModel {

    @ColumnInfo(name = "name") // SQLite
    private String name;

    @ColumnInfo(name = "stand") // SQLite
    private String stand;

    @ColumnInfo(name = "categories") // SQLite
    private List<String> categories;

    @ColumnInfo(name = "emails") // SQLite
    private List<String> emails;

    @ColumnInfo(name = "phones") // SQLite
    private List<String> phones;

    @ColumnInfo(name = "sites") // SQLite
    private List<String> sites;

    @ColumnInfo(name = "vks") // SQLite
    private List<String> vks;

    @ColumnInfo(name = "fbs") // SQLite
    private List<String> fbs;

    @ColumnInfo(name = "insts") // SQLite
    private List<String> insts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getSites() {
        return sites;
    }

    public void setSites(List<String> sites) {
        this.sites = sites;
    }

    public List<String> getVks() {
        return vks;
    }

    public void setVks(List<String> vks) {
        this.vks = vks;
    }

    public List<String> getFbs() {
        return fbs;
    }

    public void setFbs(List<String> fbs) {
        this.fbs = fbs;
    }

    public List<String> getInsts() {
        return insts;
    }

    public void setInsts(List<String> insts) {
        this.insts = insts;
    }

    public String getTitle() {
        return this.name;
    }
}

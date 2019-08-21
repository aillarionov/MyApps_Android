package ru.gastroafisha.MyApps.Proxy;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by alex on 22.12.2017.
 */

public class CatalogMemberProxy extends CatalogItemProxy {

    @SerializedName("name") @Expose // GSON
    private String name;

    @SerializedName("stand") @Expose // GSON
    private String stand;

    @SerializedName("categories") @Expose // GSON
    private List<String> categories;

    @SerializedName("emails") @Expose // GSON
    private List<String> emails;

    @SerializedName("phones") @Expose // GSON
    private List<String> phones;

    @SerializedName("sites") @Expose // GSON
    private List<String> sites;

    @SerializedName("vks") @Expose // GSON
    private List<String> vks;

    @SerializedName("fbs") @Expose // GSON
    private List<String> fbs;

    @SerializedName("insts") @Expose // GSON
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
}

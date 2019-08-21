package ru.gastroafisha.MyApps.Storage;

import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by alex on 26.12.2017.
 */

public class ConfigStore {

    private static String FILE_NAME = "config.json";
    private static String CITYFILE_NAME = "city_config.json";

    private Context context;

    public ConfigStore(Context context) {
        this.context = context;
    }

    public Integer getOrgId() {
        Integer orgId = 0;

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(getFile()));
            orgId = in.readInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orgId;
    }

    public void setOrgId(Integer orgId) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(getFile()));
            out.writeInt(orgId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {
        return new File(context.getFilesDir(), FILE_NAME);
    }

    public Integer getCityId() {
        Integer cityId = null;

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(getCityFile()));
            cityId = in.readInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityId;
    }

    public void setCityId(Integer cityId) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(getCityFile()));
            out.writeInt(cityId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getCityFile() {
        return new File(context.getFilesDir(), CITYFILE_NAME);
    }


}

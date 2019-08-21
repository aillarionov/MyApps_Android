package ru.gastroafisha.MyApps.Proxy;

import java.util.List;
import java.util.Map;


/**
 * Created by alex on 28.02.2018.
 */

public class FormDataProxy {

    private Integer formId;
    private List<Map<String, String>> data;

    public FormDataProxy(Integer formId, List<Map<String, String>> data) {
        this.formId = formId;
        this.data = data;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }
}

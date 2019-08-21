package ru.gastroafisha.MyApps.Utils.Classes;

/**
 * Created by alex on 27.01.2018.
 */

public class Progress {
    private Integer total;
    private Integer current;

    public Progress(Integer total, Integer current) {
        this.total = total;
        this.current = current;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getProgress() {
        return total > 0 ? (current * 100 / total) : 0;
    }
}

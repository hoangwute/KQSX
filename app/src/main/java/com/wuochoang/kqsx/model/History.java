package com.wuochoang.kqsx.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 19-Mar-18.
 */

public class History extends RealmObject {
    private RealmList<InputInfoEntry> entryList;
    private int result;
    private int runTimes;
    private InputInfoEntry comparedEntry;

    @PrimaryKey
    private int id;

    public History(RealmList<InputInfoEntry> entryList, int result, int runTimes, InputInfoEntry comparedEntry) {
        this.entryList = entryList;
        this.result = result;
        this.runTimes = runTimes;
        this.comparedEntry = comparedEntry;
        id = (int) System.currentTimeMillis();
    }

    public History() {
    }

    public List<InputInfoEntry> getEntryList() {
        return entryList;
    }

    public void setEntryList(RealmList<InputInfoEntry> entryList) {
        this.entryList = entryList;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(int runTimes) {
        this.runTimes = runTimes;
    }

    public int getId() {
        return id;
    }

    public InputInfoEntry getComparedEntry() {
        return comparedEntry;
    }

    public void setComparedEntry(InputInfoEntry comparedEntry) {
        this.comparedEntry = comparedEntry;
    }

    public void setId(int id) {
        this.id = id;
    }

}

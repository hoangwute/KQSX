package com.wuochoang.kqsx.manager.database;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by admin on 06-Mar-18.
 */

public class RealmHelper {
    public static void clearAllData(){
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    public static <T extends RealmObject> void save(T object) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(object);
            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    public static <T extends RealmObject> void saveAll(List<T> object) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(object);
            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    public static <T extends RealmObject> void deleteAll(Class<T> clazz){
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(clazz);
            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    public static <T extends RealmObject> void deleteWhere(Class<T> clazz, OnQuerySearch<T> where){
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            RealmQuery<T> query = realm.where(clazz);
            query = where.onQuery(query);
            RealmResults<T> listResults = query.findAll();
            if(listResults != null && listResults.size() > 0)
                listResults.deleteAllFromRealm();
            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    public static <T extends RealmObject> T findFirst(Class<T> clazz){
        return findFirst(clazz, null);
    }

    public static <T extends RealmObject> T findFirst(Class<T> clazz, OnQuerySearch<T> querySearch){
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmQuery<T> query = realm.where(clazz);
            if(querySearch != null)
                query = querySearch.onQuery(query);
            T data = query.findFirst();
            if(data != null) return realm.copyFromRealm(data);
            return null;
        }finally {
            realm.close();
        }
    }

    public static <T extends RealmObject> List<T> findAll(Class<T> clazz){
        return findAll(clazz, null);
    }

    public static <T extends RealmObject> List<T> findAll(Class<T> clazz, String sort){
        return findAll(clazz, sort, -1);
    }

    public static <T extends RealmObject> List<T> findAll(Class<T> clazz, String sort, int limit){
        return findAll(clazz, sort, limit, null);
    }
    public static <T extends RealmObject> List<T> findAll(Class<T> clazz, String sort, int limit, OnQuerySearch<T> querySearch){
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmQuery<T> query = realm.where(clazz);
            if(querySearch != null)
                query = querySearch.onQuery(query);
            List<T> listData = TextUtils.isEmpty(sort) ? query.findAll() : query.findAllSorted(sort, Sort.DESCENDING);
            List<T> listResult = new ArrayList<>();
            if(listData != null && listData.size() > 0) {
                listResult = realm.copyFromRealm(listData);
            }
            if(limit > 0 && limit < listResult.size()) return listResult.subList(0, limit);
            return listResult;
        }finally {
            realm.close();
        }
    }

    public static <T extends RealmObject> List<T> findAll(Class<T> clazz, String sort, int sortType, int limit, OnQuerySearch<T> querySearch){
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmQuery<T> query = realm.where(clazz);
            if(querySearch != null)
                query = querySearch.onQuery(query);
            List<T> listData = TextUtils.isEmpty(sort) ? query.findAll() : query.findAllSorted(sort, sortType == -1 ? Sort.DESCENDING : Sort.ASCENDING);
            List<T> listResult = new ArrayList<>();
            if(listData != null && listData.size() > 0) {
                listResult = realm.copyFromRealm(listData);
            }
            if(limit > 0 && limit < listResult.size()) return listResult.subList(0, limit);
            return listResult;
        }finally {
            realm.close();
        }
    }

    public interface  OnQuerySearch<E extends RealmObject>{
        RealmQuery<E> onQuery(RealmQuery<E> query);
    }

    public static void exportDatabase(Activity context) {

        // init realm
        Realm realm = Realm.getDefaultInstance();

        File exportRealmFile = null;
        try {
            // get or create an "export.realm" file
            exportRealmFile = new File(context.getExternalCacheDir(), "export2.realm");

            // if "export.realm" already exists, delete
            exportRealmFile.delete();

            // copy current realm to "export.realm"
            realm.writeCopyTo(exportRealmFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        realm.close();

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "hoangnq@daganghalal.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "YOUR SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT, "YOUR TEXT");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        context.startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"));
    }
}

package com.test.genplaza.users.ui.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AxxessConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public List<Integer> getIntegerListFromString(String price) {
        List<Integer> list = new ArrayList<>();

        String[] array = price.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String getStringFromIntegerList(List<Integer> list) {
        String price = "";
        for (int i : list) {
            price += "," + i;
        }
        return price;
    }
}
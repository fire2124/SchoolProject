package com.example.filip.schoolproject.RestApi;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ApiTools {

    public static List<Buss> getBussesFromApi(JsonObject dataZoSluzby, Context context) {
                JsonArray buslist = dataZoSluzby.get("busses").getAsJsonArray();
                List<Buss> newBusList = new ArrayList<>();
                for (int i = 0; i < buslist.size(); i++) {
                    Buss newBus = new Buss();
                    newBus.number = buslist.get(i).getAsJsonObject().get("number").getAsString();
                    newBus.departure = buslist.get(i).getAsJsonObject().get("departure").getAsString();
                    newBus.delay = buslist.get(i).getAsJsonObject().get("delay").getAsInt();
                    newBus.direction= buslist.get(i).getAsJsonObject().get("direction").getAsInt();
                    newBus.lat = buslist.get(i).getAsJsonObject().get("lat").getAsDouble();
                    newBus.lng =buslist.get(i).getAsJsonObject().get("lng").getAsDouble();

                    //TODO: doplnit ostatne vlastnosti objektu newbus podla toho co ti vrati API json.//done
                    newBusList.add(newBus);
                }
                return newBusList;
    }
}

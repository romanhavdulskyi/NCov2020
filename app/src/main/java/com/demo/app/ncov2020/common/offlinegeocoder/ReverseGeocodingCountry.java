package com.demo.app.ncov2020.common.offlinegeocoder;

import android.content.Context;

import com.demo.app.ncov2020.R;
import com.google.gson.JsonObject;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by rsanchez on 18/03/15.
 */
public class ReverseGeocodingCountry {

    private JSONArray countriesJsonArray;

    public ReverseGeocodingCountry(Context context) {
        InputStream countriesInputStream = context.getResources().openRawResource(R.raw.countries_geo_code);
        try {
            String jsonCountriesAsString = IOUtils.toString(countriesInputStream);
            countriesJsonArray = new JSONArray(jsonCountriesAsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCountry(GeocodeKey key, double latitude, double longitude) throws JSONException {
        List location = new ArrayList();
        location.add(longitude);
        location.add(latitude);
        JSONArray loc = new JSONArray(location);

        for (int i = 0; i < this.countriesJsonArray.length(); i++) {
            JSONObject country = this.countriesJsonArray.getJSONObject(i);
            JSONObject geometry = country.getJSONObject("geometry");
            String type = geometry.getString("type");
            if (type.equalsIgnoreCase("polygon")) {
                JSONArray polygon = geometry.getJSONArray("coordinates").getJSONArray(0);
                if (contains(polygon, loc)) {
                    return country.getString(key.getValue());
                }
            } else {
                JSONArray polygons = geometry.getJSONArray("coordinates");
                for (int j = 0; j < polygons.length(); j++) {
                    JSONArray polygon = polygons.getJSONArray(j).getJSONArray(0);
                    if (contains(polygon, loc)) {
                        return country.getString(key.getValue());
                    }
                }
            }
        }
        return null;
    }

    public List<Point> getCountryCoordinates(GeocodeKey key, double latitude, double longitude) throws JSONException {
        List<Point> result = new ArrayList<>();
        List location = new ArrayList();
        location.add(longitude);
        location.add(latitude);
        JSONArray loc = new JSONArray(location);

        for (int i = 0; i < this.countriesJsonArray.length(); i++) {
            JSONObject country = this.countriesJsonArray.getJSONObject(i);
            JSONObject geometry = country.getJSONObject("geometry");
            String type = geometry.getString("type");
            result.clear();
            if (type.equalsIgnoreCase("polygon")) {
                JSONArray polygon = geometry.getJSONArray("coordinates").getJSONArray(0);
                result.addAll(convertJSONToLatLng(polygon));
                if (contains(polygon, loc)) {
                    return result;
                }
            } else {
                JSONArray polygons = geometry.getJSONArray("coordinates");
                for (int j = 0; j < polygons.length(); j++) {
                    JSONArray polygon = polygons.getJSONArray(j).getJSONArray(0);
                    result.addAll(convertJSONToLatLng(polygon));
                    if (contains(polygon, loc)) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    private List<Point> convertJSONToLatLng(JSONArray polygon)
    {
        List<Point> latLngList = new ArrayList<>();
        LatLng latLng;
        for(int i = 0; i < polygon.length(); i++)
        {
            try {
                JSONArray jsonObject = polygon.getJSONArray(i);
                double d1 = jsonObject.getDouble(0);
                double d2 = jsonObject.getDouble(1);
              //  Timber.e("D1 %f and D2 %f", d1, d2);
                latLngList.add(Point.fromLngLat(d1, d2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }
        return latLngList;
    }

    // Credits to: http://stackoverflow.com/questions/13950062/checking-if-a-longitude-latitude-coordinate-resides-inside-a-complex-polygon-in
    private boolean contains(JSONArray polyLoc, JSONArray location) throws JSONException {
        if (location==null)
            return false;

        JSONArray lastPoint = polyLoc.getJSONArray(polyLoc.length() - 1);
        boolean isInside = false;
        double x = location.getDouble(0);

        for (int i = 0; i < polyLoc.length(); i++) {
            JSONArray point = polyLoc.getJSONArray(i);
            double x1 = lastPoint.getDouble(0);
            double x2 = point.getDouble(0);
            double dx = x2 - x1;

            if (Math.abs(dx) > 180.0)
            {
                // we have, most likely, just jumped the dateline (could do further validation to this effect if needed).  normalise the numbers.
                if (x > 0)
                {
                    while (x1 < 0)
                        x1 += 360;
                    while (x2 < 0)
                        x2 += 360;
                }
                else
                {
                    while (x1 > 0)
                        x1 -= 360;
                    while (x2 > 0)
                        x2 -= 360;
                }
                dx = x2 - x1;
            }

            if ((x1 <= x && x2 > x) || (x1 >= x && x2 < x))
            {
                double grad = (point.getDouble(1) - lastPoint.getDouble(1)) / dx;
                double intersectAtLat = lastPoint.getDouble(1) + ((x - x1) * grad);

                if (intersectAtLat > location.getDouble(1))
                    isInside = !isInside;
            }
            lastPoint = point;
        }

        return isInside;
    }
}

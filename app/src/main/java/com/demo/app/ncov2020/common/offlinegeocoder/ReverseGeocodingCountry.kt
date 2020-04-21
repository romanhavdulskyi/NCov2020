package com.demo.app.ncov2020.common.offlinegeocoder

import android.content.Context
import com.demo.app.ncov2020.R
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import org.apache.commons.io.IOUtils
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class ReverseGeocodingCountry private constructor(context: Context) {
    private var countriesJsonArray: JSONArray? = null
    @Throws(JSONException::class)
    fun getCountry(key: GeocodeKey, latitude: Double, longitude: Double): String? {
        val location: MutableList<Any> = mutableListOf()
        location.add(longitude)
        location.add(latitude)
        val loc = JSONArray(location)
        for (i in 0 until countriesJsonArray!!.length()) {
            val country = countriesJsonArray!!.getJSONObject(i)
            val geometry = country.getJSONObject("geometry")
            val type = geometry.getString("type")
            if (type.equals("polygon", ignoreCase = true)) {
                val polygon = geometry.getJSONArray("coordinates").getJSONArray(0)
                if (contains(polygon, loc)) {
                    return country.getString(key.value)
                }
            } else {
                val polygons = geometry.getJSONArray("coordinates")
                for (j in 0 until polygons.length()) {
                    val polygon = polygons.getJSONArray(j).getJSONArray(0)
                    if (contains(polygon, loc)) {
                        return country.getString(key.value)
                    }
                }
            }
        }
        return null
    }

    @Throws(JSONException::class)
    fun getCountryCoordinates(latitude: Double, longitude: Double): GeoDataMapBox? {
        val geocodeDataMapBox = GeoDataMapBox()
        val result: MutableList<Point> = ArrayList()
        val multipolygon: MutableList<List<Point>> = ArrayList()
        val location: MutableList<Double?> = ArrayList()
        location.add(longitude)
        location.add(latitude)
        val loc = JSONArray(location)
        for (i in 0 until countriesJsonArray!!.length()) {
            val country = countriesJsonArray!!.getJSONObject(i)
            val geometry = country.getJSONObject("geometry")
            val type = geometry.getString("type")
            result.clear()
            multipolygon.clear()
            if (type.equals("polygon", ignoreCase = true)) {
                val polygon = geometry.getJSONArray("coordinates").getJSONArray(0)
                result.addAll(convertJSONToLatLng(polygon))
                if (contains(polygon, loc)) {
                    geocodeDataMapBox.addPolygon(Polygon(result))
                    return geocodeDataMapBox
                }
            } else {
                val polygons = geometry.getJSONArray("coordinates")
                for (j in 0 until polygons.length()) {
                    val polygon = polygons.getJSONArray(j).getJSONArray(0)
                    multipolygon.add(convertJSONToLatLng(polygon))
                    if (contains(polygon, loc)) {
                        for (pointList in multipolygon) {
                            geocodeDataMapBox.addPolygon(Polygon(pointList))
                        }
                        return geocodeDataMapBox
                    }
                }
            }
        }
        return null
    }

    @Throws(JSONException::class)
    fun getCountryCoordinates(geocodeKey: GeocodeKey, name: String): GeoDataMapBox? {
        val geocodeDataMapBox = GeoDataMapBox()
        val result: MutableList<Point> = ArrayList()
        val multipolygon: MutableList<List<Point>> = ArrayList()
        for (i in 0 until countriesJsonArray!!.length()) {
            val country = countriesJsonArray!!.getJSONObject(i)
            if (country.getString(geocodeKey.value) != name) continue
            val geometry = country.getJSONObject("geometry")
            val type = geometry.getString("type")
            result.clear()
            if (type.equals("polygon", ignoreCase = true)) {
                val polygon = geometry.getJSONArray("coordinates").getJSONArray(0)
                result.addAll(convertJSONToLatLng(polygon))
                geocodeDataMapBox.addPolygon(Polygon(result))
                return geocodeDataMapBox
            } else {
                val polygons = geometry.getJSONArray("coordinates")
                for (j in 0 until polygons.length()) {
                    val polygon = polygons.getJSONArray(j).getJSONArray(0)
                    multipolygon.add(convertJSONToLatLng(polygon))
                    for (pointList in multipolygon) {
                        geocodeDataMapBox.addPolygon(Polygon(pointList))
                        return geocodeDataMapBox
                    }
                }
            }
        }
        return null
    }

    private fun convertJSONToLatLng(polygon: JSONArray): List<Point> {
        val latLngList: MutableList<Point> = ArrayList()
        var latLng: LatLng
        for (i in 0 until polygon.length()) {
            try {
                val jsonObject = polygon.getJSONArray(i)
                val d1 = jsonObject.getDouble(0)
                val d2 = jsonObject.getDouble(1)
                //  Timber.e("D1 %f and D2 %f", d1, d2);
                latLngList.add(Point.fromLngLat(d1, d2))
            } catch (e: JSONException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
        return latLngList
    }

    // Credits to: http://stackoverflow.com/questions/13950062/checking-if-a-longitude-latitude-coordinate-resides-inside-a-complex-polygon-in
    @Throws(JSONException::class)
    private fun contains(polyLoc: JSONArray, location: JSONArray?): Boolean {
        if (location == null) return false
        var lastPoint = polyLoc.getJSONArray(polyLoc.length() - 1)
        var isInside = false
        val x = location.getDouble(0)
        for (i in 0 until polyLoc.length()) {
            val point = polyLoc.getJSONArray(i)
            var x1 = lastPoint.getDouble(0)
            var x2 = point.getDouble(0)
            var dx = x2 - x1
            if (Math.abs(dx) > 180.0) { // we have, most likely, just jumped the dateline (could do further validation to this effect if needed).  normalise the numbers.
                if (x > 0) {
                    while (x1 < 0) x1 += 360.0
                    while (x2 < 0) x2 += 360.0
                } else {
                    while (x1 > 0) x1 -= 360.0
                    while (x2 > 0) x2 -= 360.0
                }
                dx = x2 - x1
            }
            if (x1 <= x && x2 > x || x1 >= x && x2 < x) {
                val grad = (point.getDouble(1) - lastPoint.getDouble(1)) / dx
                val intersectAtLat = lastPoint.getDouble(1) + (x - x1) * grad
                if (intersectAtLat > location.getDouble(1)) isInside = !isInside
            }
            lastPoint = point
        }
        return isInside
    }

    companion object {
        private var instance: ReverseGeocodingCountry? = null
        fun init(context: Context) {
            instance = ReverseGeocodingCountry(context)
        }

        fun getInstance(): ReverseGeocodingCountry? {
            checkNotNull(instance) { "Should be initialized before" }
            return instance
        }
    }

    init {
        val countriesInputStream = context.resources.openRawResource(R.raw.countries_geo_code)
        try {
            val jsonCountriesAsString = IOUtils.toString(countriesInputStream)
            countriesJsonArray = JSONArray(jsonCountriesAsString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
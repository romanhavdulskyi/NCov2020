package com.demo.app.ncov2020.common.offlinegeocoder

import android.text.TextUtils
import com.mapbox.mapboxsdk.geometry.LatLng

object GeoDataFactory {
    private val polygonCache = HashMap<String, GeoData?>()
    private val countryCache = HashMap<LatLng, String?>()

    fun getPolygon(countryName : String) : GeoData?
    {
        if(!polygonCache.containsKey(countryName))
            polygonCache[countryName] =  ReverseGeocodingCountry.getInstance()?.getCountryCoordinates(GeocodeKey.KEY_NAME, countryName)

        return polygonCache[countryName]
    }

    fun getPolygon(touchPoint : LatLng) : GeoData
    {
        val name = getCountryName(touchPoint)
        if(!polygonCache.containsKey(name) && !TextUtils.isEmpty(name))
            polygonCache[name!!] =  ReverseGeocodingCountry.getInstance()?.getCountryCoordinates(touchPoint.latitude, touchPoint.longitude)

        return polygonCache[name] !!
    }

    @JvmStatic
    fun getCountryName(touchPoint: LatLng) : String?
    {
        if(!countryCache.containsKey(touchPoint))
          countryCache[touchPoint] =  ReverseGeocodingCountry.getInstance()?.getCountry(GeocodeKey.KEY_NAME, touchPoint.latitude, touchPoint.longitude)

        return countryCache[touchPoint]
    }

}
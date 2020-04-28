package com.demo.app.ncov2020.map

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.demo.app.basics.mvvm.BaseAndroidViewModel
import com.demo.app.ncov2020.common.TimeUtils
import com.demo.app.ncov2020.common.offlinegeocoder.GeoDataFactory
import com.demo.app.ncov2020.game.Game
import com.demo.app.ncov2020.game.GameProvider
import com.demo.app.ncov2020.game.GameProviderImpl
import com.demo.app.ncov2020.userprofile.CurrentSession
import com.demo.app.ncov2020.userprofile.login.CurrentSessionManager
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.offline.*
import com.mapbox.mapboxsdk.offline.OfflineManager.CreateOfflineRegionCallback
import com.mapbox.mapboxsdk.offline.OfflineRegion.OfflineRegionObserver
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import org.json.JSONObject
import timber.log.Timber
import kotlin.math.roundToInt


class MapViewModel(application: Application) : BaseAndroidViewModel(application) {
    var mapLiveData: MutableLiveData<Map> = MutableLiveData()
    private var mapBoxMap: MapboxMap? = null
    private val JSON_CHARSET = "UTF-8"
    private var gameProvider = GameProviderImpl.INSTANCE
    private val JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME"

    init {
        Mapbox.getInstance(application, "pk.eyJ1IjoibmNvdmdhbWUiLCJhIjoiY2s3eWpjcjJjMDdnZTNqcGZ2ZXBxMGYxdSJ9.IBqgc27bmXnxY2G6iF-MiQ")

        val map = Map(0, false, currDate = "")
        mapLiveData.value = map
        gameProvider.addClient(object : GameProvider.Client {
            override fun onChanged(state: Game) {
                Timber.e("State %s", state)
                val mapValue = mapLiveData.value
                mapValue?.hardInfectedPoints?.clear()
                mapValue?.mediumInfectedPoints?.clear()
                mapValue?.lowInfectedPoints?.clear()

                if (state.hardLevelInfectedCountry.isNotEmpty())
                    for (item in state.hardLevelInfectedCountry)
                        mapValue?.hardInfectedPoints?.add(getPointsForCountry(item))

                if (state.mediumLevelInfectedCountry.isNotEmpty())
                    for (item in state.mediumLevelInfectedCountry)
                        mapValue?.mediumInfectedPoints?.add(getPointsForCountry(item))


                if (state.lowLevelInfectedCountry.isNotEmpty())
                    for (item in state.lowLevelInfectedCountry)
                        mapValue?.lowInfectedPoints?.add(getPointsForCountry(item))

                mapValue?.currDate = state.dateTime?.let { TimeUtils.formatDate(it) }.toString()

                mapLiveData.postValue(mapValue)
            }
        })
    }

    fun loadMap(mapboxMap: MapboxMap, definition: OfflineTilePyramidRegionDefinition) {
        this.mapBoxMap = mapboxMap
        val offlineManager: OfflineManager? = OfflineManager.getInstance(application)
        val metadata: ByteArray?
        metadata = try {
            val jsonObject = JSONObject()
            jsonObject.put(JSON_FIELD_REGION_NAME, "World")
            val json = jsonObject.toString()
            json.toByteArray(charset(JSON_CHARSET))
        } catch (exception: Exception) {
            Timber.e("Failed to encode metadata: %s", exception.message)
            null
        }
        // Create the region asynchronously
        createRegion(metadata, offlineManager, definition)

    }

    private fun createRegion(metadata: ByteArray?, offlineManager: OfflineManager?, definition: OfflineTilePyramidRegionDefinition) {
        if (metadata != null) {
            offlineManager?.createOfflineRegion(
                    definition,
                    metadata,
                    object : CreateOfflineRegionCallback {
                        override fun onCreate(offlineRegion: OfflineRegion) {
                            offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE)
                            // Display the download progress bar
                            //startProgress()
                            // Monitor the download progress using setObserver
                            offlineRegion.setObserver(object : OfflineRegionObserver {
                                override fun onStatusChanged(status: OfflineRegionStatus) { // Calculate the download percentage and update the progress bar
                                    val percentage = if (status.requiredResourceCount >= 0) 100.0 * status.completedResourceCount / status.requiredResourceCount else 0.0
                                    if (status.isComplete) { // Download complete
                                        endProgress()
                                    } else if (status.isRequiredResourceCountPrecise) { // Switch to determinate state
                                        setPercentage(percentage.roundToInt())
                                    }
                                }

                                override fun onError(error: OfflineRegionError) { // If an error occurs, print to logcat
                                    Timber.e("onError reason: %s", error.reason)
                                    Timber.e("onError message: %s", error.message)
                                }

                                override fun mapboxTileCountLimitExceeded(limit: Long) { // Notify if offline region exceeds maximum tile count
                                    Timber.e("Mapbox tile count limit exceeded: %s", limit)
                                }
                            })
                        }

                        override fun onError(error: String) {
                            Timber.e("Error: %s", error)
                        }
                    })
        }
    }

    fun onMapClicked(point: LatLng) {
        val map = mapLiveData.value
        map?.let { value ->
            run {
                val countryName = GeoDataFactory.getCountryName(point)
                gameProvider.infectCountry(countryName)
            }
        }
    }

    fun getPointsForCountry(countryName: String): MutableList<MutableList<Point>> {
        val points = mutableListOf<MutableList<Point>>()
        val data = GeoDataFactory.getPolygon(countryName)
        if (data.getPolygons()!!.isNotEmpty()) {
            points.clear()
            for (item in data.getPolygons()!!)
                points.add(item!!.points as MutableList<Point>)
        }
        return points
    }

    private fun startProgress() {
        val mapValue = mapLiveData.value
        mapValue?.let { map ->
            map.isLoading = true
            map.loadPercentage = 0
            mapLiveData.postValue(map)
        }
    }

    private fun endProgress() {
        val mapValue = mapLiveData.value
        mapValue?.let { map ->
            if(map.isLoading)
                CurrentSession.instance.playerGUID?.let { gameProvider.initGame(it) }
            map.isLoading = false
            map.loadPercentage = 0
            mapLiveData.postValue(map)
        }

    }

    private fun setPercentage(percentage: Int) {
        val mapValue = mapLiveData.value
        mapValue?.let { map ->
            map.loadPercentage = percentage
            map.isLoading = true
            mapLiveData.postValue(map)
        }
    }
}

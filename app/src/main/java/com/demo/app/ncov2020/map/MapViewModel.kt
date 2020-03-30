package com.demo.app.ncov2020.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.demo.app.ncov2020.MainActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.offline.*
import com.mapbox.mapboxsdk.offline.OfflineManager.CreateOfflineRegionCallback
import com.mapbox.mapboxsdk.offline.OfflineRegion.OfflineRegionObserver
import org.json.JSONObject
import timber.log.Timber
import kotlin.math.roundToInt

class MapViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    var mapLiveData : MutableLiveData<Map> = MutableLiveData()
    private val JSON_CHARSET = "UTF-8"
    private val JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME"
    init {
        Mapbox.getInstance(getApplication(), "pk.eyJ1IjoibmNvdmdhbWUiLCJhIjoiY2s3eWpjcjJjMDdnZTNqcGZ2ZXBxMGYxdSJ9.IBqgc27bmXnxY2G6iF-MiQ")
        val map = Map(0, false)
        mapLiveData.value =  map
    }

    fun loadMap(style : Style)
    {
        val offlineManager: OfflineManager? = OfflineManager.getInstance(getApplication())
        val context = getApplication<Application>()

        val latLngBounds = LatLngBounds.Builder()
                .include(LatLng(37.7897, -119.5073)) // Northeast
                .include(LatLng(37.6744, -119.6815)) // Southwest
                .build()

        val definition = OfflineTilePyramidRegionDefinition(
                style.uri,
                latLngBounds,
                10.0,
                20.0,
                context.resources.displayMetrics.density)

        // Set the metadata
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

    private fun createRegion(metadata: ByteArray?, offlineManager: OfflineManager?, definition: OfflineTilePyramidRegionDefinition)
    {
        if (metadata != null) {
            offlineManager?.createOfflineRegion(
                    definition,
                    metadata,
                    object : CreateOfflineRegionCallback {
                        override fun onCreate(offlineRegion: OfflineRegion) {
                            offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE)
                            // Display the download progress bar
                            startProgress()
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

    private fun startProgress()
    {
        val mapValue = mapLiveData.value
        mapValue?.let { map -> map.isLoading = true
        map.loadPercentage = 0
        mapLiveData.postValue(map)
        }
    }

    private fun endProgress()
    {
        val mapValue = mapLiveData.value
        mapValue?.let { map -> map.isLoading = false
        map.loadPercentage = 0
        mapLiveData.postValue(map)
        }
    }

    private fun setPercentage(percentage : Int)
    {
        val mapValue = mapLiveData.value
        mapValue?.let { map ->
        map.loadPercentage = percentage
        mapLiveData.postValue(map)
        }
    }
}

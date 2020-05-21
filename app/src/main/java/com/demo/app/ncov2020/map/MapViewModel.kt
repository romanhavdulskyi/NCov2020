package com.demo.app.ncov2020.map

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.demo.app.basics.mvvm.BaseAndroidViewModel
import com.demo.app.basics.mvvm.ViewModelState
import com.demo.app.ncov2020.common.TimeUtils
import com.demo.app.ncov2020.common.offlinegeocoder.GeoDataFactory
import com.demo.app.ncov2020.game.Game
import com.demo.app.ncov2020.game.GameProvider
import com.demo.app.ncov2020.game.GameProviderImpl
import com.demo.app.ncov2020.gamedialogs.GameDialogsImpl
import com.demo.app.ncov2020.logic.Callback.CallbackType
import com.demo.app.ncov2020.logic.MainPart.*
import com.demo.app.ncov2020.userprofile.CurrentSession
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.offline.*
import com.mapbox.mapboxsdk.offline.OfflineManager.CreateOfflineRegionCallback
import com.mapbox.mapboxsdk.offline.OfflineRegion.OfflineRegionObserver
import org.json.JSONObject
import timber.log.Timber
import kotlin.math.roundToInt


class MapViewModel(application: Application) : BaseAndroidViewModel(application), GameProvider.Client {
    private var viewModelState : ViewModelState = ViewModelState.INITIALIZED
    var mapStateLiveData: MutableLiveData<MapState> = MutableLiveData()
    private var mapBoxMap: MapboxMap? = null
    private val JSON_CHARSET = "UTF-8"
    private var gameProvider = GameProviderImpl.INSTANCE
    private val JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME"
    private val countryMap: HashMap<String, MapCountryData> = HashMap()

    init {
        Mapbox.getInstance(application, "pk.eyJ1IjoibmNvdmdhbWUiLCJhIjoiY2s3eWpjcjJjMDdnZTNqcGZ2ZXBxMGYxdSJ9.IBqgc27bmXnxY2G6iF-MiQ")

        val map = MapState(0, true, currDate = "", upgradePoints = "0", removeCountry = mutableListOf(), updateCountry = mutableListOf(), addCountry = mutableListOf(), messageText = "")
        mapStateLiveData.value = map
    }

    fun onAttach()
    {
        viewModelState = ViewModelState.ATTACHED
        gameProvider.addClient(this)
    }

    fun onDetach()
    {

        viewModelState = ViewModelState.DETACHED
        gameProvider.removeClient(this)
        countryMap.clear()
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
        val map = mapStateLiveData.value
        map?.let { value ->
            run {
                val countryName = GeoDataFactory.getCountryName(point)
                countryName?.let {
                    if(value.selectStrategyMode)
                        gameProvider.setCountryForStrategy(it)
                    else
                        GameDialogsImpl.openCountryDialog(it)

                    setSelectStrategyMode(false)
                }
            }
        }
    }


    private fun startProgress() {
        val mapValue = mapStateLiveData.value
        mapValue?.let { map ->
            map.isLoading = true
            map.loadPercentage = 0
            mapStateLiveData.postValue(map)
        }
    }

    private fun endProgress() {
        val mapValue = mapStateLiveData.value
        mapValue?.let { map ->
            if(map.isLoading)
                CurrentSession.instance.playerGUID?.let { gameProvider.initGame(it) }
            map.isLoading = false
            map.loadPercentage = 0
            mapStateLiveData.postValue(map)
        }

    }

    private fun setPercentage(percentage: Int) {
        val mapValue = mapStateLiveData.value
        mapValue?.let { map ->
            map.loadPercentage = percentage
            map.isLoading = true
            mapStateLiveData.postValue(map)
        }
    }

    override fun onChanged(state: Game) {
        Timber.e("State %s", state)
        val mapValue = mapStateLiveData.value
        if(state.callbackReason == CallbackType.STRATEGYEXECUTED)
        {
            mapValue?.messageText = "Strategy was executed!"
            mapValue?.selectStrategyMode = false
            mapValue?.showMessage = true
        } else if(state.callbackReason == CallbackType.STRATEGYFAILED) {
            mapValue?.messageText = "You can`t use this strategy for this country...!"
            mapValue?.selectStrategyMode = false
            mapValue?.showMessage = true
        }
        mapValue?.removeCountry?.clear()
        mapValue?.addCountry?.clear()
        mapValue?.updateCountry?.clear()

        for(item in state.infectedCountryShort) {
            if (countryMap.containsKey(item.key))
            {
                if(!countryMap[item.key]?.equals(item)!!) {
                    mapValue?.updateCountry?.add(item.value)
                    countryMap[item.key] = item.value
                }
            } else {
                countryMap[item.key] = item.value
                mapValue?.addCountry?.add(item.value)
            }
        }

        for(item in countryMap)
        {
            if(!state.infectedCountryShort.containsKey(item.key)) {
                mapValue?.removeCountry?.add(item.value)
                countryMap.remove(item.key)
            }
        }

        mapValue?.currDate = state.dateTime?.let { TimeUtils.formatDate(it) }.toString()
        mapValue?.upgradePoints =  "Points: " + state.upgradePoints.toString()

        mapStateLiveData.postValue(mapValue)
    }

    private fun setSelectStrategyMode(value: Boolean)
    {
        val mapValue = mapStateLiveData.value
        mapValue?.let { map ->
            map.selectStrategyMode = value
            mapStateLiveData.postValue(map)
        }
    }

    private fun setMessageText(text: String)
    {
        val mapValue = mapStateLiveData.value
        mapValue?.let { map ->
            map.messageText = text
            mapStateLiveData.postValue(map)
        }
    }

    private fun setShowMessage(value: Boolean)
    {
        val mapValue = mapStateLiveData.value
        mapValue?.let { map ->
            map.showMessage = value
            mapStateLiveData.postValue(map)
        }
    }

    fun onMenuClicked(v : View)
    {
        GameDialogsImpl.openDiseaseDialog()
    }

    fun onInfectSmall(v : View)
    {
        setSelectStrategyMode(true)
        setShowMessage(true)
        setMessageText("Select country for infect small strategy")
        gameProvider.setStrategy(StrategyInfectSmall())
    }

    fun onInfectMid(v : View)
    {
        setSelectStrategyMode(true)
        setShowMessage(true)
        setMessageText("Select country for infect medium strategy")
        gameProvider.setStrategy(StrategyInfectMid())
    }

    fun onTravelGround(v : View)
    {
        setSelectStrategyMode(true)
        setMessageText("Select country for travel ground strategy")
        gameProvider.setStrategy(StrategyTravelGround())
    }

    fun onKill(v : View)
    {
        setSelectStrategyMode(true)
        setShowMessage(true)
        setMessageText("Select country for death strategy")
        gameProvider.setStrategy(StrategyKill())
    }

    fun onBusinessRebellion(v : View)
    {
        setSelectStrategyMode(true)
        setShowMessage(true)
        setMessageText("Select country for business rebellion strategy")
        gameProvider.setStrategy(StrategyBusinessRebellion())
    }

    fun dismissMessage(v: View)
    {
        val mapValue = mapStateLiveData.value
        mapValue?.let { map ->
            map.showMessage = false
            map.messageText = ""
            map.selectStrategyMode = false;
            mapStateLiveData.postValue(map)
        }
    }

    fun dismissAction(v: View)
    {
        val mapValue = mapStateLiveData.value
        mapValue?.let { map ->
            map.showMessage = false
            map.messageText = ""
            map.selectStrategyMode = false;
            mapStateLiveData.postValue(map)
        }
    }
}

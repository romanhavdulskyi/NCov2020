package com.demo.app.ncov2020.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.demo.app.basics.mvvm.BaseView
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.common.ColorUtils
import com.demo.app.ncov2020.databinding.MapFragmentBinding
import com.demo.app.ncov2020.map.commands.*
import com.mapbox.geojson.Polygon
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import timber.log.Timber
import java.util.*
import kotlin.collections.HashMap

class MapView(activity: FragmentActivity?, lifecycleOwner: LifecycleOwner,
              inflater: LayoutInflater,
              container: ViewGroup?,
              model: MapViewModel, savedInstanceState: Bundle?) : BaseView(), MapCommandExecutable {

    private var mapBoxMap: MapboxMap? = null
    private var geoJsonSource: HashMap<String, GeoJsonSource?> = HashMap()
    private var fillLayer: HashMap<String, FillLayer?> = HashMap()
    var style: Style? = null
    lateinit var mapView: MapView

    init {
        val mViewBinding: MapFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false)
        mViewBinding.lifecycleOwner = lifecycleOwner
        mViewBinding.viewModel = model
        mViewBinding.executePendingBindings()
        viewLayout = mViewBinding.root
        mapView = viewLayout.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
            this.mapBoxMap = mapboxMap
            mapboxMap.addOnMapClickListener { point ->
                model.onMapClicked(point)
                true
            }

            mapboxMap.setMaxZoomPreference(3.0)
            mapboxMap.setMinZoomPreference(1.0)
            mapboxMap.uiSettings.isRotateGesturesEnabled = false
            mapboxMap.setStyle(Style.DARK) {
                style = it
                val latLngBounds = LatLngBounds.Builder()
                        .include(LatLng(37.7897, -119.5073)) // Northeast
                        .include(LatLng(37.6744, -119.6815)) // Southwest
                        .build()

                val definition = activity?.resources?.displayMetrics?.density?.let { it1 ->
                    OfflineTilePyramidRegionDefinition(
                            it.uri,
                            latLngBounds,
                            10.0,
                            20.0,
                            it1)
                }
                definition?.let { it1 -> model.loadMap(mapboxMap, it1) }
            }
        }

        model.mapLiveData.observe(lifecycleOwner, Observer { mapValue ->
            run {

                val mapCommandProcessor = MapCommandProcessor(this@MapView)
                for(item in mapValue.removeCountry)
                    mapCommandProcessor.addToQueue(RemoveCountryCommand(item))
                for(item in mapValue.addCountry)
                    mapCommandProcessor.addToQueue(AddCountryCommand(item))
                for(item in mapValue.updateCountry)
                    mapCommandProcessor.addToQueue(UpdateCountryCommand(item))

                mapCommandProcessor.processCommand()
            }
        })
    }

    @Synchronized
    private fun cleanMap() {
        if (geoJsonSource.isNotEmpty())
            for (item in geoJsonSource)
                style?.removeSource(item.value!!)

        if (fillLayer.isNotEmpty())
            for (item in fillLayer)
                item.let {
                    style?.removeLayer(it.value!!)
                }

        geoJsonSource.clear()
        fillLayer.clear()
    }

    @Synchronized
    override fun addInfectedCountry(mapCountryData: MapCountryData) {
        val date = Date()
        val uuid = UUID.randomUUID().toString() + "" + date.time
        val source = GeoJsonSource(uuid, Polygon.fromLngLats(mapCountryData.points))
        geoJsonSource[mapCountryData.countryName] = (source)
        style?.addSource(source)
        Timber.e("Added source")

        val layer = FillLayer(uuid, uuid).withProperties(PropertyFactory.fillColor(ColorUtils.genColor( mapCountryData.infectedCoefficient)))

        fillLayer[mapCountryData.countryName] = layer
        style?.addLayerBelow(layer, "settlement-label")
        Timber.e("Added layer")
    }

    @Synchronized
    override fun updateInfectedCountry(mapCountryData: MapCountryData) {
        fillLayer[mapCountryData.countryName]?.setProperties(PropertyFactory.fillColor(ColorUtils.genColor(mapCountryData.infectedCoefficient)))
    }

    @Synchronized
    override fun removeInfectedCountry(mapCountryData: MapCountryData) {

        style?.removeSource(geoJsonSource[mapCountryData.countryName]!!)
        style?.removeLayer(fillLayer[mapCountryData.countryName]!!)

        geoJsonSource.remove(mapCountryData.countryName)
        fillLayer.remove(mapCountryData.countryName)
    }

    fun onCreate(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
    }

    fun onResume() {
        mapView.onResume()
    }

    fun onPause() {
        mapView.onPause()
    }

    fun onSaveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { mapView.onSaveInstanceState(it) }
    }

    fun onLowMemory() {
        mapView.onLowMemory()
    }

    fun onDestroy() {
        mapView.onDestroy()
    }
}
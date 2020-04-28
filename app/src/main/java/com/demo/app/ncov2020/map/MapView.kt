package com.demo.app.ncov2020.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ComplexColorCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.demo.app.basics.mvvm.BaseActivity
import com.demo.app.basics.mvvm.BaseView
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.databinding.MapFragmentBinding
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import timber.log.Timber
import java.lang.Error
import java.util.*

class MapView(activity: FragmentActivity?, lifecycleOwner: LifecycleOwner,
              inflater: LayoutInflater,
              container: ViewGroup?,
              model: MapViewModel, savedInstanceState: Bundle?) : BaseView() {

    private var mapBoxMap: MapboxMap? = null
    private var geoJsonSource: MutableList<GeoJsonSource?> = mutableListOf()
    private var fillLayer: MutableList<FillLayer?> = mutableListOf()
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
                cleanMap()


                if (mapValue.hardInfectedPoints.isNotEmpty()) {
                    val temp = mutableListOf<MutableList<Point>>()
                    for (item in mapValue.hardInfectedPoints)
                        temp.addAll(item)

                    Timber.e("Draw hard infected")
                     renderInfectedCountry(temp, ContextCompat.getColor(activity!!, R.color.hardInfected))
                }

                if (mapValue.mediumInfectedPoints.isNotEmpty()) {
                    val temp = mutableListOf<MutableList<Point>>()
                    for (item in mapValue.mediumInfectedPoints)
                        temp.addAll(item)

                    Timber.e("Draw medium infected")
                    renderInfectedCountry(temp, ContextCompat.getColor(activity!!, R.color.mediumInfected))
                }

                if (mapValue.lowInfectedPoints.isNotEmpty()) {
                    val temp = mutableListOf<MutableList<Point>>()
                    for (item in mapValue.lowInfectedPoints)
                        temp.addAll(item)

                    Timber.e("Draw low infected")
                    renderInfectedCountry(temp, ContextCompat.getColor(activity!!, R.color.lowInfected))
                }

            }
        })
    }

    @Synchronized
    private fun cleanMap() {
            if (geoJsonSource.isNotEmpty())
                for (item in geoJsonSource)
                    style?.removeSource(item!!)

            if (fillLayer.isNotEmpty())
                for (item in fillLayer)
                        item.let {
                            if (it != null) {
                                style?.removeLayer(it)
                            }
                        }

        geoJsonSource.clear()
        fillLayer.clear()
    }

    @Synchronized
    private fun renderInfectedCountry(points: MutableList<MutableList<Point>>, color: Int) {
            val date = Date()
            val uuid = UUID.randomUUID().toString() + "" + date.time
            val source  = GeoJsonSource(uuid, Polygon.fromLngLats(points))
            geoJsonSource.add(source)
            style?.addSource(source)
            Timber.e("Added source")
            val layer = FillLayer(uuid, uuid).withProperties(PropertyFactory.fillColor(color))

            fillLayer.add(layer)
            style?.addLayerBelow(layer, "settlement-label")
            Timber.e("Added layer")
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
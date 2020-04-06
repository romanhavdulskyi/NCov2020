package com.demo.app.ncov2020.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.demo.app.basics.mvvm.BaseActivity
import com.demo.app.basics.mvvm.BaseView
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.databinding.MapFragmentBinding
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import timber.log.Timber

class MapView(activity: FragmentActivity?, lifecycleOwner: LifecycleOwner,
              inflater: LayoutInflater,
              container: ViewGroup?,
              model: MapViewModel, savedInstanceState: Bundle?) : BaseView() {
    init {
        val mViewBinding: MapFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false)
        mViewBinding.lifecycleOwner = lifecycleOwner
        mViewBinding.viewModel = model
        mViewBinding.executePendingBindings()
        viewLayout = mViewBinding.root
        val mapView: MapView = viewLayout.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.addOnMapClickListener { point ->
                model.onMapClicked(point)
                true
            }
            model.loadMap(mapboxMap)
        }
    }
}
package com.demo.app.ncov2020.map

import android.os.Bundle
import android.view.*
import com.demo.app.basics.mvvm.BaseFragment
import com.demo.app.ncov2020.common.ViewModelProvider

class MapFragment : BaseFragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MapViewModel
    private  var mapView:MapView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider.getInstance()?.getViewModel(MapViewModel::class.java) as MapViewModel
        mapView = MapView(activity, this, inflater, container, viewModel, savedInstanceState)
        return mapView?.viewLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView?.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }


}

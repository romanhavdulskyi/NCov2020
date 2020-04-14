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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider.getInstance()?.getViewModel(MapViewModel::class.java) as MapViewModel
        val mapView = MapView(activity, this, inflater, container, viewModel, savedInstanceState)
        return mapView.viewLayout
    }


}

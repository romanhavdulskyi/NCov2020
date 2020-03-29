package com.demo.app.ncov2020.map

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import com.demo.app.basics.mvvm.BaseFragment

class MapFragment : BaseFragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        val mapView = MapView(activity, this, inflater, container, viewModel, savedInstanceState)
        return mapView.viewLayout
    }


}

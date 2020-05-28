package com.demo.app.ncov2020.gamedialogs.disease

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.data.GameProperties
import com.demo.app.ncov2020.game.Game
import com.demo.app.ncov2020.game.GameProvider
import com.demo.app.ncov2020.game.GameProviderImpl
import com.demo.app.ncov2020.logic.Callback.CallbackType
import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Transmission

class DiseaseTransmissionFragment : Fragment(), GameProvider.Client, TransmissionRecyclerAdapter.Callback {
    private lateinit var recyclerView : RecyclerView
    private lateinit var cardInfoView : CardView
    private lateinit var nameView : TextView
    private lateinit var descView : TextView
    private lateinit var addInfoView : TextView
    private lateinit var closeBtn : AppCompatImageButton
    private lateinit var buyBtn : Button

    private var selectedTransmission : Transmission? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.disease_transmission_dialog, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        cardInfoView = rootView.findViewById(R.id.cardInfoView)
        nameView = rootView.findViewById(R.id.nameView)
        descView = rootView.findViewById(R.id.descView)
        addInfoView = rootView.findViewById(R.id.addInfoView)
        closeBtn = rootView.findViewById(R.id.closeBtn)
        buyBtn = rootView.findViewById(R.id.buyBtn)
        recyclerView.layoutManager = GridLayoutManager(activity, 6) as RecyclerView.LayoutManager?
        GameProviderImpl.INSTANCE.addClient(this)
        cardInfoView.visibility = View.GONE
        closeBtn.setOnClickListener {
            selectedTransmission = null
            cardInfoView.visibility = View.GONE }
        buyBtn.setOnClickListener {
            selectedTransmission?.let { GameProviderImpl.INSTANCE.addTransmission(it) }
        }
        return rootView
    }

    override fun onDestroyView() {
        GameProviderImpl.INSTANCE.removeClient(this)
        super.onDestroyView()
    }

    override fun onChanged(state: Game) {
        state.transmission?.let {
            selectedTransmission?.let { transmission ->
                val result = it.find { it.name == transmission.name }
                if(result != null)
                    onItemClicked(result, true)
                else
                    onItemClicked(transmission, false)
            }
            recyclerView.post { recyclerView.adapter = TransmissionRecyclerAdapter( GameProperties.transmissionMap.values.toMutableList(),state.transmission!!.toMutableList(),   this)}
        }
    }

    private val handler = Handler()
    override fun onItemClicked(item: Transmission, engaged : Boolean) {
        handler.post {
            selectedTransmission = item
            cardInfoView.visibility = View.VISIBLE
            nameView.text = item.name
            descView.text = item.description
            addInfoView.text = "Price: "  + item.pricePoints
            if(engaged) {
                buyBtn.visibility = View.GONE
                addInfoView.visibility = View.GONE
            } else {
                buyBtn.visibility = View.VISIBLE
                addInfoView.visibility = View.VISIBLE
            }
        }

    }
}
package com.demo.app.ncov2020.gamedialogs.disease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.game.Game
import com.demo.app.ncov2020.game.GameProvider
import com.demo.app.ncov2020.game.GameProviderImpl
import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Symptom

class DiseaseSymptomFragment : Fragment(), GameProvider.Client, SymptomRecyclerAdapter.Callback {
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.disease_symptom_dialog, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(activity, 6)
        GameProviderImpl.INSTANCE.addClient(this)
        return rootView
    }

    override fun onDestroyView() {
        GameProviderImpl.INSTANCE.removeClient(this)
        super.onDestroyView()
    }

    override fun onChanged(state: Game) {
        state.symptom?.let {
            recyclerView.post { recyclerView.adapter = SymptomRecyclerAdapter(state.symptom!!.toMutableList(),   this) }
        }
    }

    override fun onItemClicked(item: Symptom) {

    }
}
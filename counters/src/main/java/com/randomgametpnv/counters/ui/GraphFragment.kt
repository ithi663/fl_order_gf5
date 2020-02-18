package com.randomgametpnv.counters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.counters.R
import com.randomgametpnv.counters.entities.TypeOfEnergy
import com.randomgametpnv.counters.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_graph.*


class GraphFragment : BaseModuleFragment() {

    val graphFragmentArgs: GraphFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_graph, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val topText = when(graphFragmentArgs.typeOfEnergy) {
            TypeOfEnergy.COLD_WATER.name -> {resources.getText(com.randomgametpnv.base.R.string.cold_water).toString()}
            TypeOfEnergy.HOT_WATER.name -> {resources.getText(com.randomgametpnv.base.R.string.hot_water).toString()}
            TypeOfEnergy.EL_POWER.name -> {resources.getText(com.randomgametpnv.base.R.string.el_power).toString()}
            else -> {resources.getText(com.randomgametpnv.base.R.string.heating).toString()}
        }

        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)


        viewModel.counterRes.observe(this.viewLifecycleOwner, Observer {

        })

        viewModel.getCounterData()


        val start = LineGraphSeries<DataPoint> (arrayOf(
            DataPoint(0.toDouble(),1.toDouble()),
            DataPoint(1.toDouble(),6.toDouble()),
            DataPoint(2.toDouble(),4.toDouble()),
            DataPoint(3.toDouble(),9.toDouble()),
            DataPoint(4.toDouble(),2.toDouble()),
            DataPoint(5.toDouble(),2.toDouble()),
            DataPoint(6.toDouble(),8.toDouble()),
            DataPoint(7.toDouble(),9.toDouble()),
            DataPoint(8.toDouble(),3.toDouble())
        ))


        val start2 = LineGraphSeries<DataPoint> (arrayOf(
            DataPoint(0.toDouble(),4.toDouble()),
            DataPoint(1.toDouble(),1.toDouble()),
            DataPoint(2.toDouble(),3.toDouble()),
            DataPoint(3.toDouble(),2.toDouble()),
            DataPoint(4.toDouble(),1.toDouble()),
            DataPoint(5.toDouble(),7.toDouble()),
            DataPoint(6.toDouble(),4.toDouble()),
            DataPoint(7.toDouble(),1.toDouble()),
            DataPoint(8.toDouble(),1.toDouble())
        ))


        start.color = resources.getColor(R.color.dark_ripple)
        start2.color = resources.getColor(R.color.yellow_text_color)

        graph.addSeries(start)
        graph.addSeries(start2)
    }
}

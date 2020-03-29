package com.randomgametpnv.counters.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.base.showErrorMessage
import com.randomgametpnv.base.util.AppDataFormat
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.R
import com.randomgametpnv.counters.entities.CounterDataUi
import com.randomgametpnv.counters.entities.TypeOfEnergy
import com.randomgametpnv.counters.ui.base.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_graph.*
import java.util.*


class GraphFragment : BaseModuleFragment() {

    private val graphFragmentArgs: GraphFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_graph, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val topText = when(graphFragmentArgs.typeOfEnergy) {
            TypeOfEnergy.coldwater -> {resources.getText(com.randomgametpnv.base.R.string.cold_water).toString()}
            TypeOfEnergy.hotwater -> {resources.getText(com.randomgametpnv.base.R.string.hot_water).toString()}
            TypeOfEnergy.electrical -> {resources.getText(com.randomgametpnv.base.R.string.el_power).toString()}
            else -> {resources.getText(com.randomgametpnv.base.R.string.heating).toString()}
        }

        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        viewModel.getCounterData(graphFragmentArgs.typeOfEnergy).observe(this.viewLifecycleOwner, Observer {

            when(it) {
                is ApiCall.Success -> {
                    progressBar.setInvisible()
                    graph.setVisible()
                    fillGraph(it.data)
                }
                is ApiCall.ConnectException -> {
                    progressBar.setInvisible()
                    graph.setVisible()
                    showErrorMessage(it)
                }
                is ApiCall.ResponseError -> {
                    graph.setVisible()
                    progressBar.setInvisible()
                    showErrorMessage(it)
                }
                is ApiCall.Loading -> {
                    graph.setInvisible()
                    progressBar.setVisible()
                }
            }
        })

/*        val start2 = LineGraphSeries<DataPoint> (arrayOf(
            DataPoint(0.toDouble(),4.toDouble()),
            DataPoint(1.toDouble(),1.toDouble()),
            DataPoint(2.toDouble(),3.toDouble()),
            DataPoint(3.toDouble(),2.toDouble()),
            DataPoint(4.toDouble(),1.toDouble()),
            DataPoint(5.toDouble(),7.toDouble()),
            DataPoint(6.toDouble(),4.toDouble()),
            DataPoint(7.toDouble(),1.toDouble()),
            DataPoint(8.toDouble(),1.toDouble())
        ))*/

        //start2.color = resources.getColor(R.color.yellow_text_color)

    }


    private fun fillGraph(counterDataUi: CounterDataUi) {

        val grid = graph.gridLabelRenderer
        //grid.setHorizontalLabelsAngle(90)
        //grid.horizontalAxisTitle = "дата"
        grid.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {

                if (isValueX) {
                    return AppDataFormat.fromDateTo_dd_MM(value.toLong())
                }
                return super.formatLabel(value, isValueX)
            }
        }


        var index = 0

        val array = counterDataUi.date.map {
            index +=1
            DataPoint(it.date, it.value.toDouble())
        }.toTypedArray()

        val graphLine = LineGraphSeries(array)
        graphLine.color = resources.getColor(R.color.dark_ripple)
        graph.addSeries(graphLine)
    }
}

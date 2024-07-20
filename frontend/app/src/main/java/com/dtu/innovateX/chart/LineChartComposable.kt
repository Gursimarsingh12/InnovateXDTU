package com.dtu.innovateX.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrevLineChartComposable() {
    LineChartComposable(
        expectedData = mapOf(30 to 50, 20 to 70, 60 to 40),
        actualData = mapOf(40 to 45, 10 to 60, 60 to 55)
    )
}

@Composable
fun LineChartComposable(
    expectedData: Map<Int, Int>,
    actualData: Map<Int, Int>
) {
    val expectedEntries = expectedData.map { Entry(it.key.toFloat(), it.value.toFloat()) }
    val actualEntries = actualData.map { Entry(it.key.toFloat(), it.value.toFloat()) }

    val lineDataSet1 = LineDataSet(expectedEntries, "Expected").apply {
        color = android.graphics.Color.BLUE
        lineWidth = 2f
        setDrawCircles(true)
        setDrawValues(true)
    }

    val lineDataSet2 = LineDataSet(actualEntries, "Actual").apply {
        color = android.graphics.Color.RED
        lineWidth = 2f
        setDrawCircles(true)
        setDrawValues(true)
    }

    val lineData = LineData(lineDataSet1, lineDataSet2)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            LineChart(context).apply {
                data = lineData
                description.isEnabled = false
                legend.isEnabled = true
                legend.form = Legend.LegendForm.LINE

                axisRight.isEnabled = false

                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.valueFormatter = object : com.github.mikephil.charting.formatter.ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return value.toInt().toString()
                    }
                }

                axisLeft.setDrawGridLines(false)
            }
        },
        update = { chart ->
            chart.data = lineData
            chart.invalidate()
        }
    )
}
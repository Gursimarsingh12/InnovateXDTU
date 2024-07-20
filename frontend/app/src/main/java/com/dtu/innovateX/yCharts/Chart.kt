package com.dtu.innovateX.yCharts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.dtu.innovateX.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewChart() {
    val list1 = listOf(Point(0f, 40f), Point(1f, 90f), Point(2f, 0f), Point(3f, 60f), Point(4f, 10f))
    Chart(list1, backgroundColor = Color.White)
}

@Composable
fun Chart(pointsData: List<Point>, backgroundColor: Color) {
    val steps = 5
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(backgroundColor)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(colorResource(id = R.color.black))
        .axisLabelColor(colorResource(id = R.color.black))
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(backgroundColor)
        .labelAndAxisLinePadding(5.dp)
        .labelData { i ->
            val yScale = 100 / steps
            (i * yScale).toString()
        }
        .axisLineColor(colorResource(id = R.color.black))
        .axisLabelColor(colorResource(id = R.color.black))
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = backgroundColor
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(backgroundColor),
        lineChartData = lineChartData
    )
}
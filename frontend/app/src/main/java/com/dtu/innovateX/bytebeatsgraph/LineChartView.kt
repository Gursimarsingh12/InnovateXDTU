package com.dtu.innovateX.bytebeatsgraph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrevLineChartView() {
    LineChartView()
}

@Composable
fun LineChartView() {
    LineChart(
        lineChartData = LineChartData(
            points = listOf(
                LineChartData.Point(10F, "Line 1"),
                LineChartData.Point(30F, "Line 2"),
                LineChartData.Point(70F, "Line 3"),
                LineChartData.Point(20F, "Line 4"),
                LineChartData.Point(10F, "Line 5"),
                LineChartData.Point(5F, "Line 6"),
                LineChartData.Point(90F, "Line 7")
            )
        ),
        // Optional properties.
        modifier = Modifier.fillMaxSize(),
        animation = simpleChartAnimation(),
        pointDrawer = FilledCircularPointDrawer(),
        lineDrawer = SolidLineDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        horizontalOffset = 5f
    )
}
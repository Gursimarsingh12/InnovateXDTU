//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import co.yml.charts.axis.AxisData
//import co.yml.charts.common.model.Point
//import co.yml.charts.ui.combinedchart.CombinedChart
//import co.yml.charts.ui.combinedchart.model.CombinedChartData
//import co.yml.charts.ui.linechart.model.Line
//import co.yml.charts.ui.linechart.model.LinePlotData
//import co.yml.charts.ui.linechart.model.LineStyle
//
//@Composable
//fun MyCombinedChart() {
//    val list1 = listOf(
//        Point(x = 0f, y = 2f), Point(x = 1f, y = 3f), Point(x = 2f, y = 5f)
//    )
//
//    val list2 = listOf(
//        Point(x = 0f, y = 1f), Point(x = 1f, y = 4f), Point(x = 2f, y = 6f)
//    )
//
//    val linePlotData1 = LinePlotData(
//        lines = listOf(
//            Line(
//                dataPoints = list1,
//                lineStyle = LineStyle(color = Color.Red)
//            )
//        )
//    )
//
//    val linePlotData2 = LinePlotData(
//        lines = listOf(
//            Line(
//                dataPoints = list2,
//                lineStyle = LineStyle(color = Color.Blue)
//            )
//        )
//    )
//
//
//    val combinedChartData = CombinedChartData(
//        combinedPlotDataList = listOf(linePlotData1, linePlotData2),
//        xAxisData = AxisData.Builder().build(),
//        yAxisData = AxisData.Builder().build()
//    )
//
//    CombinedChart(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        combinedChartData = combinedChartData
//    )
//}

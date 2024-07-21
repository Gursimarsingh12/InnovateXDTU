import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.combinedchart.CombinedChart
import co.yml.charts.ui.combinedchart.model.CombinedChartData
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp

@Preview
@Composable
fun DualLinePlot(modifier: Modifier = Modifier) {
  // Sample data for demonstration
  val list1 = List(10) { it * 10 }  // 10 data points ranging from 0 to 90
  val list2 = List(10) { it * 15 }  // 10 data points ranging from 0 to 135

  val linePlotData = LinePlotData(
    lines = listOf(
      Line(
        DataUtils.getLineChartData(list1.size, maxRange = 100),
        lineStyle = LineStyle(color = Color.Blue),
        intersectionPoint = IntersectionPoint(),
        selectionHighlightPoint = SelectionHighlightPoint(),
        selectionHighlightPopUp = SelectionHighlightPopUp()
      ),
      Line(
        DataUtils.getLineChartData(list2.size, maxRange = 100),
        lineStyle = LineStyle(color = Color.Black),
        intersectionPoint = IntersectionPoint(),
        selectionHighlightPoint = SelectionHighlightPoint(),
        selectionHighlightPopUp = SelectionHighlightPopUp()
      )
    )
  )

  val xAxisData = AxisData.Builder()
    .axisStepSize(30.dp)
    .steps(maxOf(list1.size - 1, list2.size - 1))
    .bottomPadding(40.dp)
    .labelData { index -> index.toString() }
    .build()

  val yAxisData = AxisData.Builder()
    .steps(10)
    .labelAndAxisLinePadding(20.dp)
    .axisOffset(20.dp)
    .labelData { index -> (index * (100 / 10)).toString() }
    .build()

  val combinedChartData = CombinedChartData(
    combinedPlotDataList = listOf(linePlotData),
    xAxisData = xAxisData,
    yAxisData = yAxisData
  )

  CombinedChart(
    modifier = Modifier.height(400.dp),
    combinedChartData = combinedChartData
  )
}

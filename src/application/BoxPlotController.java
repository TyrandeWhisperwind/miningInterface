package application;

import javafx.fxml.FXML;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import weka.core.Instances;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoxPlotController {

    @FXML
    private ChartViewer boxPlot;


    public void plot(Instances instances){

        final CategoryAxis xAxis = new CategoryAxis("Attribute");
        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRange(true);
        yAxis.setAutoRangeIncludesZero(true);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        //renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(getDataSet(instances), xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
                "Box-and-Whisker",
                new Font("SansSerif", Font.BOLD, 14),
                plot,
                true
        );

        boxPlot.setChart(chart);

    }

    @SuppressWarnings("unchecked")
	private BoxAndWhiskerCategoryDataset getDataSet(Instances instances) {
        final DefaultBoxAndWhiskerCategoryDataset dataset
                = new DefaultBoxAndWhiskerCategoryDataset();
        for (int i = 0; i < instances.numAttributes(); i++) {
            @SuppressWarnings("rawtypes")
			final List list = new ArrayList<>();
            for (double d : instances.attributeToDoubleArray(i)) list.add(d);
            dataset.add(list, i, 1);
        }

        return dataset;
    }

}

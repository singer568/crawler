/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.ui.view.panel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * 内存监控
 * <p>
 * 说明:
 * </p>
 * <li></li>
 * 
 * @author DuanYong
 * @since 2013-5-4 上午9:37:29
 * @version 1.0
 */
public class MemoryUsagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	/** 总内存 */
	private TimeSeries total;
	/** 空闲内存 */
	private TimeSeries free;

	public MemoryUsagePanel(int i) {
		super(new BorderLayout());
		total = new TimeSeries("Total Memory");
		total.setMaximumItemAge(i);
		free = new TimeSeries("Free Memory");
		free.setMaximumItemAge(i);
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(total);
		timeseriescollection.addSeries(free);
		DateAxis dateaxis = new DateAxis("Time");
		NumberAxis numberaxis = new NumberAxis("Memory");
		dateaxis.setTickLabelFont(new Font("SansSerif", 0, 12));
		numberaxis.setTickLabelFont(new Font("SansSerif", 0, 12));
		dateaxis.setLabelFont(new Font("SansSerif", 0, 14));
		numberaxis.setLabelFont(new Font("SansSerif", 0, 14));
		XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(
				true, false);
		xylineandshaperenderer.setSeriesPaint(0, Color.red);
		xylineandshaperenderer.setSeriesPaint(1, Color.green);
		xylineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F, 0, 2));
		xylineandshaperenderer.setSeriesStroke(1, new BasicStroke(3F, 0, 2));
		XYPlot xyplot = new XYPlot(timeseriescollection, dateaxis, numberaxis,xylineandshaperenderer);
		dateaxis.setAutoRange(true);
		dateaxis.setLowerMargin(0.0D);
		dateaxis.setUpperMargin(0.0D);
		dateaxis.setTickLabelsVisible(true);
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		JFreeChart jfreechart = new JFreeChart("JVM Memory Usage", new Font(
				"SansSerif", 1, 24), xyplot, true);
		ChartUtilities.applyCurrentTheme(jfreechart);
		ChartPanel chartpanel = new ChartPanel(jfreechart, true);
		chartpanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createLineBorder(Color.black)));
		add(chartpanel);
	}

	private void addTotalObservation(double d) {
		total.add(new Millisecond(), d);
	}

	private void addFreeObservation(double d) {
		free.add(new Millisecond(), d);
	}

	class DataGenerator extends Timer implements ActionListener {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent actionevent) {
			long l = Runtime.getRuntime().freeMemory();
			long l1 = Runtime.getRuntime().totalMemory();
			addTotalObservation(l1);
			addFreeObservation(l);
		}

		DataGenerator(int i) {
			super(i, null);
			addActionListener(this);
		}
	}
	
	public void start(int i){
		(new DataGenerator(i)).start();
	}

}

/**
 * 
 */
package com.gopivotal.rabbitmq.besteffort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.gopivotal.rabbitmq.Quotation;

/**
 *
 */
public class Chart {

	private JFreeChart chart;
	
	public Chart(String source,List<Quotation> quotations) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries series = new TimeSeries("Quotation");
		dataset.addSeries(series);
		Calendar cal = Calendar.getInstance();
		for(Quotation quotation : quotations) {
			series.addOrUpdate(new Second(quotation.getDate()), quotation.getValue());
			cal.add(Calendar.SECOND, 1);
		}
		
		this.chart = ChartFactory.createTimeSeriesChart(
				"Quotations ("+source+")",
				"Time",
				"Value",
				dataset,
				true,
				true,
				false
		);
	}
	
	public void write(OutputStream out) throws IOException {
		ChartUtilities.writeChartAsPNG(out, chart, 500, 500);
	}
	
}

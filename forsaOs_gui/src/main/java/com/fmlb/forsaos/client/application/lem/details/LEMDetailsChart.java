package com.fmlb.forsaos.client.application.lem.details;

import com.google.gwt.core.client.JavaScriptObject;

public class LEMDetailsChart
{

	public static native JavaScriptObject drawLEMDetailsChart( String id, String percentageValue, String dataValue )
	/*-{
		var chart = $wnd.AmCharts.makeChart(id, {
			"theme" : "light",
			"responsive" : {
				"enabled" : true
			},
			"type" : "gauge",
			"axes" : [ {
				"topTextFontSize" : 20,
				"topTextYOffset" : 70,
				"axisColor" : "#31d6ea",
				"axisThickness" : 1,
				"endValue" : 100,
				"gridInside" : true,
				"inside" : true,
				"radius" : "60%",
				"valueInterval" : 10,
				"tickColor" : "#67b7dc",
				"startAngle" : -90,
				"endAngle" : 90,
				"unit" : "%",
				"bandOutlineAlpha" : 0,
				"topText" : dataValue,
				"bands" : [ {
					"color" : "#0080ff",
					"endValue" : 100,
					"innerRadius" : "105%",
					"radius" : "170%",
					"gradientRatio" : [ 0.5, 0, -0.5 ],
					"startValue" : 0
				}, {
					"color" : "#3cd3a3",
					"endValue" : percentageValue,
					"innerRadius" : "105%",
					"radius" : "170%",
					"gradientRatio" : [ 0.5, 0, -0.5 ],
					"startValue" : 0
				} ]
			} ],
			"arrows" : [ {
				"alpha" : 1,
				"innerRadius" : "35%",
				"nailRadius" : 0,
				"radius" : "170%",
				"value" : percentageValue

			} ]
		});
		return chart;
	}-*/;

	public static native void setData( JavaScriptObject chart, String percentageValue, String dataValue, String unusedPercentage )
	/*-{
		chart.arrows[0].setValue(percentageValue);
		chart.axes[0].setTopText(dataValue);
		// adjust darker band to new value
		chart.axes[0].bands[1].setEndValue(percentageValue);
		chart.axes[0].bands[1].balloonText = "Allocated " + percentageValue
				+ "%";
		chart.axes[0].bands[0].balloonText = "Available " + unusedPercentage
				+ "%";
		chart.validateData();
	}-*/;

}

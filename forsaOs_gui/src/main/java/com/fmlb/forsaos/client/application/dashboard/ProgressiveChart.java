package com.fmlb.forsaos.client.application.dashboard;

import com.google.gwt.core.client.JavaScriptObject;

public class ProgressiveChart
{
	public static native JavaScriptObject drawProgressiveChart( String id )
	/*-{
		var chart = $wnd.AmCharts.makeChart(id, {
			"type" : "serial",
			"addClassNames" : true,
			"responsive" : {
				"enabled" : true
			},
			"rotate" : true,
			"theme" : "light",
			"autoMargins" : false,
			"marginTop" : 30,
			"marginLeft" : 80,
			"marginBottom" : 5,
			"marginRight" : 50,
			"dataProvider" : [ {
				"category" : "Evaluation",
				"excelent" : 20,
				"good" : 20,
				"average" : 20,
				"poor" : 20,
				"bad" : 20,
				"limit" : 78,
				"full" : 100,
				"bullet" : 0
			} ],
			"valueAxes" : [ {
				"maximum" : 100,
				"stackType" : "regular",
				"gridAlpha" : 0,
				"labelsEnabled" : false,
				"tickLength" : 0
			} ],
			"startDuration" : 1,
			"graphs" : [ {
				"valueField" : "full",
				"showBalloon" : false,
				"type" : "column",
				"lineAlpha" : 0,
				"fillAlphas" : 0.8,
				"fillColors" : [ "#19d228", "#f6d32b", "#fb2316" ],
				"gradientOrientation" : "horizontal",
			}, {
				"clustered" : false,
				//"labelPosition" : "right",
				//"labelText" : "[[bullet]]%",
				//"labelOffset" : 5,
				//"color":"#000000",
				//"fontSize" : 14,
				"columnWidth" : 0.3,
				"fillAlphas" : 0.65,
				"lineColor" : "#ffffff",
				"stackable" : false,
				"type" : "column",
				"valueField" : "bullet"
			}, {
				"columnWidth" : 0.3,
				"lineColor" : "#000000",
				"lineThickness" : 3,
				"noStepRisers" : true,
				"stackable" : false,
				"type" : "step",
				"valueField" : "limit"
			} ],
			"columnWidth" : 0.6,
			"categoryField" : "category",
			"categoryAxis" : {
				"gridAlpha" : 0,
				"position" : "left",
				"labelsEnabled" : false,
				"tickLength" : 0
			}
		});
		return chart;
	}-*/;

	public static native void setData( JavaScriptObject chart, String value )
	/*-{
		chart.dataProvider[0].bullet = value;
		chart.validateData();
		chart.validateNow();

	}-*/;

	public static native void setData( JavaScriptObject chart, String value, String maximum, String threshold, String hoverText )
	/*-{
		chart.dataProvider[0].bullet = value;
		chart.dataProvider[0].limit = threshold;
		chart.valueAxes[0].maximum = maximum;
		chart.graphs[1].balloonFunction = function(graphDataItem, graph) {
			return hoverText;
		}
		chart.validateData();
		chart.validateNow();

	}-*/;

}

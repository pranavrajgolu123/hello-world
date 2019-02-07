package com.fmlb.forsaos.client.application.dashboard;

import com.google.gwt.core.client.JavaScriptObject;

public class CapacityChart
{

	public static native JavaScriptObject drawCapacityChart( String id )
	/*-{
		var chart = $wnd.AmCharts.makeChart(id, {
			"type" : "gauge",
			"responsive" : {
				"enabled" : true
			},
			"theme" : "light",
			"axes" : [ {
				"axisAlpha" : 0,
				"tickAlpha" : 0,
				"labelsEnabled" : false,
				"startValue" : 0,
				"endValue" : 100,
				"startAngle" : 0,
				"endAngle" : 270,
				"bands" : [ {
					"color" : "#eee",
					"startValue" : 0,
					"endValue" : 100,
					"radius" : "100%",
					"innerRadius" : "85%"
				}, {
					"color" : "#67b7dc",
					"startValue" : 0,
					"endValue" : 90,
					"radius" : "100%",
					"innerRadius" : "85%",
					"balloonText" : "Available" + "<br>" + "90%"
				}, {
					"color" : "#eee",
					"startValue" : 0,
					"endValue" : 100,
					"radius" : "80%",
					"innerRadius" : "65%"
				}, {
					"color" : "#2f4074",
					"startValue" : 0,
					"endValue" : 35,
					"radius" : "80%",
					"innerRadius" : "65%",
					"balloonText" : "Allocated" + "<br>" + "35%"
				}
	//				, {
	//					"color" : "#eee",
	//					"startValue" : 0,
	//					"endValue" : 100,
	//					"radius" : "60%",
	//					"innerRadius" : "45%"
	//				}, {
	//					"color" : "#cc4748",
	//					"startValue" : 0,
	//					"endValue" : 92,
	//					"radius" : "60%",
	//					"innerRadius" : "45%",
	//					"balloonText" : "Physical" + "<br>" + "92%"
	//				}, {
	//					"color" : "#eee",
	//					"startValue" : 0,
	//					"endValue" : 100,
	//					"radius" : "40%",
	//					"innerRadius" : "25%"
	//				}, {
	//					"color" : "#67b7dc",
	//					"startValue" : 0,
	//					"endValue" : 68,
	//					"radius" : "40%",
	//					"innerRadius" : "25%",
	//					"balloonText" : "Usable" + "<br>" + "68%"
	//				}
				 ]
			} ],
			"allLabels" : [ {
				"x" : "49%",
				"y" : "5%",
				"size" : 12,
				"bold" : true,
				"color" : "#1ab7ea",
				"align" : "right"
			}, {
				"x" : "49%",
				"y" : "15%",
				"size" : 12,
				"bold" : true,
				"color" : "#0d6c8c",
				"align" : "right"
			}
	//			, {
	//				"x" : "49%",
	//				"y" : "24%",
	//				"size" : 15,
	//				"bold" : true,
	//				"color" : "#cc4748",
	//				"align" : "right"
	//			}, {
	//				"x" : "49%",
	//				"y" : "33%",
	//				"size" : 15,
	//				"bold" : true,
	//				"color" : "#67b7dc",
	//				"align" : "right"
	//			}
			, {
				"text" : "Amplification:",
				"x" : "52%",
				"y" : "35%",
				"size" : 12,
				"bold" : true,
				"color" : "#000",
				"align" : "right"
			}, {
				"x" : "52%",
				"y" : "42%",
				"size" : 13,
				"bold" : true,
				"color" : "#007cca",
				"align" : "right"
			} ],
			"export" : {
				"enabled" : true
			}
		});
		return chart;
	}-*/;

	public static native void setData( JavaScriptObject chart, String availablePercent, String availableValue, String allocatedPercent, String allocatedValue, String amplificationValue )
	/*-{
		console.log("data while setting");
		console.log("availablePercent "+availablePercent);
		console.log("availableValue "+availableValue);
		console.log("allocatedPercent "+allocatedPercent);
		console.log("allocatedValue "+allocatedValue);
		console.log("amplificationValue "+amplificationValue);
		chart.axes[0].bands[1].endValue=parseFloat(availablePercent);
		chart.axes[0].bands[1].balloonText="Available" + "<br>" +availableValue;
		
		chart.axes[0].bands[3].endValue=parseFloat(allocatedPercent);
		chart.axes[0].bands[3].balloonText="Allocated" + "<br>" +allocatedValue;
		
		
		chart.allLabels[0].text="Available "+availableValue;
		chart.allLabels[1].text="Allocated "+allocatedValue;
	
		chart.allLabels[3].text=amplificationValue;
		chart.validateData();
		chart.validateNow();
		
	}-*/;

}

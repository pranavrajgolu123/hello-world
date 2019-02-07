package com.fmlb.forsaos.client.application.vm.details;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class VMReadWriteIOChart
{

	public static native JavaScriptObject drawVMReadWriteIOChart( String id )
	/*-{
		var chart = $wnd.AmCharts.makeChart(id, {
			"type" : "stock",
			"theme" : "light",
			"glueToTheEnd" : true,
			"categoryAxesSettings" : {
				"startOnAxis" : true,
				"axisColor" : "#DADADA",
				"gridAlpha" : 0.07,
				"parseDates" : true,
				"minPeriod" : "fff",
				"dateFormats" : [ {
					period : 'fff',
					format : 'JJ: NN: SS'
				}, {
					period : 'ss',
					format : 'JJ: NN: SS'
				}, {
					period : 'mm',
					format : 'JJ: NN'
				}, {
					period : 'hh',
					format : 'JJ: NN'
				}, {
					period : 'DD',
					format : 'MM/DD/YYYY'
				},
				//you may need to change the entries for 'WW' and 'MM' as well, depending on the amount of visibledata
				{
					period : 'WW',
					format : 'MMMDD'
				}, {
					period : 'MM',
					format : 'MMM'
				}, {
					period : 'YYYY',
					format : 'YYYY'
				} ]
			},
			"valueAxesSettings" : {
				"axisAlpha" : 0.2,
				"dashLength" : 1,
				"position" : "left",
				"usePrefixes" : false
			},
			"dataSets" : [ {
				"fieldMappings" : [ {
					"fromField" : "rd_iops",
					"toField" : "rd_iops"
				},
				{
					"fromField" : "wr_iops",
					"toField" : "wr_iops"
				}
				],
				"dataProvider" : [],
				"categoryField" : "timestamp"
			} ],
			"panels" : [ {
				"valueAxes" : [ {
					"labelFunction" : function(value, valueText, valueAxis) {
						return value;
					},
				} ],
				"stockGraphs" : [ {
					"id" : "g1",
					"title" : "Read IO",
					"lineColor" : "#0084ff",
					"useDataSetColors" : false,
					"fillAlphas" : 0.7,
					//Here the value field needs to be changed based on correct data received
					"valueField" : "rd_iops",
					"balloonFunction" : function(item) {
						return "Read IO: <b>" + item.values.value + "</b>";
					}
				},
				{
					"id" : "g2",
					"title" : "Write IO",
					"lineColor" : "#fb954f",
					"useDataSetColors" : false,
					"fillAlphas" : 0.7,
					//Here the value field needs to be changed based on correct data received
					"valueField" : "wr_iops",
					"balloonFunction" : function(item) {
						return "Write IO: <b>" + item.values.value + "</b>";
					}
				}
				 ],
				"stockLegend" : {
					"position" : "bottom",
					"valueFunction" : function(graphDataItem, valueText) {
						return valueText;
					}
				}
			} ],
			"chartScrollbarSettings" : {
				"graph" : "g1"
			},
			"chartCursorSettings" : {
				"valueBalloonsEnabled" : true,
				"graphBulletSize" : 1,
				//"valueLineBalloonEnabled": true,
				//"valueLineEnabled": true,
				"valueLineAlpha" : 0.5
			},
			"panelsSettings" : {
			//"usePrefixes": true
			},
			"export" : {
				"enabled" : true
			}
		});
		return chart;
	}-*/;

	public static native void setData( JavaScriptObject chart, JsArray<JavaScriptObject> data )
	/*-{
		chart.dataSets[0].dataProvider = data;
		chart.validateData();
		chart.validateNow();
	}-*/;

	public static native void setIncrementalData( JavaScriptObject chart, JsArray<JavaScriptObject> data )
	/*-{
		chart.dataSets[0].dataProvider.push(data);
		chart.validateData();
	}-*/;

}

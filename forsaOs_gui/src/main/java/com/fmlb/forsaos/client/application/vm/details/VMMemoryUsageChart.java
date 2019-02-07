package com.fmlb.forsaos.client.application.vm.details;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class VMMemoryUsageChart
{

	public static native JavaScriptObject drawVMMemoryUsageChart( String id )
	/*-{
		var chart = $wnd.AmCharts.makeChart(id, {
			"type" : "stock",
			"theme" : "light",
			 "glueToTheEnd": true,
			"categoryAxesSettings" : {
				"startOnAxis" : true,
				"axisColor" : "#DADADA",
				"gridAlpha" : 0.07,
				"parseDates" : true,
				"minPeriod" : "fff",
				"dateFormats" : [ {
					period : 'fff',
					format : 'JJ:NN:SS'
				}, {
					period : 'ss',
					format : 'JJ:NN:SS'
				}, {
					period : 'mm',
					format : 'JJ:NN'
				}, {
					period : 'hh',
					format : 'JJ:NN'
				}, {
					period : 'DD',
					format : 'MM/DD/YYYY'
				}, //you may need to change the entries for 'WW' and 'MM' as well, depending on the amount of visible data
				{
					period : 'WW',
					format : 'MMM DD'
				}, {
					period : 'MM',
					format : 'MMM'
				}, {
					period : 'YYYY',
					format : 'YYYY'
				} ]
			},
	"valueAxesSettings": {
	"axisAlpha": 0.2,
	"dashLength": 1,
	"position": "left",
	 "usePrefixes": false
	},
			"dataSets" : [ {
				"fieldMappings" : [ {
					"fromField" : "mem_rss_kb",
					"toField" : "mem_rss_kb"
				}
				 ],
				"dataProvider" : [],
				"categoryField" : "timestamp"
			} ],
			"panels" : [ {
	    "valueAxes": [ {
	    // we created at at the top of this page
	    "labelFunction": function( value, valueText, valueAxis ) {
	      return formatFileSize(value);
	    },
	  } ],
				"stockGraphs" : [ {
					"id" : "g1",
					"title": "Used",
					"lineColor" : "#0084ff",
	      		    "useDataSetColors": false,
					"fillAlphas" : 0.7,
					// Here the value field needs to be changed based on correct data received
					"valueField" : "mem_rss_kb",
					 "balloonFunction": function(item) {
	                                             return "Used: <b>" + formatFileSize(item.values.value) + "</b>";
	                                              }
				}
				],
				"stockLegend" : {
					 "position": "bottom",
					    "valueFunction": function(graphDataItem, valueText) {
	      if (valueText !== " ") {
	        //console.log(graphDataItem);
	        return formatFileSize(
	          graphDataItem.dataContext.dataContext[
	            graphDataItem.graph.valueField
	          ]
	        );
	      } else {
	        return valueText;
	      }
	    }
				}
			} ],
	
			"chartScrollbarSettings" : {
				"graph" : "g1"
			},
	
			"chartCursorSettings" : {
				"valueBalloonsEnabled" : true,
				"graphBulletSize" : 1,
				//				"valueLineBalloonEnabled" : true,
				//				"valueLineEnabled" : true,
				"valueLineAlpha" : 0.5
			},
			"panelsSettings" : {
				//"usePrefixes" : true
			},
			"export" : {
				"enabled" : true
			}
		});
	
	    function formatFileSize(value) {
	       var divisor = 1;
		   var scaleLabels = [ "B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" ];
		   var x = 0;
	       while ((value / divisor) >= 1024) {
			 divisor = divisor * 1024;
			 x++;
		   }
	       return Math.round(value / divisor * 100) / 100 + " " + scaleLabels[x];
	      }
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

package com.fmlb.forsaos.client.application.dashboard;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class RTMPerformanceChart
{

	public static native JavaScriptObject drawRTMPerformanceChart( String id )
	/*-{
	var chart = $wnd.AmCharts.makeChart(id,{
	"type": "stock",
	"theme": "light",
	"glueToTheEnd": true,
	"categoryAxesSettings": {
	    "startOnAxis": true,
	    "axisColor": "#DADADA",
	    "gridAlpha": 0.07,
	    "parseDates": true,
	    "minPeriod": "fff",
	    "dateFormats": [{
	            period: 'fff',
	            format: 'JJ:NN:SS'
	        }, {
	            period: 'ss',
	            format: 'JJ:NN:SS'
	        }, {
	            period: 'mm',
	            format: 'JJ:NN'
	        }, {
	            period: 'hh',
	            format: 'JJ:NN'
	        }, {
	            period: 'DD',
	            format: 'MM/DD/YYYY'
	        }, //you may need to change the entries for 'WW' and 'MM' as well, depending on the amount of visible data
	        {
	            period: 'WW',
	            format: 'MMM DD'
	        }, {
	            period: 'MM',
	            format: 'MMM'
	        }, {
	            period: 'YYYY',
	            format: 'YYYY'
	        }
	    ]
	},
	"valueAxesSettings": {
	    "axisAlpha": 0.2,
	    "dashLength": 1,
	    "usePrefixes": false
	},
	"dataSets": [{
	    "fieldMappings": [{
	        "fromField": "read",
	        "toField": "read"
	    }, {
	        "fromField": "write",
	        "toField": "write"
	    },
	    {
	        "fromField": "readThrough",
	        "toField": "readThrough"
	    }, {
	        "fromField": "writeThrough",
	        "toField": "writeThrough"
	    }
	    ],
	    "dataProvider": [],
	    "categoryField": "timepoint"
	}],
	"panels": [{
	    "valueAxes": [
//	    {
//	        "id":"readWrite",
//	        "position": "left"
//	        // we created at at the top of this page
//	        "labelFunction": function(value, valueText, valueAxis) {
//	            return formatFileSize(value);
//	        }
//	    },
	    
	    {
	    	"id":"readWriteThrough",
	    	// "position": "right",
	    	"position": "left",
	        // we created at at the top of this page
	        "labelFunction": function(value, valueText, valueAxis) {
	            return formatFileSize(value)+"ps";
	        }
	    }
	    ],
	    "stockGraphs": [
//	    {
//	            "id": "g1",
//	            "title": "Total Read",
//	            "lineColor" : "#0b823e",
//	      		"useDataSetColors": false,
	           // "fillAlphas": 0.7,
//	            "valueField": "read",
//	            "valueAxis": "readWrite",
//	             "balloonText": "Total Read: <b>[[value]]</b>"
//	            "balloonFunction": function(item) {
//	                return "Total Read: <b>" + formatFileSize(item.values.value) + "</b>";
//	            }
//	        }, {
//	            "id": "g2",
//	            "title": "Total Write",
//	            "lineColor" : "#f5c701",
//	      		"useDataSetColors": false,
//	      		"valueAxis": "readWrite",
//	            "valueField": "write",
//	            "balloonText": "Total Write: <b>[[value]]</b>"
//	            "balloonFunction": function(item) {
//	                //console.log(item);
//	                return "Total Write: <b>" + formatFileSize(item.values.value) + "</b>";
//	            }
//	        },
	        {
	            "id": "g3",
	            "title": "Read Throughput",
	            "lineColor" : "#FF8033",
	      		"useDataSetColors": false,
	           // "fillAlphas": 0.7,
	           "valueAxis": "readWriteThrough",
	            "valueField": "readThrough",
	            "balloonFunction": function(item) {
	                return "Read Throughput: <b>" + formatFileSize(item.values.value) + "ps</b>";
	            }
	        }, {
	            "id": "g4",
	            "title": "Write Throughput",
	            "lineColor" : "#9933FF",
	      		"useDataSetColors": false,
	      		 "valueAxis": "readWriteThrough",
	            "valueField": "writeThrough",
	            "balloonFunction": function(item) {
	                //console.log(item);
	                return "Write Throughput: <b>" + formatFileSize(item.values.value) + "ps</b>";
	            }
	        }
	
	    ],
	    "stockLegend": {
	        "position": "bottom",
	        "valueWidth":60,
	        "valueFunction": function(graphDataItem, valueText) {
	            if (valueText !== " ") {
	            	if(graphDataItem.graph.id=="g3" || graphDataItem.graph.id=="g4" )
	            	{
	                return formatFileSize(
	                    graphDataItem.dataContext.dataContext[
	                        graphDataItem.graph.valueField
	                    ]
	                )+"ps";
	            	}
	            	else
	            	{
//	                return formatFileSize(
//	                    graphDataItem.dataContext.dataContext[
//	                        graphDataItem.graph.valueField
//	                    ]
//	                );
	                return valueText;
	            	}
	            } else {
	                return valueText;
	            }
	        }
	    }
	}],
	
	"chartScrollbarSettings": {
	    //"graph": "g1"
	    "graph": "g3"
	},
	
	"chartCursorSettings": {
	    "valueBalloonsEnabled": true,
	    "graphBulletSize": 1,
	    //				"valueLineBalloonEnabled" : true,
	    //				"valueLineEnabled" : true,
	    "valueLineAlpha": 0.5
	},
	"panelsSettings": {
	    //"usePrefixes" : true
	},
	"periodSelector": {
    "position": "top",
    "inputFieldsEnabled": false,
    "periods": [ {
      "period": "hh",
      "selected": true,
      "count": 1,
      "label": "Last Hour"
    }, {
      "period": "DD",
      "count": 1,
      "label": "1 day"
    }, {
      "period": "MM",
      "label": "5 days"
    }, {
      "period": "MAX",
      "label": "MAX"
    } ]
  },
	"export": {
	    "enabled": true
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
		chart.periodSelector.setDefaultPeriod();
		chart.validateNow();
	}-*/;
	
	public static native void setIncrementalData( JavaScriptObject chart, JsArray<JavaScriptObject> data )
	/*-{
		chart.dataSets[0].dataProvider.push(data);
		chart.validateData();
	}-*/;

}

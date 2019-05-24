package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.sample.stockwatcher.shared.FieldVerifier;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StockWatcher implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private StockPriceServiceAsync stockPriceSvc = GWT.create(StockPriceService.class);
	  private VerticalPanel mainPanel = new VerticalPanel();
	  private FlexTable stocksFlexTable = new FlexTable();
	  private HorizontalPanel addPanel = new HorizontalPanel();
	  private TextBox newSymbolTextBox = new TextBox();
	  private Button addStockButton = new Button("Add");
	  private Label lastUpdatedLabel = new Label();
	  private ArrayList<String> stocks = new ArrayList<String>();
	  private static final int REFRESH_INTERVAL = 5000;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
			stocksFlexTable.setText(0, 0, "Symbol");
		    stocksFlexTable.setText(0, 1, "Price");
		    stocksFlexTable.setText(0, 2, "Change");
		    stocksFlexTable.setText(0, 3, "Remove");
		    
		    addPanel.add(newSymbolTextBox);
		    addPanel.add(addStockButton);

		    // Assemble Main panel.
		    mainPanel.add(stocksFlexTable);
		    mainPanel.add(addPanel);
		    mainPanel.add(lastUpdatedLabel);

		    // Associate the Main panel with the HTML host page.
		    RootPanel.get("stockList").add(mainPanel);
		    
		    newSymbolTextBox.setFocus(true);
		    
		    Timer refreshTimer = new Timer() {
		        @Override
		        public void run() {
		          refreshWatchList();
		        }

				
		      };
		      refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
		    
		    addStockButton.addClickHandler(new ClickHandler() {
		        public void onClick(ClickEvent event) {
		          addStock();
		        }
		      });

		      // Listen for keyboard events in the input box.
		      newSymbolTextBox.addKeyDownHandler(new KeyDownHandler() {
		        public void onKeyDown(KeyDownEvent event) {
		          if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		            addStock();
		          }
		        }
		      });	
		      DateTimeFormat dateFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
			  lastUpdatedLabel.setText("Last update : "+ dateFormat.format(new Date()));

	}
	
	  private void addStock() {
	      final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
	      newSymbolTextBox.setFocus(true);
    
	      // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
	      if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
	        Window.alert("'" + symbol + "' is not a valid symbol.");
	        newSymbolTextBox.selectAll();
	        return;
	      }

	      newSymbolTextBox.setText("");

	      // TODO Don't add the stock if it's already in the table.
	      if (stocks.contains(symbol))
	          return;
	      // TODO Add the stock to the table
	      int row = stocksFlexTable.getRowCount();
	      stocks.add(symbol);
	      stocksFlexTable.setText(row, 0, symbol);
	      // TODO Add a button to remove this stock from the table.
	      Button removeStockButton = new Button("x");
	      removeStockButton.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          int removedIndex = stocks.indexOf(symbol);
	          stocks.remove(removedIndex);
	          stocksFlexTable.removeRow(removedIndex + 1);
	        }
	      });
	      stocksFlexTable.setWidget(row, 3, removeStockButton);
	      
	      // TODO Get the stock price.
	      refreshWatchList();


	    }
	  
	  private void refreshWatchList() {
			// TODO Auto-generated method stub
		  if (stockPriceSvc == null) {
			    stockPriceSvc = GWT.create(StockPriceService.class);
			  }

			  // Set up the callback object.
			  AsyncCallback<StockPrice[]> callback = new AsyncCallback<StockPrice[]>() {
			    public void onFailure(Throwable caught) {
			      // TODO: Do something with errors.
			    }

			    public void onSuccess(StockPrice[] result) {
			      updateTable(result);
			    }
			  };

			  // Make the call to the stock price service.
			  stockPriceSvc.getPrices(stocks.toArray(new String[0]), callback);
	  }
	  
	  private void updateTable(StockPrice[] prices) {
	      for (int i = 0; i < prices.length; i++) {
	        updateTable(prices[i]);
	      }
	    }
	  
	  private void updateTable(StockPrice price) {
		     // Make sure the stock is still in the stock table.
		     if (!stocks.contains(price.getSymbol())) {
		       return;
		     }
		     int row = stocks.indexOf(price.getSymbol()) + 1;
		        String priceText = NumberFormat.getFormat("#,##0.00").format(
		             price.getPrice());
		         NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
		         String changeText = changeFormat.format(price.getChange());
		         String changePercentText = changeFormat.format(price.getChange());

		         // Populate the Price and Change fields with new data.

		         stocksFlexTable.setText(row, 1, priceText);
		         stocksFlexTable.setText(row, 2, changeText + " (" + changePercentText
		             + "%)");
	  }
	  
	  
	  
}

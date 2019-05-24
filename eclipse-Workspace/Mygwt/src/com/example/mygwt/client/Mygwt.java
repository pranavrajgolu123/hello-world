package com.example.mygwt.client;

import java.io.Console;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mygwt implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	
	private String value;
	private TabPanel tabPanel;
	public void onModuleLoad() {
	/*	 HTML html1 = 
			      new HTML("This is first GWT HTML widget using <U> tag of html.");
			      HTML html2 = 
			      new HTML("This is second GWT HTML widget using <i> tag of html.");

			      // use UIObject methods to set HTML widget properties.
			      html1.addStyleName("gwt-Green-Border");
			      html2.addStyleName("gwt-Blue-Border");

			      // add widgets to the root panel.
			      VerticalPanel panel = new VerticalPanel();
			      panel.setSpacing(10);
			      panel.add(html1);
			      panel.add(html2);
			      
			      RootPanel.get("gwtContainer").add(panel);*/
		
		
		 //create a label
	    /*  final Label labelMessage = new Label();
	      labelMessage.setWidth("300");

	      // Create a root tree item as department
	      TreeItem department = new TreeItem();
	      department.setText("Department");

	      //create other tree items as department names
	      TreeItem salesDepartment = new TreeItem();
	      salesDepartment.setText("Sales");
	      TreeItem marketingDepartment = new TreeItem();
	      marketingDepartment.setText("Marketing");
	      TreeItem manufacturingDepartment = new TreeItem();
	      manufacturingDepartment.setText("Manufactering");

	      //create other tree items as employees
	      TreeItem employee1 = new TreeItem();
	      employee1.setText("Apple");
	      TreeItem employee2 = new TreeItem();
	      employee2.setText("Mango");
	      TreeItem employee3 = new TreeItem();
	      employee3.setText("test");


	      //add employees to sales department
	      salesDepartment.addItem(employee1);
	      salesDepartment.addItem(employee2);
	      salesDepartment.addItem(employee3);

	      //create other tree items as employees
	      TreeItem employee4 = new TreeItem();
	      employee4.setText("market1");
	      TreeItem employee5 = new TreeItem();	
	      employee5.setText("market2");

	      //add employees to marketing department
	      marketingDepartment.addItem(employee4);
	      marketingDepartment.addItem(employee5);	    

	      //create other tree items as employees
	      TreeItem employee6 = new TreeItem();
	      employee6.setText("manufac1");
	      TreeItem employee7 = new TreeItem();
	      employee7.setText("manufac2");

	      //add employees to sales department
	      manufacturingDepartment.addItem(employee6);
	      manufacturingDepartment.addItem(employee7);

	      //add departments to department item
	      department.addItem(salesDepartment);
	      department.addItem(marketingDepartment);
	      department.addItem(manufacturingDepartment);

	      //create the tree
	      Tree tree = new Tree();
	      
	      //add root item to the tree
	      tree.addItem(department);	   
	     

	     tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				// TODO Auto-generated method stub
				if(event.getSelectedItem().getText().toString() == "Mango") {
					//showCustomDialog();
					 MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();  
				      oracle.add("A");
				      oracle.add("AB");
				      oracle.add("ABC");
				      oracle.add("ABCD");
				      oracle.add("B");
				      oracle.add("BC");
				      oracle.add("BCD");
				      oracle.add("BCDE");
				      oracle.add("C");
				      oracle.add("CD");
				      oracle.add("CDE");
				      oracle.add("CDEF");
				      oracle.add("D");
				      oracle.add("DE");
				      oracle.add("DEF");
				      oracle.add("DEFG");
				      
				      //create the suggestion box and pass it the data created above
				       SuggestBox suggestionBox = new SuggestBox(oracle);
				 
				      //set width to 200px.
				      suggestionBox.setWidth("200");
				      VerticalPanel panel = new VerticalPanel();
				      panel.add(suggestionBox);
					RootPanel.get().add(suggestionBox);
					
				}
				else {
				labelMessage.setText("Selected Value: "
			            + event.getSelectedItem().getText());
				       //ComboBox box=new ComboBox();
				       //RootPanel.get().add(box);
				}
			}
		});

			
			
	      // Add text boxes to the root panel.
	      VerticalPanel panel = new VerticalPanel();
	      panel.setSpacing(10);
	      panel.add(tree);
	      panel.add(labelMessage);
          
          
	      //add the tree to the root panel
	      RootPanel.get("gwtContainer").add(panel);*/
		
	    tabPanel = new TabPanel();

	    tabPanel.add(new HTML("<h1>Page 0 Content: Llamas</h1>"), " Page 0 ");
	    tabPanel.add(new HTML("<h1>Page 1 Content: Alpacas</h1>"), " Page 1 ");
	    tabPanel.add(new HTML("<h1>Page 2 Content: Camels</h1>"), " Page 2 ");

	    tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				// TODO Auto-generated method stub
				 History.newItem("page" + event.getSelectedItem());
			}
		});
	    History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO Auto-generated method stub
				String historyToken = event.getValue();
                System.out.print("history:   "+historyToken);
                
		        // Parse the history token
		        try {
		          if (historyToken.substring(0, 4).equals("page")) {
		            String tabIndexToken = historyToken.substring(4, 5);
		            int tabIndex = Integer.parseInt(tabIndexToken);
		            // Select the specified tab panel
		            tabPanel.selectTab(tabIndex);
		          } else {
		            tabPanel.selectTab(0);
		          }

		        } catch (IndexOutOfBoundsException e) {
		          tabPanel.selectTab(0);
		        }
			}
		});

	      tabPanel.selectTab(0);
	      RootPanel.get().add(tabPanel);
	   }	
	
	private DialogBox showCustomDialog() {  
		  
	       final DialogBox dialog = new DialogBox(false, true);  
	       // final DialogBox dialog = new DialogBox(true, true);  
	       // Set caption  
	       dialog.setText("DialogBox Caption");  
	       // Setcontent  
	    Label content = new Label("This is sample text message inside "  
	        + "Customized Dialogbox. In this example a Label is "  
	        + "added inside the Dialogbox, whereas any custom widget "  
	        + "can be added inside Dialogbox as per application'ser's need. ");  
	    
	    if (dialog.isAutoHideEnabled())  {  
	       dialog.setWidget(content);  
	        } else {  
	    VerticalPanel vPanel = new VerticalPanel();
	    vPanel.setSpacing(2);  
	    vPanel.add(content);
	    vPanel.add(new Label("\n"));  
	    vPanel.add(new Button("Close", new ClickHandler() {  
	    public void onClick(ClickEvent event) {  
	            dialog.hide();  
	        }  
	    }));  
	    dialog.setWidget(vPanel);  
	    }  
	    dialog.setPopupPosition(1000, 150);  
	    dialog.show();  
	    return dialog;  
	}  
	
	private SuggestBox suggestion(){
		
		 MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();  
	      oracle.add("A");
	      oracle.add("AB");
	      oracle.add("ABC");
	      oracle.add("ABCD");
	      oracle.add("B");
	      oracle.add("BC");
	      oracle.add("BCD");
	      oracle.add("BCDE");
	      oracle.add("C");
	      oracle.add("CD");
	      oracle.add("CDE");
	      oracle.add("CDEF");
	      oracle.add("D");
	      oracle.add("DE");
	      oracle.add("DEF");
	      oracle.add("DEFG");
	      
	      //create the suggestion box and pass it the data created above
	       SuggestBox suggestionBox = new SuggestBox(oracle);
	 
	      //set width to 200px.
	      suggestionBox.setWidth("200");
		return suggestionBox;
	}
	
 }
			
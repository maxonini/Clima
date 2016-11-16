package com.climattention.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Climattention implements EntryPoint {
	
	private boolean isInMapMode = false;
	
	private HorizontalPanel hPanelSlider = new HorizontalPanel();
	
	private VerticalPanel vPanel = new VerticalPanel();
	private ScrollPanel scrollPanelTable = new ScrollPanel();
	private VerticalPanel map = new VerticalPanel();
	private FlowPanel searchMenu = new FlowPanel();
	
	private HorizontalPanel name = new HorizontalPanel();
	private HorizontalPanel search = new HorizontalPanel();
	private HorizontalPanel upperPanel=new HorizontalPanel();
	
	private TextBox nameField = new TextBox();
	private Button searchButton = new Button("Search");
	private Button exportButton = new Button("Export");
	private Button updateMapButton =new Button("Update Map");
	
	private Map worldMap;
	private Table table = new Table();
	private CheckBox toggleUncertainty=new CheckBox();
	private Label toggleUncertaintylbl=new Label("Toggle Uncertainty");
	private HorizontalPanel toggleUSContainer=new HorizontalPanel();
	private HorizontalPanel mapOptionsPanel=new HorizontalPanel();
	
	private Label nameLabel = new Label("Country");
	
	private TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
	  
	private Label sourceLabel = new HTML();
	private Anchor licenseLink = new Anchor("All data is published under the following License", "http://creativecommons.org/licenses/by-sa/4.0/");
	
  /**
   * Entry point method.
   */
  public void onModuleLoad() {
	  System.out.println("Module starts loading... ");
	  
	  searchMenu.setWidth("100%");
	  upperPanel.add(searchMenu);
	  
	  name.setStyleName("flowPanel_inline");
	  search.setStyleName("flowPanel_inline");
	  searchButton.setStyleName("flowPanel_inline");
	  exportButton.setStyleName("rightTop");
	  
	  name.add(nameLabel);
	  name.add(nameField);
	  search.add(searchButton);

	  searchMenu.add(name);
	  searchMenu.add(search);
	  
	  //MAP UI ELEMENTS
	  toggleUSContainer.add(toggleUncertaintylbl);
	  toggleUSContainer.add(toggleUncertainty);
	  
	  mapOptionsPanel.add(toggleUSContainer);
	  mapOptionsPanel.addStyleName("mapOptionsPanel");
	  mapOptionsPanel.setWidth("300px");
	  
	  //scrollPanelTable.add(table);
	  
	  upperPanel.add(exportButton);
	  upperPanel.add(updateMapButton);
	  upperPanel.add(hPanelSlider);
	  upperPanel.add(mapOptionsPanel);

	  hPanelSlider.addStyleName("mapOptionsPanelContent");
	  hPanelSlider.setVisible(false);
	  updateMapButton.setVisible(false);
	  mapOptionsPanel.setVisible(false);
	
	  tabPanel.setHeight("800px");
	  tabPanel.setAnimationDuration(1000);
	  tabPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);

	  tabPanel.add(scrollPanelTable, "table");
	  tabPanel.add(new ScrollPanel(map), "map");

	  vPanel.add(searchMenu);
	  vPanel.add(upperPanel);
	  vPanel.add(tabPanel);
	  vPanel.setWidth("100%");
	  
	  
	  // Associate the Main panel with the HTML host page.
	  RootPanel.get("clima").add(vPanel);
		
	  
	  vPanel.add(sourceLabel);
	  vPanel.add(licenseLink);
	  
	  
	  tabPanel.addSelectionHandler(new SelectionHandler<Integer>(){
		  public void onSelection(SelectionEvent<Integer> event){
		   int tabId = event.getSelectedItem();
		   if(tabId==0)
		   {
			   isInMapMode=false;
			   toggleMapMode(); 
		   }
		   else
		   {
			   isInMapMode=true;
			   toggleMapMode();
		   }
		 }
		});
  }
  
  
  
  
  
  private void toggleMapMode()
	{
		if(isInMapMode)
		{
			mapOptionsPanel.setVisible(true);
			hPanelSlider.setVisible(true);
			name.setVisible(false);
			search.setVisible(false);
			updateMapButton.setVisible(true);
			/*if(yearField.getText()!=""){
				worldMap.getCurrentQuery().setYear(yearField.getText());
				worldMap.UpdateWorldMap();
			}*/
		}
		else
		{
			mapOptionsPanel.setVisible(false);
			hPanelSlider.setVisible(false);
			name.setVisible(true);
			search.setVisible(true);
			updateMapButton.setVisible(false);
		}
	}
}

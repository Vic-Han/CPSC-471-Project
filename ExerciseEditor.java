package com.example.demo;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;




@Route("")
@RouteAlias("exerciseEdit")
public class ExerciseEditor extends VerticalLayout{
    protected List<Metric> metricList;
    private Grid<Metric> metricGrid;//declared here so all functions have access
    public ExerciseEditor(){
        setupTitle();
        setupNameInput();
        setupMetricGrid();
        setupAddMetric();
        setupExitButtons();
    }
    public List<Metric> getList(){
        return metricList;
    }
    public void refreshGrid(){
        metricGrid.setItems(metricList);
    }
    private void setupTitle(){
        H1 title = new H1("Exercise Editor");
        add(title);
    }
    private void setupNameInput(){
        TextField nameField = new TextField("Name:");
        add(nameField);
    }
    private void setupMetricGrid(){
        //setup grid...
        metricGrid = new Grid<>(Metric.class, false);
        metricGrid.addColumn(Metric::getName).setHeader("Metric")
            .setAutoWidth(true).setFlexGrow(1);
        metricGrid.addColumn(Metric::getUnit).setHeader("Unit")
            .setAutoWidth(true).setFlexGrow(1);
        metricList = new ArrayList<Metric>();
        //setup testing metrics...
            metricList.add(new Metric("Pace", "min/km"));
            metricList.add(new Metric("Weight", "kg"));
            metricList.add(new Metric("Distance", "km"));
        //done setting up example
        refreshGrid();
        metricGrid.setAllRowsVisible(true);

        //add menu options...
        metricGrid.addComponentColumn(met -> {
            MenuBar menuBar = new MenuBar();
            menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
            MenuItem menuItem = menuBar.addItem("•••");
            menuItem.getElement().setAttribute("aria-label", "More options");
            SubMenu subMenu = menuItem.getSubMenu();
            subMenu.addItem("Edit", event -> {
                openExistingMetricEditor(met);
            });
            subMenu.addItem("Delete", event -> {
                metricList.remove(met);//remove from arraylist
                refreshGrid();
            });
            return menuBar;
        }).setWidth("70px").setFlexGrow(0);
        add(metricGrid);
    }
    private void openMetricEditor(){
        MetricEditor metricEdit = new MetricEditor(this);//instantiate metric editor, passing on metric list
                                                            // so metric editor can add its own metric to the list
        metricEdit.open();// open that shit

    }
    private void openExistingMetricEditor(Metric met){
        ExistingMetricEditor existMetricEdit = new ExistingMetricEditor(met, this);//instantiate metric editor, passing on metric list
                                                            // so metric editor can add its own metric to the list
        existMetricEdit.open();// open that shit

    }
    private void setupAddMetric(){
        Button addMetric = new Button("Add metric");
        addMetric.addClickListener(e ->
            openMetricEditor()
        );
        add(addMetric);
    }
    private void setupExitButtons(){
        HorizontalLayout lastRow = new HorizontalLayout();//declare layout
        Button submit = new Button("Submit");//declaring buttons...
        Button cancel = new Button("Cancel");
        Button delete = new Button("Delete");
        lastRow.add(submit,cancel,delete);//add all the buttons to layout
        add(lastRow);//add layout

    }
}

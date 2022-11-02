package com.example.demo;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@Route("")
@RouteAlias("HomeScr")
public class HomeScreen extends AppLayout{
    Tab dashboard;
    Tab profile;
    Tab food;
    Tab exercise;
    Tab performanceMetric;
    Tab graph;
    private VerticalLayout content;
    public HomeScreen() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Health Tracker");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();
        content = new VerticalLayout();
        content.add(new Dashboard());
        setContent(content);

        addToDrawer(tabs);
        addToNavbar(toggle, title);
    }

    private Tabs getTabs() {

        Tabs tabs = new Tabs();
        dashboard = new Tab("Dashboard");
        profile = new Tab("Profile");
        food = new Tab("Foods");
        exercise = new Tab("Exercises");
        performanceMetric = new Tab("Performance Metrics");
        graph = new Tab("Graph");

        tabs.add(dashboard, profile, food, exercise, performanceMetric, graph);
        tabs.addSelectedChangeListener(
                event -> setContent(event.getSelectedTab()));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private void setContent(Tab tab) {
        content.removeAll();

        if (tab.equals(dashboard)) {
            content.add(new Dashboard());
        } else if (tab.equals(profile)) {
            content.add(new Paragraph("This is the profile tab"));
        } else if (tab.equals(food)) {
            content.add(new Paragraph("This is the food tab"));
        } else if (tab.equals(exercise)) {
            content.add(new ExerciseView());
        } else if (tab.equals(performanceMetric)) {
            content.add(new MetricView());
        }else {
            content.add(new Paragraph("This is the graph tab"));
        }
    }

}
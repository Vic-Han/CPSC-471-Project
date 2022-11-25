// package com.example.demo;

// import java.time.LocalDate;
// import java.util.List;

// import com.vaadin.flow.component.accordion.Accordion;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.contextmenu.MenuItem;
// import com.vaadin.flow.component.contextmenu.SubMenu;
// import com.vaadin.flow.component.datepicker.DatePicker;
// import com.vaadin.flow.component.grid.Grid;
// import com.vaadin.flow.component.html.H2;
// import com.vaadin.flow.component.html.Paragraph;
// import com.vaadin.flow.component.menubar.MenuBar;
// import com.vaadin.flow.component.menubar.MenuBarVariant;
// import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;

// public class Dashboard extends VerticalLayout {
//     Day testDay;// in reality would come from user
//     LocalDate selected;
//     DatePicker datePick;
//     HorizontalLayout headerLayout;
//     VerticalLayout workoutLayout;
//     VerticalLayout mealLayout;
//     public Dashboard(){
//         testDay = new Day(LocalDate.now());
//         setupDateLayout();
//         setupHeader(LocalDate.now());
//         setupWorkoutLayout(LocalDate.now());
//         setupMealLayout(LocalDate.now());
//     }
//     private void setupDateLayout(){
//         HorizontalLayout dateLayout = new HorizontalLayout();
//         datePick = new DatePicker(LocalDate.now());
//         dateLayout.add(datePick);
//         Button search = new Button("Search Date");
//         search.addClickListener(clickEvent -> {
//             selected = datePick.getValue();
//             updateHeader(selected);
//             updateWorkoutLayout(selected);
//             updateMealLayout(selected);

//         });
//         dateLayout.add(search);
//         add(dateLayout);
//     }
//     private void setupHeader(LocalDate day){
//         headerLayout = new HorizontalLayout();
//         updateHeader(day);
//         add(headerLayout);
//     }
//     private void updateHeader(LocalDate day){
//         headerLayout.removeAll();
//         headerLayout.add (new H2("Viewing: "+ day.toString()));
//     }
//     private void setupWorkoutLayout(LocalDate day){
//         workoutLayout = new VerticalLayout();
//         updateWorkoutLayout(day);
//         add(workoutLayout);
//     }
//     private void updateWorkoutLayout(LocalDate day){
//         workoutLayout.removeAll();
//         if (day.compareTo(testDay.getDate()) == 0){// in reality, day class would check database and provide
//             // either null or correct Day object
//             Accordion accordion = new Accordion();
//             List<Workout> dailyWorkouts = testDay.getWorkouts();// in reality testDay is taken from database
//             for (Workout wo: dailyWorkouts){
//                 accordion.add("Workout "+wo.getNum()+":", new Paragraph("Insert workout editor for this workout"));
//             }
//             workoutLayout.add(accordion);
//             //add buttons
//             HorizontalLayout buttons = new HorizontalLayout();
//             Button addWorkout = new Button("Add Workout");
//             addWorkout.addClickListener(clickEvent -> {
//                 accordion.add("Workout ", new Paragraph("Insert  new workout editor"));
//             });
//             buttons.add(addWorkout);
//             Button saveWorkouts = new Button("Save");
//             saveWorkouts.addClickListener(clickEvent -> {
//                 //saves current arrayList of meals
//             });
//             buttons.add(saveWorkouts);
//             workoutLayout.add(buttons);
//         }
//         else{
//             workoutLayout.add(new Paragraph("No workouts on this day"));
//         }
//     }
//     private void setupMealLayout(LocalDate day){
//         mealLayout = new VerticalLayout();
//         updateMealLayout(day);
//         add(mealLayout);
//     }
//     private void updateMealLayout(LocalDate day){
//         mealLayout.removeAll();
//         if (day.compareTo(testDay.getDate()) == 0){// in reality, day class would check database and provide
//                                                 // either null or correct Day object

//             //set up grid
//             Grid<Meal> grid = new Grid<>(Meal.class, false);
//             List<Meal> dailyMeals = testDay.getMeals();// in reality testDay is taken from database
            
//             grid.addColumn(Meal::getNum).setHeader("Meal number:")
//             .setAutoWidth(true).setFlexGrow(1);
//             grid.addColumn(Meal::getProtein).setHeader("Protein")
//             .setAutoWidth(true).setFlexGrow(1);
//             grid.addColumn(Meal::getCarbs).setHeader("Carbohydrates")
//             .setAutoWidth(true).setFlexGrow(1);
//             grid.addColumn(Meal::getfat).setHeader("Fat")
//             .setAutoWidth(true).setFlexGrow(1);
//             grid.addColumn(Meal::getCalories).setHeader("Calories")
//             .setAutoWidth(true).setFlexGrow(1);
            
//             grid.setItems(dailyMeals);
//             //add context menu
//             grid.addComponentColumn(m -> {
//                 MenuBar menuBar = new MenuBar();
//                 menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
//                 MenuItem menuItem = menuBar.addItem("•••");
//                 menuItem.getElement().setAttribute("aria-label", "More options");
//                 SubMenu subMenu = menuItem.getSubMenu();
//                 subMenu.addItem("Edit", event -> {
//                     //open meal editor
//                 });
//                 subMenu.addItem("Delete", event -> {
//                     dailyMeals.remove(m);//remove from arraylist
//                     grid.setItems(dailyMeals);
//                 });
//                 return menuBar;
//             }).setWidth("70px").setFlexGrow(0);

//             mealLayout.add(grid);
//             //add buttons
//             HorizontalLayout buttons = new HorizontalLayout();
//             Button addMeal = new Button("Add Meal");
//             addMeal.addClickListener(clickEvent -> {
//                 //opens meal editor
//             });
//             buttons.add(addMeal);
//             Button saveMeals = new Button("Save");
//             saveMeals.addClickListener(clickEvent -> {
//                 //saves current arrayList of meals
//             });
//             buttons.add(saveMeals);
//             mealLayout.add(buttons);
            
//         }
//         else{
//             mealLayout.add(new Paragraph("No meals on this day"));
//         }
//     }
// }
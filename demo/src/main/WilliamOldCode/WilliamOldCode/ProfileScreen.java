// package com.example.demo;

// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.combobox.ComboBox;
// import com.vaadin.flow.component.datepicker.DatePicker;
// import com.vaadin.flow.component.html.H1;
// import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.component.textfield.TextField;
// import com.vaadin.flow.router.Route;
// import com.vaadin.flow.router.RouteAlias;

// @Route("")
// @RouteAlias("profScreen")
// public class ProfileScreen extends VerticalLayout {
//     private String[] exampleGender = {"Male", "Female", "Other"};
//     private String[] profileType = {"Trainer", "Athlete"};
//     public ProfileScreen(){
//         setupTitle();
//         setupNameFieldInput();
//         date();
//         setupGenderInput();
//         setupProfileType();
//         updateOrMake();
//     }

//     private void setupTitle(){
//         H1 title = new H1("Profile");
//         add(title);
//     }

//     private void setupNameFieldInput(){
//         TextField nameField = new TextField("First Name:");
//         add(nameField);
//         TextField nameField1 = new TextField("Last Name:");
//         add(nameField1);
//         TextField nameField2 = new TextField("Height(cm):");
//         add(nameField2);
//         TextField nameField3 = new TextField("Weight(kg):");
//         add(nameField3);
//     }

//     private void date(){
//         DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
//         singleFormatI18n.setDateFormat("yyyy-MM-dd");
//         DatePicker singleFormatDatePicker = new DatePicker("Date of Birth:");
//         singleFormatDatePicker.setI18n(singleFormatI18n);
//         add(singleFormatDatePicker);

//     }
//     private void setupGenderInput(){
//         HorizontalLayout genderLayout = new HorizontalLayout();//declare layout
//         ComboBox<String> gender = new ComboBox<>("Gender:");//declare combobox
//         gender.setItems(exampleGender);//put array of objects (Strings in this case) into combobox
//         genderLayout.add(gender);//add combobox to layout
//         add(genderLayout);//add layout to screen
//     }

//     private void setupProfileType(){
//         HorizontalLayout profileTypeLayout = new HorizontalLayout();//declare layout
//         ComboBox<String> type = new ComboBox<>("Profile Type:");//declare combobox
//         type.setItems(profileType);//put array of objects (Strings in this case) into combobox
//         profileTypeLayout.add(type);//add combobox to layout
//         add(profileTypeLayout);//add layout to screen
//     }

//     private void updateOrMake(){
//         Button updateMake = new Button("Update/Make");
//         add(updateMake);
//     }
// }

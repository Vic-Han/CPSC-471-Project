package com.example.demo;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class LoginController extends VerticalLayout{
    Login l;
    HomeScreen h;
    Register r;
    public LoginController(){
        l = new Login(this);
        add(l);
   }
   public void loginSuccess(int userID){
        remove(l);
        h = new HomeScreen(userID,this);
        add(h);
   }
   public void registerUser(){
        remove(l);
        r = new Register(this);
        add(r);
   }
   public void registerSuccess(int userID){
        remove(r);
        h = new HomeScreen(userID, this);
        add(h);
   }
   public void logout(){
        remove(h);
        l = new Login(this);
        add(l);
   }
   public void cancelRegister(){
        remove(r);
        l = new Login(this);
        add(l);
   }

}

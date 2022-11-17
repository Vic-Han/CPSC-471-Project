package com.example.demo;

public interface Editor<E> extends VerticalLayout{
    public void addObject(Object E);
    public void deleteObject(Object E);
    public void fetchData();
    public void getUserID();
    private void done(){
        
    }


}
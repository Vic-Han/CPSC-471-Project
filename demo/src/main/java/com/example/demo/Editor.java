package com.example.demo;
public interface Editor<E>{
    public void addObject(Object E);
    public void deleteObject(Object E);
    public void fetchData();
    public int getUserID();
    private void done(){
        
    }


}
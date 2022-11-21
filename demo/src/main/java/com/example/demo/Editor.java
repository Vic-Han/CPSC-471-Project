package com.example.demo;
public interface Editor<E>{
    public void addObject(E obj);
    public void deleteObject(E obj);
    public void fetchData();
    public int getUserID();
    private void done(){
        
    }


}
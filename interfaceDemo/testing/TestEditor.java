package testing;

import java.util.ArrayList;

public class TestEditor implements TestInterface<String>{
    private ArrayList<String> list;

    public TestEditor(){
        list = new ArrayList<String>();
    }
    @Override
    public void addObject(String s){
        list.add(s);
    }
    @Override
    public void removeObject(String s){
        list.remove(s);
    }

    
}

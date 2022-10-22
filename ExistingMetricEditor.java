package com.example.demo;

public class ExistingMetricEditor extends MetricEditor {
    private Metric existingMetric;
    public ExistingMetricEditor(Metric met){
        super();
        existingMetric = met;
        fillFields();
    }
    public ExistingMetricEditor(Metric met, ExerciseEditor parent ){
        super(parent);
        existingMetric = met;
        fillFields();
    }
    private void fillFields(){
        nameField.setValue(existingMetric.getName());
        unitField.setValue(existingMetric.getUnit());
    }
    @Override
    protected void submit(){
        existingMetric.setName(nameField.getValue());
        existingMetric.setUnit(unitField.getValue());
        //metric should alter database upon edit
        if (parentEditor.getList() != null){// if called from exercise editor
            //metric is already in list
            parentEditor.refreshGrid();
        }
        //otherwise, if called from main screen
        close();
    }


}

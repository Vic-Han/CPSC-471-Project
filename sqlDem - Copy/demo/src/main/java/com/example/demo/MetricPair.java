package com.example.demo;

public class MetricPair {
    private Metric metric;
    private int metricVal;
    public MetricPair(Metric metric, int metricVal){
        this.metric = metric;
        this.metricVal = metricVal;
    }
    public Metric getMetric(){
        return metric;
    }
    public String getName(){
        return metric.getName();
    }
    public String getUnit(){
        return metric.getUnit();
    }
    public int getVal(){
        return metricVal;
    }
    public void setMetric(Metric metric){
        this.metric = metric;
    }
    public void setVal(int val){
        this.metricVal = val;
    }
}

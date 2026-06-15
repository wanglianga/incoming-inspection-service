package com.factory.inspection.vo;

import java.util.List;

public class SamplingCalculationVO {

    private String batchNo;
    private Integer arrivedQuantity;
    private Integer sampleQuantity;
    private String samplingScheme;
    private List<String> reasons;
    private Double samplingRatio;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getArrivedQuantity() {
        return arrivedQuantity;
    }

    public void setArrivedQuantity(Integer arrivedQuantity) {
        this.arrivedQuantity = arrivedQuantity;
    }

    public Integer getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(Integer sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public String getSamplingScheme() {
        return samplingScheme;
    }

    public void setSamplingScheme(String samplingScheme) {
        this.samplingScheme = samplingScheme;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public Double getSamplingRatio() {
        return samplingRatio;
    }

    public void setSamplingRatio(Double samplingRatio) {
        this.samplingRatio = samplingRatio;
    }
}

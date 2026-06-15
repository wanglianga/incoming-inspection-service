package com.factory.inspection.dto;

import jakarta.validation.constraints.NotBlank;

public class SamplingDTO {
    @NotBlank(message = "检验批次号不能为空")
    private String batchNo;

    private Integer sampleQuantity;

    private String samplingScheme;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
}

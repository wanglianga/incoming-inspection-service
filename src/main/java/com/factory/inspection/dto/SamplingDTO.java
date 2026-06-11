package com.factory.inspection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SamplingDTO {
    @NotBlank(message = "检验批次号不能为空")
    private String batchNo;

    @NotNull(message = "抽样数量不能为空")
    @Positive(message = "抽样数量必须大于0")
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

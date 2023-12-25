package com.eteration.simplebanking.model.rest;

public class TransactionApplyResponseDto {
    private String status;
    private String approvalCode;

    public static TransactionApplyResponseDto of(String status, String approvalCode) {
        TransactionApplyResponseDto responseDto = new TransactionApplyResponseDto();
        responseDto.setStatus(status);
        responseDto.setApprovalCode(approvalCode);

        return responseDto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
}

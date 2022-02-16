package com.mob.casestudy.digitalbanking.customerror;

public final class ErrorList {
    private ErrorList(){}
    public static final String CUS_DELETE_NOT_FOUND = "CUS-DELETE-NFD-001";
    public static final String CUS_SEC_QUES_CREATE_FIELD_ERROR = "CSQ-CREATE-FIE-001";
    public static final String CUS_SEC_QUES_VALIDATE_ERROR = "CSQ-CREATE-FIE-002";
    public static final String CUS_SEC_QUES_NOT_FOUND = "CSQ-CREATE-FIE-003";
    public static final String CUS_SEC_QUES_CREATE_3_QUES_ERROR = "CSQ-CREATE-FIE-004";
    public static final String CUS_SEC_QUES_CUS_NOT_FOUND = "CSQ-CREATE-FIE-005";
    public static final String SEC_IMG_NOT_FOUND = "SIM-GET-FIE-001";
    public static final String CUS_SEC_IMG_NOT_FOUND = "CSI-GET-FIE-001";
    public static final String CUS_SEC_IMG_CUS_NOT_FOUND = "CSI-GET-FIE-002";
    public static final String CUS_NOT_FOUND = "OTP-VAL-FIE-001";
    public static final String OTP_IS_NULL_OR_EMPTY = "OTP-VAL-FIE-002";
    public static final String INVALID_OTP = "OTP-VAL-FIE-003";
    public static final String NO_FAILED_OTP_ATTEMPT_EXCEED = "OTP-VAL-FIE-004";
    public static final String CUS_INITIATE_OTP_EXPIRED = "OTP-VAL-FIE-005";
}

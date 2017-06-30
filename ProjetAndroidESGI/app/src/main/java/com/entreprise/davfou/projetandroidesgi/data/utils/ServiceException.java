package com.entreprise.davfou.projetandroidesgi.data.utils;

import android.annotation.TargetApi;

/**
 * Created by davidfournier on 28/02/2017.
 */


public class ServiceException extends Exception {

    private int mCode;
    private ServiceExceptionType mType;

    public ServiceException(int mCode) {
        super();
        setCode(mCode);
    }

    public ServiceException(ServiceExceptionType mType) {
        super();
        setType(mType);
    }

    public ServiceException(String message, ServiceExceptionType mType) {
        super(message);
        setType(mType);
    }

    public ServiceException(String message, Throwable cause, ServiceExceptionType mType) {
        super(message, cause);
        setType(mType);
    }

    public ServiceException(Throwable cause, ServiceExceptionType mType) {
        super(cause);
        setType(mType);
    }


    public ServiceException(String message, int mCode) {
        super(message);
        setCode(mCode);
    }

    public ServiceException(String message, Throwable cause, int mCode) {
        super(message, cause);
        setCode(mCode);
    }

    public ServiceException(Throwable cause, int mCode) {
        super(cause);
        setCode(mCode);
    }

    @TargetApi(24)
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int mCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        setCode(mCode);
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
        setType(getTypeForCode(code));
    }

    public ServiceExceptionType getType() {
        return mType;
    }

    public void setType(ServiceExceptionType type) {
        this.mType = type;
    }

    @Override
    public String getMessage() {
        return "ServiceException with code : " + mCode + " and type " + mType + " " + getOriginalMessage();
    }

    public String getOriginalMessage() {
        return super.getMessage();
    }

    public static ServiceExceptionType getTypeForCode(int code) {
        switch (code) {
            case 400:
                return ServiceExceptionType.BAD_REQUEST;
            case 401:
                return ServiceExceptionType.UNAUTHORIZED;
            case 403:
                return ServiceExceptionType.FORBIDDEN;
            case 404:
                return ServiceExceptionType.NOT_FOUND;
            case 405:
                return ServiceExceptionType.METHOD_NOT_ALLOWED;
            case 409:
                return ServiceExceptionType.CONFLICT;
            case 410:
                return ServiceExceptionType.GONE;
            case 500:
                return ServiceExceptionType.SERVER_ERROR;
            case 501:
                return ServiceExceptionType.NOT_IMPLEMENTED;
            default:
                return ServiceExceptionType.UNKNOWN;
        }
    }
}

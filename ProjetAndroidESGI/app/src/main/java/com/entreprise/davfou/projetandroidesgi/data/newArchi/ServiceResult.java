package com.entreprise.davfou.projetandroidesgi.data.newArchi;

/**
 * Created by davidfournier on 29/06/2017.
 */

public class ServiceResult<T> {
    T mData;
    ServiceException mError;

    public T getmData() {
        return mData;
    }

    public void setmData(T mData) {
        this.mData = mData;
    }

    public ServiceException getmError() {
        return mError;
    }

    public void setmError(ServiceException mError) {
        this.mError = mError;
    }
}

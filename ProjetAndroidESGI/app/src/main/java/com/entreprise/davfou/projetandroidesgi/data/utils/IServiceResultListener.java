package com.entreprise.davfou.projetandroidesgi.data.utils;

/**
 * Created by davidfournier on 28/02/2017.
 */


public interface IServiceResultListener<T> {
    void onResult(ServiceResult<T> result);
}
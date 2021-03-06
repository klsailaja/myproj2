package com.ab.telugumoviequiz.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class GetTask<T> implements Runnable {
    private final String reqUri;
    private final int requestId;
    private CallbackResponse callbackResponse;
    private Object helperObject;
    private int reqTimeOut = 5 * 1000;
    private final Class<T> classType;

    public GetTask(String reqUri, int reqId, CallbackResponse callbackResponse, Class<T> classType, Object helperObject) {
        this.reqUri = reqUri;
        this.requestId = reqId;
        this.callbackResponse = callbackResponse;
        this.classType = classType;
        this.helperObject = helperObject;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getReqUri() {
        return reqUri;
    }

    public CallbackResponse getCallbackResponse() {
        return callbackResponse;
    }

    public void setCallbackResponse(CallbackResponse callbackResponse) {
        this.callbackResponse = callbackResponse;
    }

    public Object getHelperObject() {
        return helperObject;
    }
    public void setHelperObject(Object helperObject) {
        this.helperObject = helperObject;
    }

    public void setReqTimeOut(int timeOut) {
        this.reqTimeOut = timeOut;
    }

    public HttpEntity<?> getHttpEntity(List<MediaType> acceptableMediaTypes) {
        if (acceptableMediaTypes == null) {
            acceptableMediaTypes = new ArrayList<>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        }
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(acceptableMediaTypes);

        // Populate the headers in an HttpEntity object to use for the request
        return new HttpEntity<>(requestHeaders);
    }

    protected ClientHttpRequestFactory getReqFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(reqTimeOut);
        return requestFactory;
    }

    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(getReqFactory());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    @Override
    public void run() {
        try {
            RestTemplate restTemplate = getRestTemplate();
            ResponseEntity<T> responseEntity = restTemplate.exchange(getReqUri(), HttpMethod.GET,
                    getHttpEntity(null), classType);
            Object resObj = responseEntity.getBody();
            getCallbackResponse().handleResponse(getRequestId(), false, false, resObj, helperObject);
        } catch (Exception ex) {
            ex.printStackTrace();
            String errMessage = "Please check your internet connectivity and retry";
            boolean isAPIException = false;
            if (ex instanceof HttpClientErrorException) {
                HttpClientErrorException clientExp = (HttpClientErrorException) ex;
                errMessage = clientExp.getResponseBodyAsString();
                isAPIException = true;
            }
            getCallbackResponse().handleResponse(getRequestId(), true, isAPIException, errMessage, helperObject);
        }
    }
}

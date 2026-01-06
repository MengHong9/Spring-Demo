package org.example.damo.common.logging;

import org.example.damo.common.constant.LoggingConstant;

import org.springframework.stereotype.Component;

@Component
public class LogFormatter {
    private static final String LOG_FORMAT = "%s : %s | target = %s , method = %s , httpMethod = %s , requestPath = %s , startTime = %s , endTime = %s , executeTime = %sms ";

    public String logRequest(String requestId , String target, String method , String httpMethod , String requestPath , Long startTime) {
        return String.format(LOG_FORMAT,
                LoggingConstant.REQUEST,
                requestId,
                target,
                method,
                httpMethod,
                requestPath,
                startTime,
                0,
                0
        );
    }

    public String logResponse(String requestId , String target, String method , String httpMethod , String requestPath , Long startTime , Long endTime) {
        return String.format(LOG_FORMAT,
                LoggingConstant.RESPONSE,
                requestId,
                target,
                method,
                httpMethod,
                requestPath,
                startTime,
                endTime,
                endTime - startTime
        );
    }


    public String logError(String requestId , String target, String method , String httpMethod , String requestPath , Long startTime , Long endTime) {
        return String.format(LOG_FORMAT,
                LoggingConstant.ERROR,
                requestId,
                target,
                method,
                httpMethod,
                requestPath,
                startTime,
                endTime,
                endTime - startTime
        );
    }

}

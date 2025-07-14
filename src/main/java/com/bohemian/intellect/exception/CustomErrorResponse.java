package com.bohemian.intellect.exception;

import java.util.Map;

public record CustomErrorResponse(String title, int status, String timeStamp, Map<String, String> errors) {}

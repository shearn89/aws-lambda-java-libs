/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.amazonaws.services.lambda.runtime.events.s3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Event to represent the response which should be returned as part of a S3 Batch custom
 * action.
 *
 * https://docs.aws.amazon.com/AmazonS3/latest/dev/batch-ops-invoke-lambda.html
 */

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class S3BatchResponse {

    private String invocationSchemaVersion;
    private ResultCode treatMissingKeysAs;
    private String invocationId;
    private List<Result> results;

    @Data
    @Builder(setterPrefix = "with")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {

        private String taskId;
        private ResultCode resultCode;
        private String resultString;
    }

    public enum ResultCode {

        /**
         * 	The task completed normally. If you requested a job completion report,
         * 	the task's result string is included in the report.
         */
        Succeeded,
        /**
         * The task suffered a temporary failure and will be redriven before the job
         * completes. The result string is ignored. If this is the final redrive,
         * the error message is included in the final report.
         */
        TemporaryFailure,
        /**
         * 	The task suffered a permanent failure. If you requested a job-completion
         * 	report, the task is marked as Failed and includes the error message
         * 	string. Result strings from failed tasks are ignored.
         */
        PermanentFailure
    }

    public static S3BatchResponseBuilder fromS3BatchEvent(S3BatchEvent s3BatchEvent) {
        return S3BatchResponse.builder()
                .withInvocationId(s3BatchEvent.getInvocationId())
                .withInvocationSchemaVersion(s3BatchEvent.getInvocationSchemaVersion());
    }
}
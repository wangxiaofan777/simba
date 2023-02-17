package com.wxf;

import datahub.client.rest.RestEmitter;
import datahub.event.UpsertAspectRequest;
import io.datahubproject.openapi.generated.DatasetProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        RestEmitter emitter = RestEmitter.createWithDefaults();

        List<UpsertAspectRequest> requests = new ArrayList<>();
        UpsertAspectRequest upsertAspectRequest = UpsertAspectRequest.builder()
                .entityType("dataset")
                .entityUrn("urn:li:dataset:(urn:li:dataPlatform:bigquery,my-project.my-other-dataset.user-table,PROD)")
                .aspect(new DatasetProperties().description("This is the canonical User profile dataset"))
                .build();
        UpsertAspectRequest upsertAspectRequest2 = UpsertAspectRequest.builder()
                .entityType("dataset")
                .entityUrn("urn:li:dataset:(urn:li:dataPlatform:bigquery,my-project.another-dataset.user-table,PROD)")
                .aspect(new DatasetProperties().description("This is the canonical User profile dataset 2"))
                .build();
        requests.add(upsertAspectRequest);
        requests.add(upsertAspectRequest2);
        System.out.println(emitter.emit(requests, null).get());
        System.exit(0);
    }
}
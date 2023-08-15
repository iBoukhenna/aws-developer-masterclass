package com.handy.aws.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

public class InventoryFindFunction implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);

        Region region = Region.US_EAST_1;
        S3Client s3Client = S3Client.builder().region(region).build();
        ResponseInputStream<?> objectData = s3Client.getObject(GetObjectRequest.builder().bucket("handy-inventory-data-iboukhenna").key("handy-tool-catalog.json").build());

        InputStreamReader inputStreamReader = new InputStreamReader(objectData);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Product[] products = null;

        Gson gson = new Gson();
        products = gson.fromJson(bufferedReader, Product[].class);
        return products[0].toString();
    }

}

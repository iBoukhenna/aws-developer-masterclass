package com.handy.aws.functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

public class InventoryFindFunction implements RequestHandler<HttpQueryStringRequest, HttpProductResponse> {

    @Override
    public HttpProductResponse handleRequest(HttpQueryStringRequest request, Context context) {
        String idAsString = (String) request.getQueryStringParameters().get("id");
        Integer productId = Integer.parseInt(idAsString);
        Product product = getProductById(productId);
        return new HttpProductResponse(product);
    }

    private Product getProductById(int productId) {
        Region region = Region.US_EAST_1;
        S3Client s3Client = S3Client.builder().region(region).build();
        ResponseInputStream<?> objectData = s3Client.getObject(GetObjectRequest.builder().bucket("handy-inventory-data-iboukhenna").key("handy-tool-catalog.json").build());

        InputStreamReader inputStreamReader = new InputStreamReader(objectData);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Product[] products = null;

        Gson gson = new Gson();
        products = gson.fromJson(bufferedReader, Product[].class);
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

}

package com.handy.aws.functions;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class InventoryDeleteFunction 
extends InventoryS3Client
implements RequestHandler<HttpRequest, HttpProductResponse> {

    @Override
    public HttpProductResponse handleRequest(HttpRequest request, Context context) {
        String idAsString = (String) request.getPathParameters().get("id");
        Integer productId = Integer.parseInt(idAsString);

        List<Product> productsList = getAllProductsList();

        boolean didRemove = productsList.removeIf(p -> p.getId() == productId);
        if (didRemove) {
            if (updateAllProducts(productsList)) {
                return new HttpProductResponse();
            }
        }

        HttpProductResponse response = new HttpProductResponse();
        response.setStatusCode("404");
        return response;
    }
}

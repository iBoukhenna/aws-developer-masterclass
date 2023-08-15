package com.handy.aws.functions;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class InventoryFindFunction 
extends InventoryS3Client
implements RequestHandler<HttpQueryStringRequest, HttpProductResponse> {

    @Override
    public HttpProductResponse handleRequest(HttpQueryStringRequest request, Context context) {
        String idAsString = (String) request.getQueryStringParameters().get("id");

        if (idAsString.equalsIgnoreCase("all")) {
            Product[] products = getAllProducts();
            HttpProductResponse response = new HttpProductResponse(products);
            return response;
        }

        Integer productId = Integer.parseInt(idAsString);
        Product product = getProductById(productId);
        return new HttpProductResponse(product);
    }

    private Product getProductById(int productId) {
        Product[] products = getAllProducts();

        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

}

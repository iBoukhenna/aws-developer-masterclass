package com.handy.aws.functions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class InventoryFindFunctionTest {

    private static HttpQueryStringRequest input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new HttpQueryStringRequest();
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("InventoryFindFunction");

        return ctx;
    }

    @Test
    public void testInventoryFindFunction() {
        InventoryFindFunction handler = new InventoryFindFunction();
        Context ctx = createContext();

        Gson gson = new Gson();

        Map<String, String> queryStringParameters = new HashMap<>();
        queryStringParameters.put("id", "102");
        input.setQueryStringParameters(queryStringParameters);

        HttpProductResponse output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals(gson.toJson(new Product(102, "Hammer", "DeWalt", "15oz MIG Weld", 14)), output.getBody());
    }
}

package com.alexey.shop.core.endpoints;

import com.alexey.shop.core.services.ProductsService;
import com.alexey.shop.core.soap.GetAllProductsRequest;
import com.alexey.shop.core.soap.GetAllProductsResponse;
import com.alexey.shop.core.soap.GetProductByNameRequest;
import com.alexey.shop.core.soap.GetProductByNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.transaction.Transactional;

@Endpoint
@RequiredArgsConstructor
public class ProductsEndpoint {

    private static final String NAMESPACE_URI = "http://alex.org/Products.xsd";
    private final ProductsService productsService;

    /* Пример запроса:
    *  POST http://localhost:8189/ws

*      Header ->  Content-Type: text/xml
*
          <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:f="http://alex.org/Products.xsd">
            <soapenv:Header/>
            <soapenv:Body>
               </f:getAllProductsRequest>
            </soapenv:Body>
          </soapenv:Envelope>

    * */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByNameRequest")
    @ResponsePayload
    @Transactional
    public GetProductByNameResponse getAllProducts(@RequestPayload GetProductByNameRequest request){
        GetProductByNameResponse response = new GetProductByNameResponse();
        response.setProduct(productsService.getProductByName(request.getName()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    @Transactional
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request){
        GetAllProductsResponse response = new GetAllProductsResponse();
        response.getProduct().addAll(productsService.getAllProducts());
        return response;
    }
}

package com.spinel.framework.globaladminintegration;



import com.spinel.framework.globaladminintegration.request.BankRequest;
import com.spinel.framework.globaladminintegration.request.SingleRequest;
import com.spinel.framework.globaladminintegration.response.ListResponse;
import com.spinel.framework.globaladminintegration.response.PageResponse;
import com.spinel.framework.globaladminintegration.response.PermissionResponseDto;
import com.spinel.framework.globaladminintegration.response.SingleResponse;
import com.spinel.framework.helpers.API;
import com.spinel.framework.helpers.CoreValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

//@Transactional
@Service
public class GlobalService {

    @Autowired
    private API api;
    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private CoreValidations validations;
    @Value("${bank.base.url}")
    private String bankBaseUrl;
    @Value("${country.base.url}")
    private String countryBaseUrl;
    @Value("${state.base.url}")
    private String stateBaseUrl;
    @Value("${lga.base.url}")
    private String lgaBaseUrl;

    @Value("${ward.base.url}")
    private String wardBaseUrl;

    @Value("${permission.base.url}")
    private String permissionBaseUrl;

    @Value("${permission.name.url}")
    private String permissionNameUrl;



    public PageResponse getBankPagination(BankRequest request)  {
//             validations.validateGlobalBank(request);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(bankBaseUrl+"page")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("code", request.getCode())
                .queryParam("page", request.getPage())
                .queryParam("pageSize", request.getPageSize());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        PageResponse response = api.get(builder.toUriString(), PageResponse.class, map);
        return response;
    }


    public ListResponse getBankList(BankRequest request)  {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(bankBaseUrl+"list")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("code", request.getCode());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        ListResponse response = api.get(builder.toUriString(), ListResponse.class, map);
        return response;
    }


    public SingleResponse getSingleBank(SingleRequest request) {

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        SingleResponse response = api.get(bankBaseUrl.trim()+request.getId(), SingleResponse.class, map);
        return response;
    }



    public PageResponse getCountryPagination(BankRequest request)  {
//        validations.validateGlobalBannk(request);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(countryBaseUrl+"page")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("code", request.getCode())
                .queryParam("page", request.getPage())
                .queryParam("pageSize", request.getPageSize());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        PageResponse response = api.get(builder.toUriString(), PageResponse.class, map);
        return response;
    }


    public ListResponse getCountryList(BankRequest request) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(countryBaseUrl+"list")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("code", request.getCode());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        ListResponse response = api.get(builder.toUriString(), ListResponse.class, map);
        return response;
    }

    public SingleResponse getSingleCountry(SingleRequest request) {

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        SingleResponse response = api.get(countryBaseUrl.trim()+request.getId(), SingleResponse.class, map);
        return response;
    }



    public PageResponse getStatePagination(BankRequest request) {
//        validations.validateGlobalBank(request);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(stateBaseUrl+"page")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("countryId", request.getCountryId())
                .queryParam("page", request.getPage())
                .queryParam("pageSize", request.getPageSize());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        PageResponse response = api.get(builder.toUriString(), PageResponse.class, map);
        return response;
    }


    public ListResponse getStateList(BankRequest request)  {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(stateBaseUrl+"list")
                // Add query parameter
                .queryParam("countryId", request.getCountryId())
                .queryParam("name", request.getName());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        ListResponse response = api.get(builder.toUriString(),ListResponse.class, map);
        return response;
    }



    public SingleResponse getSingleState(SingleRequest request) {

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        SingleResponse response = api.get(stateBaseUrl.trim()+request.getId(), SingleResponse.class, map);
        return response;
    }


    public PageResponse getLgaPagination(BankRequest request) {
//        validations.validateGlobalBank(request);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(lgaBaseUrl+"page")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("stateId", request.getStateId())
                .queryParam("page", request.getPage())
                .queryParam("pageSize", request.getPageSize());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        PageResponse response = api.get(builder.toUriString(), PageResponse.class, map);
        return response;
    }


    public ListResponse getLgaList(BankRequest request)  {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(lgaBaseUrl+"list")
                // Add query parameter
                .queryParam("stateId", request.getStateId())
                .queryParam("name", request.getName());


        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        ListResponse response = api.get(builder.toUriString(), ListResponse.class, map);
        return response;
    }


    public SingleResponse getSingleLga(SingleRequest request) {

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        SingleResponse response = api.get(lgaBaseUrl.trim()+request.getId(), SingleResponse.class, map);
        return response;
    }

    public SingleResponse getSingleWard(SingleRequest request) {

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        SingleResponse response = api.get(wardBaseUrl.trim()+request.getId(), SingleResponse.class, map);
        return response;
    }


    public ListResponse getWardList(BankRequest request)  {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(wardBaseUrl+"list")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("lgaId", request.getLgaId());


        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        ListResponse response = api.get(builder.toUriString(), ListResponse.class, map);
        return response;
    }



    public PageResponse getWardPagination(BankRequest request) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(wardBaseUrl+"page")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("stateId", request.getStateId())
                .queryParam("page", request.getPage())
                .queryParam("pageSize", request.getPageSize());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        PageResponse response = api.get(builder.toUriString(), PageResponse.class, map);
        return response;
    }



    public SingleResponse getSinglePermission(SingleRequest request) {

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        SingleResponse response = api.get(permissionBaseUrl.trim()+request.getId(), SingleResponse.class, map);
        return response;
    }

    public ListResponse getPermissionList(BankRequest request)  {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(permissionBaseUrl+"list")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("appPermission", request.getAppPermission());


        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        ListResponse response = api.get(builder.toUriString(), ListResponse.class, map);
        return response;
    }

    public PageResponse getPermissionPagination(BankRequest request) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(permissionBaseUrl+"page")
                // Add query parameter
                .queryParam("name", request.getName())
                .queryParam("appPermission", request.getAppPermission())
                .queryParam("page", request.getPage())
                .queryParam("pageSize", request.getPageSize());

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        PageResponse response = api.get(builder.toUriString(), PageResponse.class, map);
        return response;
    }


    public PermissionResponseDto getPermissionByName(String name) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(permissionNameUrl)
                // Add query parameter
                .queryParam("name", name);

        Map map = new HashMap();
        map.put("Authorization", accessTokenService.getGlobalToken());
        PermissionResponseDto response = api.get(builder.toUriString(), PermissionResponseDto.class, map);
        return response;
    }
}

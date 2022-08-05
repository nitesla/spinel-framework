package com.spinel.framework.service;


import com.spinel.framework.dto.requestDto.EnableDisEnableDto;
import com.spinel.framework.dto.requestDto.RolePermissionDto;
import com.spinel.framework.dto.responseDto.RolePermissionResponseDto;
import com.spinel.framework.exceptions.ConflictException;
import com.spinel.framework.exceptions.NotFoundException;
import com.spinel.framework.helpers.CoreValidations;
import com.spinel.framework.models.RolePermission;
import com.spinel.framework.models.User;
import com.spinel.framework.repositories.PermissionRepository;
import com.spinel.framework.repositories.RolePermissionRepository;
import com.spinel.framework.repositories.RoleRepository;
import com.spinel.framework.utils.CustomResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
@Slf4j
@Service
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final ModelMapper mapper;
    private final CoreValidations coreValidations;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final AuditTrailService auditTrailService;


    public RolePermissionService(RolePermissionRepository RolePermissionRepository,
                                 ModelMapper mapper, CoreValidations coreValidations,
                                 PermissionRepository permissionRepository,RoleRepository roleRepository,
                                 AuditTrailService auditTrailService) {
        this.rolePermissionRepository = RolePermissionRepository;
        this.mapper = mapper;
        this.coreValidations = coreValidations;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.auditTrailService = auditTrailService;
    }

    /**
     * <summary>
     * RolePermission creation
     * </summary>
     * <remarks>this method is responsible for creation of new RolePermission</remarks>
     */

//    public RolePermissionResponseDto createRolePermission(RolePermissionDto request,HttpServletRequest request1) {
//        coreValidations.validateRolePermission(request);
//        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
//        RolePermission rolePermission = new RolePermission();
//        for (long permission : request.getPermissionIds()) {
//            rolePermission.setPermissionId(permission);
//            rolePermission.setRoleId(request.getRoleId());
//            rolePermission.setCreatedBy(userCurrent.getId());
//            rolePermission.setIsActive(true);
//            boolean exists = rolePermissionRepository
//                    .existsByRoleIdAndPermissionId(request.getRoleId(), permission);
//            if (!exists) {
//                rolePermission = rolePermissionRepository.save(rolePermission);
//                log.debug("Create new RolePermission - {}" + new Gson().toJson(rolePermission));
//            }
//        }


    public void assignPermission(RolePermissionDto request) {
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        List<RolePermission> rolePerm = new ArrayList<>();
        request.getPermissionIds().forEach(p -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(p.getPermissionId());
            rolePermission.setPermissionName(p.getPermissionName());
            rolePermission.setRoleId(request.getRoleId());
            rolePermission.setCreatedBy(userCurrent.getId());
            rolePermission.setIsActive(true);
            log.info(" role permission details " + rolePermission);
            RolePermission exist = rolePermissionRepository.findByRoleIdAndPermissionId(request.getRoleId(),p.getPermissionId());
            if(exist != null){
                throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Permission id already assigned to the role ::::"+p.getPermissionId());
            }

            rolePermissionRepository.save(rolePermission);
            rolePerm.add(rolePermission);

        });

    }

//    List<PartnerCategories> categories = new ArrayList<>();
//        request.getCategories().forEach(p -> {
//        PartnerCategories tran = PartnerCategories.builder()
//                .categoryId(p.getCategoryId())
//                .build();
//        tran.setPartnerId(partner.getId());
//        tran.setCreatedBy(partner.getUserId());
//        tran.setIsActive(true);
//        log.info(" category details " + tran);
//        partnerCategoriesRepository.save(tran);
//        categories.add(tran);
//
//    });






    /**
     * <summary>
     * RolePermission update
     * </summary>
     * <remarks>this method is responsible for updating already existing RolePermission</remarks>
     */

//    public RolePermissionResponseDto updateRolePermission(RolePermissionDto request,HttpServletRequest request1) {
//        coreValidations.validateRolePermission(request);
//        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
//        rolePermissionRepository.findById(request.getId()).orElseThrow(() ->
//                new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
//                        "Requested role permission id does not exist!")
//        );
//        RolePermission rolePermission = new RolePermission();
//        for (long permission : request.getPermissionIds()) {
//            rolePermission.setPermissionId(permission);
//            rolePermission.setId(request.getId());
//            rolePermission.setRoleId(request.getRoleId());
//            rolePermission.setUpdatedBy(userCurrent.getId());
//            rolePermission.setIsActive(true);
//            boolean exists = rolePermissionRepository
//                    .existsByRoleIdAndPermissionId(request.getRoleId(), permission);
//            if (!exists) {
//                rolePermission = rolePermissionRepository.save(rolePermission);
//                log.debug("Create new RolePermission - {}" + new Gson().toJson(rolePermission));
//            }
//        }
//
//        auditTrailService
//                .logEvent(userCurrent.getUsername(),
//                        "Update role permission by username:" + userCurrent.getUsername(),
//                        AuditTrailFlag.UPDATE,
//                        " Update role permission Request for:" + rolePermission.getId(),1, Utility.getClientIp(request1));
//        return mapper.map(rolePermission, RolePermissionResponseDto.class);
//    }


    /**
     * <summary>
     * Find RolePermission
     * </summary>
     * <remarks>this method is responsible for getting a single record</remarks>
     */
    public RolePermissionResponseDto findRolePermission(Long id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested RolePermission id does not exist!"));
//        log.info(String.valueOf(Arrays.asList(rolePermission.getPermissionId())));
        return mapper.map(rolePermission, RolePermissionResponseDto.class);
    }


    /**
     * <summary>
     * Find all functions
     * </summary>
     * <remarks>this method is responsible for getting all records in pagination</remarks>
     */
    public Page<RolePermission> findAll(Long roleId, Boolean isActive, PageRequest pageRequest) {
        Page<RolePermission> functions = rolePermissionRepository.findRolePermission(roleId, isActive, pageRequest);
        if (functions == null) {
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return functions;
    }

    public void enableDisEnableState(EnableDisEnableDto request) {
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        RolePermission rolePermission = rolePermissionRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested creditLevel id does not exist!"));
//        creditLevel.setIsActive(request.isActive());
        rolePermission.setIsActive(request.getIsActive());
        rolePermission.setUpdatedBy(userCurrent.getId());
        rolePermissionRepository.save(rolePermission);

    }

    public List<RolePermission> getAll(Long roleId, Boolean isActive){
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleIdAndIsActive(roleId, isActive);
        return rolePermissions;

    }




    public List<RolePermission> getPermissionsByRole(Long roleId) {
        List<RolePermission> permissionRole = rolePermissionRepository.getPermissionsByRole(roleId);
        return permissionRole;
    }


    public void removePermission(Long id){
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested RolePermission id does not exist!"));
        rolePermissionRepository.delete(rolePermission);
    }



    public void deleteAllBYIds(List<Long> Longs) {
        rolePermissionRepository.deleteByIdIn(Longs);
    }
}

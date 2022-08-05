package com.spinel.framework.service;


import com.google.gson.Gson;


import com.spinel.framework.dto.requestDto.EnableDisEnableDto;
import com.spinel.framework.dto.requestDto.RoleDto;
import com.spinel.framework.dto.responseDto.RoleResponseDto;
import com.spinel.framework.dto.responseDto.RoleUserStat;
import com.spinel.framework.exceptions.ConflictException;
import com.spinel.framework.exceptions.NotFoundException;
import com.spinel.framework.helpers.CoreValidations;
import com.spinel.framework.models.Role;
import com.spinel.framework.models.RolePermission;
import com.spinel.framework.models.User;
import com.spinel.framework.repositories.PermissionRepository;
import com.spinel.framework.repositories.RolePermissionRepository;
import com.spinel.framework.repositories.RoleRepository;
import com.spinel.framework.utils.AuditTrailFlag;
import com.spinel.framework.utils.CustomResponseCode;
import com.spinel.framework.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@Service
public class RoleService {

    private RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final CoreValidations coreValidations;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionService rolePermissionService;
    private final AuditTrailService auditTrailService;
    private final UserService userService;


    public RoleService(RoleRepository roleRepository, ModelMapper mapper, CoreValidations coreValidations,
                       RolePermissionRepository rolePermissionRepository,PermissionRepository permissionRepository,
                       RolePermissionService rolePermissionService,AuditTrailService auditTrailService,
                       UserService userService) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.coreValidations = coreValidations;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionService = rolePermissionService;
        this.auditTrailService = auditTrailService;
        this.userService = userService;
    }



    /** <summary>
     * Role creation
     * </summary>
     * <remarks>this method is responsible for creation of new role</remarks>
     */

    public RoleResponseDto createRole(RoleDto request, HttpServletRequest request1) {
        coreValidations.validateRole(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Role role = mapper.map(request,Role.class);
        Role roleExist = roleRepository.findByName(request.getName());
        if(roleExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Role already exist");
        }
        role.setCreatedBy(userCurrent.getId());
        role.setIsActive(true);
        role = roleRepository.save(role);
        log.debug("Create new role - {}"+ new Gson().toJson(role));


        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Create new role by :" + userCurrent.getUsername(),
                        AuditTrailFlag.CREATE,
                        " Create new role for:" + role.getName(),1, Utility.getClientIp(request1));
        return mapper.map(role, RoleResponseDto.class);
    }




    /** <summary>
     * Role update
     * </summary>
     * <remarks>this method is responsible for updating already existing role</remarks>
     */

    public RoleResponseDto updateRole(RoleDto request,HttpServletRequest request1) {
        coreValidations.validateRole(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Role role = roleRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested role id does not exist!"));
        mapper.map(request, role);
        role.setUpdatedBy(userCurrent.getId());
        roleRepository.save(role);
        log.debug("role record updated - {}"+ new Gson().toJson(role));

        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Update role by username:" + userCurrent.getUsername(),
                        AuditTrailFlag.UPDATE,
                        " Update role Request for:" + role.getId(),1, Utility.getClientIp(request1));
        return mapper.map(role, RoleResponseDto.class);
    }



    /** <summary>
     * Find role
     * </summary>
     * <remarks>this method is responsible for getting a single record</remarks>
     */
    public RoleResponseDto findRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested role id does not exist!"));
        RoleResponseDto roleResponseDto = new RoleResponseDto();

        roleResponseDto.setId(role.getId());
        roleResponseDto.setName(role.getName());
        roleResponseDto.setDescription(role.getDescription());
        roleResponseDto.setCreatedBy(role.getCreatedBy());
        roleResponseDto.setCreatedDate(role.getCreatedDate());
        roleResponseDto.setUpdatedBy(role.getUpdatedBy());
        roleResponseDto.setUpdatedDate(role.getUpdatedDate());
        roleResponseDto.setIsActive(role.getIsActive());
        List<RolePermission> permissions= null;

        permissions = rolePermissionService.getPermissionsByRole(role.getId());

        roleResponseDto.setPermissions(permissions);

        return roleResponseDto;

    }



    /** <summary>
     * Find all roles
     * </summary>
     * <remarks>this method is responsible for getting all records in pagination</remarks>
     */
    public Page<Role> findAll(String name,Boolean isActive, PageRequest pageRequest ){
        Page<Role> roles = roleRepository.findRoles(name,isActive,pageRequest);
        if(roles == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return roles;

    }


    public Page<Role> findRolesByClientId(String name,Long clientId,Boolean isActive, PageRequest pageRequest ){
        Page<Role> roles = roleRepository.findRolesByClientId(name,clientId,isActive,pageRequest);
        if(roles == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }

        roles.getContent().forEach(role ->{

            RoleUserStat rolestat= userService.fetchByRoleIdUserCount(role.getId());
               role.setUsers(rolestat.getActiveRoleUsers().toString());
        });
        return roles;

    }


    public List<Role> getAll(Boolean isActive){
        List<Role> roles = roleRepository.findByIsActive(isActive);
        return roles;

    }

    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this method is responsible for enabling and dis enabling a role</remarks>
     */
    public void enableDisable (EnableDisEnableDto request, HttpServletRequest request1){
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Role role  = roleRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested role id does not exist!"));
//        role.setIsActive(request.isActive());
        role.setIsActive(request.getIsActive());
        role.setUpdatedBy(userCurrent.getId());

        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Disable/Enable role by :" + userCurrent.getUsername() ,
                        AuditTrailFlag.UPDATE,
                        " Disable/Enable role Request for:" +  role.getId()
                                + " " +  role.getName(),1, Utility.getClientIp(request1));
        roleRepository.save(role);

    }






}

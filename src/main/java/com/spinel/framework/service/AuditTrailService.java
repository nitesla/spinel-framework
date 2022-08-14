package com.spinel.framework.service;


import com.spinel.framework.exceptions.NotFoundException;
import com.spinel.framework.models.AuditTrail;
import com.spinel.framework.models.Role;
import com.spinel.framework.models.User;
import com.spinel.framework.repositories.AuditTrailRepository;
import com.spinel.framework.repositories.RoleRepository;
import com.spinel.framework.repositories.UserRepository;
import com.spinel.framework.utils.AuditTrailFlag;
import com.spinel.framework.utils.CustomResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@SuppressWarnings("ALL")
public class AuditTrailService {



    private AsyncService asyncService;
    private AuditTrailRepository auditTrailRepository;
    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private final ModelMapper mapper;

    public AuditTrailService(AsyncService asyncService,AuditTrailRepository auditTrailRepository, ModelMapper mapper) {
        this.asyncService = asyncService;
        this.auditTrailRepository = auditTrailRepository;
        this.mapper = mapper;
    }




    public void logEvent(String username, String event, AuditTrailFlag flag, String request,
                         int status, String ipAddress) {
        log.info(":::::: PROCESSING AUDIT TRAIL ::");
        asyncService.processAudit(username, event, flag, request, status, ipAddress);
    }



    public AuditTrail getAudit(Long id){
        AuditTrail auditTrail  = auditTrailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested id does not exist!"));
        return auditTrail;
    }



    public Page<AuditTrail> findAll(String username, String event, String flag, LocalDateTime startDate, LocalDateTime endDate, PageRequest pageRequest ){
        Page<AuditTrail> auditTrails = auditTrailRepository.audits(username,event,flag,startDate,endDate,pageRequest);
        if(auditTrails == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }

        auditTrails.getContent().forEach(auditTrail -> {
            User user = userRepository.findByUsername(auditTrail.getUsername());
            if(user.getRoleId() !=null){
                Role role = roleRepository.getOne(user.getRoleId());
                auditTrail.setRoleName(role.getName());
            }
        });
        return auditTrails;

    }



}

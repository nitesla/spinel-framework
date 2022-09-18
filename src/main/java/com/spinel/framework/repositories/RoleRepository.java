package com.spinel.framework.repositories;


import com.spinel.framework.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Query("SELECT r FROM Role r WHERE r.isActive =?1")
    Role findIsActive(Boolean isActive);
    Role findByNameAndClientId(String name,Long clientId);

    List<Role> findByIsActive(Boolean isActive);


    @Query("SELECT r FROM Role r WHERE ((:name IS NULL) OR (:name IS NOT NULL AND r.name like %:name%))" +
            " AND ((:isActive IS NULL) OR (:isActive IS NOT NULL AND r.isActive = :isActive)) order by r.id")
    Page<Role> findRoles(@Param("name")String name,
                         @Param("isActive")Boolean isActive,
                         Pageable pageable);



    @Query("SELECT r FROM Role r WHERE ((:name IS NULL) OR (:name IS NOT NULL AND r.name like %:name%))" +
            " AND ((:clientId IS NULL) OR (:clientId IS NOT NULL AND r.clientId = :clientId))"+
            " AND ((:isActive IS NULL) OR (:isActive IS NOT NULL AND r.isActive = :isActive)) order by r.id")
    Page<Role> findRolesByClientId(@Param("name")String name,
                                   @Param("clientId")Long clientId,
                         @Param("isActive")Boolean isActive,
                         Pageable pageable);

    List<Role> findByIsActiveAndClientId(Boolean isActive , Long clientId);


    Integer countAllByIsActive(boolean isActive);
    List<Role> findAll();
    Integer countAllByName(String name);

    @Query(value = "SELECT count(User.id) as count from User, Role WHERE Role.id = :id AND Role.id = User.roleId AND User.isActive = true", nativeQuery = true)
    Map getCountOfActiveUsers(@Param("id") Long id);
}

package com.spinel.framework.helpers;




import com.spinel.framework.dto.requestDto.*;
import com.spinel.framework.exceptions.BadRequestException;
import com.spinel.framework.exceptions.ConflictException;
import com.spinel.framework.exceptions.NotFoundException;
import com.spinel.framework.models.Role;
import com.spinel.framework.models.RolePermission;
import com.spinel.framework.models.User;
import com.spinel.framework.repositories.PermissionRepository;
import com.spinel.framework.repositories.RolePermissionRepository;
import com.spinel.framework.repositories.RoleRepository;
import com.spinel.framework.repositories.UserRepository;
import com.spinel.framework.utils.CustomResponseCode;
import com.spinel.framework.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@SuppressWarnings("All")
@Slf4j
@Service
public class CoreValidations {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PermissionRepository permissionRepository;
    private RolePermissionRepository rolePermissionRepository;

    public CoreValidations(RoleRepository roleRepository,UserRepository userRepository, PermissionRepository permissionRepository,
                           RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public void validateRole(RoleDto roleDto) {
        if (roleDto.getName() == null || roleDto.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
        if (roleDto.getName().length() < 2 || roleDto.getName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid name  length");
//        if (roleDto.getDescription() == null || roleDto.getDescription().isEmpty())
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Description cannot be empty");
    }

    public void validateFunction(PermissionDto permissionDto) {

        if (permissionDto.getName() == null || permissionDto.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Name cannot be empty");
        if (permissionDto.getName().length() < 2 || permissionDto.getName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid name  length");

        if (permissionDto.getCode() == null || permissionDto.getCode().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "code cannot be empty");
    }

    public void validateRolePermission(RolePermission rolePermissionDto) {
        if (rolePermissionDto.getRoleId()== null)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Role Id cannot be empty");
        if (rolePermissionDto.getPermissionName()== null)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Permission name cannot be empty");
        if (rolePermissionDto.getPermissionId() == null)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Role permissionid cannot be empty");
        roleRepository.findById(rolePermissionDto.getRoleId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " Enter a valid Role"));
        permissionRepository.findById(rolePermissionDto.getPermissionId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " Enter a valid permissionId"));
        RolePermission rolePermission = rolePermissionRepository.findByRoleIdAndPermissionId(rolePermissionDto.getRoleId(),rolePermissionDto.getPermissionId());
        if(rolePermission !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Permission already assigned to the role"+rolePermissionDto.getPermissionId());
        }

    }


    public void validateUser(UserDto userDto) {

        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "First name cannot be empty");
        if (!Utility.validateName(userDto.getFirstName()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for First Name ");
        if (userDto.getFirstName().length() < 2 || userDto.getFirstName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid first name  length");

        if (userDto.getLastName() == null || userDto.getLastName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Last name cannot be empty");
        if (!Utility.validateName(userDto.getLastName()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for Last Name ");
        if (userDto.getLastName().length() < 2 || userDto.getLastName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid last name  length");


        if (userDto.getEmail() == null || userDto.getEmail().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "email cannot be empty");
        if (!Utility.validEmail(userDto.getEmail().trim()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid Email Address");
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Email already exist");
        }

        if (userDto.getPhone() == null || userDto.getPhone().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Phone number cannot be empty");
        if (userDto.getPhone().length() < 8 || userDto.getPhone().length() > 14)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid phone number  length");
        if (!Utility.isNumeric(userDto.getPhone()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for phone number ");
        User userExist = userRepository.findByPhone(userDto.getPhone());
        if(userExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, "  user phone already exist");
        }
        if(userDto.getRoleId()== null)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Role id can not be null ");



//        if (userDto.getPassword() == null || userDto.getPassword().isEmpty())
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Password cannot be empty");
//        if (userDto.getPassword().length() < 6 || userDto.getPassword().length() > 20)// NAME LENGTH*********
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid password length");

    }



    public void changeTransactionPin(SetTransactionPin setTransactionPin) {
        if (setTransactionPin.getTransactionPin() == null || setTransactionPin.getTransactionPin().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Transaction pin cannot be empty");

        if (!Utility.isNumeric(setTransactionPin.getTransactionPin()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Transaction pin must be numeric ");

        if (setTransactionPin.getTransactionPin().length() < 4 || setTransactionPin.getTransactionPin().length() > 6)// LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid pin length");
    }

    public void validateTransactionPin(CreateTransactionPinDto transactionPinDto) {
        if (transactionPinDto.getTransactionPin() == null || transactionPinDto.getTransactionPin().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Transaction pin cannot be empty");

        if (!Utility.isNumeric(transactionPinDto.getTransactionPin()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Transaction pin must be numeric ");

        if (transactionPinDto.getTransactionPin().length() < 4 || transactionPinDto.getTransactionPin().length() > 6)// LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid pin length");
    }


    public void updateUser(UserDto userDto) {


        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "First name cannot be empty");
        if (userDto.getFirstName().length() < 2 || userDto.getFirstName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid first name  length");
        if(userDto.getRoleId()== null)
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Role id cannot be empty");

        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " Enter a valid Role"));

        if (userDto.getLastName() == null || userDto.getLastName().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Last name cannot be empty");
        if (userDto.getLastName().length() < 2 || userDto.getLastName().length() > 100)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid last name  length");

        if (userDto.getEmail() == null || userDto.getEmail().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "email cannot be empty");
        if (!Utility.validEmail(userDto.getEmail().trim()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid Email Address");

        if (userDto.getPhone() == null || userDto.getPhone().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Phone number cannot be empty");
        if (userDto.getPhone().length() < 8 || userDto.getPhone().length() > 14)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid phone number  length");
        if (!Utility.isNumeric(userDto.getPhone()))
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid data type for phone number ");

    }



    public void changePassword(ChangePasswordDto changePasswordDto) {
        if (changePasswordDto.getPassword() == null || changePasswordDto.getPassword().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Password cannot be empty");
        if (changePasswordDto.getPassword().length() < 6 || changePasswordDto.getPassword().length() > 20)// NAME LENGTH*********
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid password length");
        if (changePasswordDto.getPreviousPassword() == null || changePasswordDto.getPreviousPassword().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Previous password cannot be empty");


    }


    public void generatePasswordValidation(GeneratePassword request) {

        if (request.getPhone() == null || request.getPhone().isEmpty())
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Phone cannot be empty");

    }


//    public void validateGlobalBank(BankRequest request) {
//
//        if (request.getPage() >0)
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Page cannot be empty");
//        if (request.getPageSize() > 0)
//            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Page size cannot be empty");
//
//    }


}

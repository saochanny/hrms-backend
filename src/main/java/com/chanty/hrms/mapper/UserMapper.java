package com.chanty.hrms.mapper;

import com.chanty.hrms.dto.io.UploadFileResponse;
import com.chanty.hrms.dto.user.UserRequest;
import com.chanty.hrms.dto.user.UserResponse;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.service.impl.UserFileService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);

    User toUser(UserRequest userRequest);

    @Mapping(source = "roles", target = "roles")
    @Mapping(target = "image", ignore = true)
    UserResponse toUserResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream().map(role -> role.getName().getName()).collect(Collectors.toSet());
    }

    default String getImage(String image, @Context UserFileService userFileService) throws IOException {
        UploadFileResponse response = userFileService.download(image);
        if (response == null) {
            return null;
        }
        return response.getFile();
    }

    @Mapping(target = "image", source = "tempPath")
    void updateUser(UserRequest source, @MappingTarget User target);
}

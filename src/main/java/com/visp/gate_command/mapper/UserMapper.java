package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User toEntity(UserDto userDto);

  UserDto toDto(User user);
}

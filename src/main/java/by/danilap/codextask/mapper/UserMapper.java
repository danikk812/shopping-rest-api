package by.danilap.codextask.mapper;

import by.danilap.codextask.dto.tag.TagDTO;
import by.danilap.codextask.dto.tag.TagRequestDTO;
import by.danilap.codextask.dto.user.UserDTO;
import by.danilap.codextask.entity.Tag;
import by.danilap.codextask.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }


}

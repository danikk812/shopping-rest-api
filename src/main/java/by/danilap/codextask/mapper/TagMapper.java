package by.danilap.codextask.mapper;

import by.danilap.codextask.dto.tag.TagDTO;
import by.danilap.codextask.dto.tag.TagRequestDTO;
import by.danilap.codextask.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Tag convertToEntity(TagRequestDTO tagRequestDTO) {
        return modelMapper.map(tagRequestDTO, Tag.class);
    }

    public TagDTO convertToDTO(Tag tag) {
        return modelMapper.map(tag, TagDTO.class);
    }


}

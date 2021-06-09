package by.danilap.codextask.util.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class ItemQueryParameter {

    @NotBlank
    private String description;

    private List<String> tagName;

    public ItemQueryParameter(String description, List<String> tagName) {
        if (!StringUtils.isEmpty(description)) {
            setDescription(description);
        }
        if (tagName != null && !tagName.isEmpty()) {
            this.tagName = tagName;
        }
    }

    public boolean isEmpty() {
        return description == null && tagName == null;
    }

}

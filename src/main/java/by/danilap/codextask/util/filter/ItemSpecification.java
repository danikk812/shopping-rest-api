package by.danilap.codextask.util.filter;

import by.danilap.codextask.entity.Item;
import by.danilap.codextask.entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemSpecification {

    public static Specification<Item> findByQueryParameter(ItemQueryParameter itemQueryParameter) {
        return (Specification<Item>) (root, query, builder) -> {

            List<Predicate>  predicateList = new ArrayList<>();

            String description = itemQueryParameter.getDescription();
            if (description != null) {
                Predicate predicate = builder.like(root.get("description"), "%" + description + "%");
            }

            List<String> tagNameList = itemQueryParameter.getTagName();
            if (tagNameList != null) {
                List<String> tagNamesWithoutDuplicates  = tagNameList.stream().distinct().collect(Collectors.toList());

                if (tagNameList.size() != tagNamesWithoutDuplicates.size()) {
                    return builder.disjunction();
                }

                ListJoin<Tag, Item> tagJoin = root.joinList("tags");
                Path<Tag> tagList = tagJoin.get("name");

                tagNameList.forEach(tag -> predicateList.add(builder.equal(tagList, tag)));
            }

            return builder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}

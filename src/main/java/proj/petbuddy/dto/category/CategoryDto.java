package proj.petbuddy.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import proj.petbuddy.domain.category.Category;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private Long depth;
    private List<CategoryDto> children;

    public static CategoryDto of(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getCateName(),
                category.getDepth(),
                category.getChildren().stream().map(CategoryDto::of).collect(Collectors.toList())
        );
    }
}
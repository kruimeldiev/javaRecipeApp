package nl.casperdaris.recipesapp.recipe;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@org.springframework.data.relational.core.mapping.Table("recipes")
public record Recipe(
        @Id Integer id, @NotEmpty String title,
        String description, @Positive Integer timeInMinutes,
        Double rating, @Version Integer version) {

}

package telran.oos.api.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static telran.oos.api.ApiConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
    @NotNull
    Long id;
    @NotNull @Size(min = MIN_CATEGORY_NAME_LENGTH, max = MAX_CATEGORY_NAME_LENGTH)
    String name;
}

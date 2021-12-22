package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class DateDto {
    int dayofMonth;
    String dayofWeek;
    int month;
    int year;
}

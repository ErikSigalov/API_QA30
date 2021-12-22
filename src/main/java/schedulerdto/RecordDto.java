package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class RecordDto {

    int breaks;
    String currency;
    DateDto dateDto;
    int hours;
    int id;
    String timeFrom;
    String timeTo;
    String title;
    int totalSalary;
    String type;
    int wage;
}

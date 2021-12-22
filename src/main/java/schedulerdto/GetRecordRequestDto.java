package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class GetRecordRequestDto {

    int monthFrom;
    int getMonthTo;
    int yearFrom;
    int yearTo;
}

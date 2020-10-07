package ru.sertok.robot.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.sertok.robot.data.Status;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BaseResponse {
    private Status status;
    private String error;
}

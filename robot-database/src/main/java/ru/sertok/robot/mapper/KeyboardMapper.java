package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.entity.KeyboardEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface KeyboardMapper {
    @Mapping(target = "testCase", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position", ignore = true)
    KeyboardEntity toKeyboardEntity(Keyboard keyboard);

    Keyboard toKeyboard(KeyboardEntity keyboardEntity);
}

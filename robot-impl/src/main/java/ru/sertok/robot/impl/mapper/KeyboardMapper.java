package ru.sertok.robot.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.Keyboard;
import ru.sertok.robot.entity.KeyboardEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface KeyboardMapper {
    Keyboard toKeyboard(KeyboardEntity keyboardEntity);
}

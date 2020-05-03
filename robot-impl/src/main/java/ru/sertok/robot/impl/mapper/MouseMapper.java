package ru.sertok.robot.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.Mouse;
import ru.sertok.robot.entity.MouseEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MouseMapper {
    @Mapping(target = "testCase", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position", ignore = true)
    MouseEntity toMouseEntity(Mouse mouse);

    Mouse toMouse(MouseEntity mouseEntity);
}

package ru.sertok.robot.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.entity.ScreenShotEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ScreenShotMapper {
    @Mapping(target = "expectedImage", ignore = true)
    @Mapping(target = "actualImage", ignore = true)
    Image toImage(ScreenShotEntity screenShotEntity);
}

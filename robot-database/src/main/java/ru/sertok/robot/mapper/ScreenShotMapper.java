package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.entity.ScreenShotEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ScreenShotMapper {
    @Mapping(target = "image", ignore = true)
    Image toImage(ScreenShotEntity screenShotEntity);
}

package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.ScreenshotSize;
import ru.sertok.robot.entity.ScreenShotEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ScreenShotMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "testCase", ignore = true)
    ScreenShotEntity toScreenShotEntity(ScreenshotSize size);

    ScreenshotSize toScreenshotSize(ScreenShotEntity screenShotEntity);
}

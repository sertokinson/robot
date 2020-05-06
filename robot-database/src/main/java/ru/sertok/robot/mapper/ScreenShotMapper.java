package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.Image;
import ru.sertok.robot.entity.ScreenShotEntity;
import ru.sertok.robot.request.ScreenShotRequest;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ScreenShotMapper {
    @Mapping(target = "image", ignore = true)
    Image toImage(ScreenShotEntity screenShotEntity);

    @Mapping(target = "image", ignore = true)
    Image toImage(ScreenShotRequest screenShotRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "testCase", ignore = true)
    ScreenShotEntity toScreenShot(Image image);
}

package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.BaseTestCase;
import ru.sertok.robot.entity.TestCaseEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCaseMapper {
    BaseTestCase toBaseTestCase(TestCaseEntity testCaseEntity);

    @Mapping(target = "mouse", ignore = true)
    @Mapping(target = "keyboard", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "screenShots", ignore = true)
    @Mapping(target = "id", ignore = true)
    TestCaseEntity toTestCaseEntity(BaseTestCase baseTestCase);
}

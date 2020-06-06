package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.BaseTestCase;
import ru.sertok.robot.entity.TestCaseEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCaseMapper {

    @Mapping(target = "browser.name", source = "browserName")
    @Mapping(target = "browser.version", source = "browserVersion")
    BaseTestCase toBaseTestCase(TestCaseEntity testCaseEntity);

    @Mapping(target = "mouse", ignore = true)
    @Mapping(target = "keyboard", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "screenShots", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "runDate", ignore = true)
    @Mapping(target = "browserName", source = "browser.name")
    @Mapping(target = "browserVersion", source = "browser.version")
    TestCaseEntity toTestCaseEntity(BaseTestCase baseTestCase);
}

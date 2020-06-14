package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.request.RecordRequest;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCaseMapper {

    @Mapping(target = "url", source = "url.url")
    @Mapping(target = "testCaseName", source = "name")
    @Mapping(target = "pathToApp", expression = "java(testCaseEntity.getIsBrowser()?testCaseEntity.getBrowser().getPath():testCaseEntity.getDesktop().getPath())")
    @Mapping(target = "appName", expression = "java(testCaseEntity.getIsBrowser()?testCaseEntity.getBrowser().getName():testCaseEntity.getDesktop().getName())")
    TestCase toTestCase(TestCaseEntity testCaseEntity);

    @Mapping(target = "time", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "runDate", ignore = true)
    TestCase toTestCase(RecordRequest recordRequest);

    @Mapping(target = "mouse", ignore = true)
    @Mapping(target = "keyboard", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "screenShots", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "desktop", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "runDate", ignore = true)
    @Mapping(target = "browser.id", ignore = true)
    @Mapping(target = "name", source = "testCaseName")
    TestCaseEntity toTestCaseEntity(TestCase testCase);

}

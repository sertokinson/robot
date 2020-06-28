package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.request.RecordRequest;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCaseMapper {

    @Mapping(target = "url", ignore = true)
    @Mapping(target = "pathToApp", ignore = true)
    @Mapping(target = "appName", ignore = true)
    @Mapping(target = "folderName", ignore = true)
    @Mapping(target = "testCaseName", source = "name")
    TestCase toTestCase(TestCaseEntity testCaseEntity);

    @Mapping(target = "time", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "runDate", ignore = true)
    @Mapping(target = "appName", ignore = true)
    @Mapping(target = "pathToApp", source = "path")
    TestCase toTestCase(RecordRequest recordRequest);

    @Mapping(target = "mouse", ignore = true)
    @Mapping(target = "keyboard", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "screenShots", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "runDate", ignore = true)
    @Mapping(target = "browserId", ignore = true)
    @Mapping(target = "urlId", ignore = true)
    @Mapping(target = "desktopId", ignore = true)
    @Mapping(target = "folderId", ignore = true)
    @Mapping(target = "name", source = "testCaseName")
    TestCaseEntity toTestCaseEntity(TestCase testCase);

}

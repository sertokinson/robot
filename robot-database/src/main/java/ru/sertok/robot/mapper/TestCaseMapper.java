package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.request.RecordRequest;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCaseMapper {

    @Mapping(target = "time", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "runDate", ignore = true)
    @Mapping(target = "appName", ignore = true)
    TestCase toTestCase(RecordRequest recordRequest);

}

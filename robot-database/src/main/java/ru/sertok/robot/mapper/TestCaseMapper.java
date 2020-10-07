package ru.sertok.robot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.request.RecordRequest;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TestCaseMapper {
    TestCase toTestCase(RecordRequest recordRequest);
}

package ru.sertok.robot.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@XmlRootElement(name = "ImageOutputRequest")
public class ImageOutputRequest {
    @Parameter(required = true)
    private String testCase;

    @PathParam("testCase")
    @Parameter(required = true)
    @XmlElement(name = "testCase")
    public String getTestCase() {
        return testCase;
    }
}

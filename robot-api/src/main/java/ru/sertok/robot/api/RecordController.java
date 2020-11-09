package ru.sertok.robot.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sertok.robot.data.TestCase;

@CrossOrigin
@RequestMapping("/record")
public interface RecordController {

    @PostMapping("/start")
    ResponseEntity start(@RequestBody TestCase testCase);

    @GetMapping("/stop")
    ResponseEntity stop() throws InterruptedException;

    @GetMapping("/exit")
    ResponseEntity exit();
}

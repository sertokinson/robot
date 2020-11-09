package ru.sertok.robot.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sertok.robot.request.SettingsRequest;
import ru.sertok.robot.response.AppResponse;

@CrossOrigin
@RequestMapping("/")
public interface AppController {

    @GetMapping("/ping")
    ResponseEntity<AppResponse> ping();

    @GetMapping("/pathToLog")
    ResponseEntity<AppResponse> pathToLog();

    @GetMapping("/version")
    ResponseEntity<AppResponse> version();

    @GetMapping("/settings")
    ResponseEntity<AppResponse> settings();

    @PostMapping("/saveSetting")
    ResponseEntity saveSetting(@RequestBody SettingsRequest settingsRequest);
}

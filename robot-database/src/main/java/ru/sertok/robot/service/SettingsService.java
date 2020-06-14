package ru.sertok.robot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.entity.BrowserEntity;
import ru.sertok.robot.entity.DesktopEntity;
import ru.sertok.robot.entity.UrlEntity;
import ru.sertok.robot.repository.BrowserRepository;
import ru.sertok.robot.repository.DesktopRepository;
import ru.sertok.robot.repository.UrlRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SettingsService {
    private final UrlRepository urlRepository;
    private final DesktopRepository desktopRepository;
    private final BrowserRepository browserRepository;

    public List<String> getUrls() {
        return urlRepository.findAll()
                .stream()
                .map(UrlEntity::getUrl)
                .collect(Collectors.toList());
    }

    public List<String> getDesktops() {
        return desktopRepository.findAll()
                .stream()
                .map(DesktopEntity::getName)
                .collect(Collectors.toList());
    }

    public List<String> getBrowsers() {
        return browserRepository.findAll()
                .stream()
                .map(BrowserEntity::getName)
                .collect(Collectors.toList());
    }

    public BrowserEntity getBrowser(String name){
        return browserRepository.findByName(name).orElse(null);
    }

    public DesktopEntity getDesktop(String name){
        return desktopRepository.findByName(name).orElse(null);
    }

    public String getPathToApp(TestCase testCase) {
        String appName = testCase.getAppName();
        if (testCase.getIsBrowser())
            return browserRepository.findByName(appName).map(BrowserEntity::getPath).orElse(null);
        return desktopRepository.findByName(appName).map(DesktopEntity::getPath).orElse(null);
    }

    public BrowserEntity saveBrowser(TestCase testCase) {
        return browserRepository.save(BrowserEntity.builder()
                .name(testCase.getAppName())
                .path(testCase.getPathToApp())
                .build());
    }

    public UrlEntity saveUrl(String url){
        return urlRepository.save(UrlEntity.builder()
                .url(url)
                .build());
    }

    public DesktopEntity saveDesktop(String name, String path){
        return desktopRepository.save(DesktopEntity.builder()
                .name(name)
                .path(path)
                .build());
    }

}

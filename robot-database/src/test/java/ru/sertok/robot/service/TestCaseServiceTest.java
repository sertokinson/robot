package ru.sertok.robot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.sertok.robot.data.TestCase;
import ru.sertok.robot.entity.DesktopEntity;
import ru.sertok.robot.entity.FolderEntity;
import ru.sertok.robot.entity.TestCaseEntity;
import ru.sertok.robot.mapper.TestCaseMapper;
import ru.sertok.robot.repository.FolderRepository;
import ru.sertok.robot.repository.TestCaseRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCaseServiceTest {
    @InjectMocks
    private TestCaseService testCaseService;

    @Mock
    private FolderRepository folderRepository;

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private SettingsService settingsService;

    @Mock
    private TestCaseMapper testCaseMapper;


    @Test
    public void getFolders() {
        List<FolderEntity> folders = new ArrayList<>();
        FolderEntity folder1 = FolderEntity.builder()
                .id(1L)
                .name("folder1")
                .build();
        FolderEntity folder2 = FolderEntity.builder()
                .id(2L)
                .name("folder2")
                .build();
        folders.add(folder1);
        folders.add(folder2);
        TestCaseEntity test1 = TestCaseEntity.builder()
                .desktopId(1L)
                .folderId(1L)
                .name("test1")
                .build();
        TestCaseEntity test2 = TestCaseEntity.builder()
                .desktopId(1L)
                .folderId(1L)
                .name("test2")
                .build();
        TestCaseEntity test3 = TestCaseEntity.builder()
                .desktopId(1L)
                .folderId(2L)
                .name("test3")
                .build();
        List<TestCaseEntity> testCases1 = new ArrayList<>();
        testCases1.add(test1);
        testCases1.add(test2);
        when(folderRepository.findAll()).thenReturn(folders);
        when(testCaseRepository.findAllByFolderId(1L)).thenReturn(testCases1);
        when(testCaseRepository.findAllByFolderId(2L)).thenReturn(Collections.singletonList(test3));
        when(settingsService.getFolder(1L)).thenReturn(folder1);
        when(settingsService.getFolder(2L)).thenReturn(folder2);
        when(settingsService.getDesktop(anyLong())).thenReturn(DesktopEntity.builder().build());
        when(testCaseMapper.toTestCase(test1)).thenReturn(TestCase.builder()
                .testCaseName("test1")
                .folderName("folder1")
                .isBrowser(false)
                .build());
        when(testCaseMapper.toTestCase(test2)).thenReturn(TestCase.builder()
                .testCaseName("test2")
                .folderName("folder1")
                .isBrowser(false)
                .build());
        when(testCaseMapper.toTestCase(test3)).thenReturn(TestCase.builder()
                .testCaseName("test3")
                .folderName("folder2")
                .isBrowser(false)
                .build());
        Map<String, List<TestCase>> result = testCaseService.getFolders();
        assertEquals(2, result.get("folder1").size());
        assertEquals(1, result.get("folder2").size());
    }
}
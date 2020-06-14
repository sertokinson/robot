package ru.sertok.robot.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "URL",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"url"})})
public class UrlEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * url сайта который тестируется
     */
    @Column(name = "URL")
    private String url;

    /**
     * Все тесты которые тестируют этот url
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "url", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TestCaseEntity> testCases;
}

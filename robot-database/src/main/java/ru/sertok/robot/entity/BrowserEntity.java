package ru.sertok.robot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BROWSER",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
public class BrowserEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Браузер в котором тестируется приложение
     */
    @Column(name = "NAME")
    private String name;

    /**
     * путь до браузера
     */
    @Column(name = "PATH")
    private String path;
}

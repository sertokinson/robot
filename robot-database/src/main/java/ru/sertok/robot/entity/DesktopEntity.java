package ru.sertok.robot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DESKTOP",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
public class DesktopEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * имя приложения
     */
    @Column(name = "NAME")
    private String name;

    /**
     * путь до приложения
     */
    @Column(name = "PATH")
    private String path;
}

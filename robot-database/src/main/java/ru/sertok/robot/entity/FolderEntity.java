package ru.sertok.robot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FOLDER",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class FolderEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * имя папки
     */
    @Column(name = "NAME")
    private String name;
}

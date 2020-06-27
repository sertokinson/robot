package ru.sertok.robot.entity;

import lombok.*;

import javax.persistence.*;

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
    private Long id;

    /**
     * url сайта который тестируется
     */
    @Column(name = "URL")
    private String url;
}

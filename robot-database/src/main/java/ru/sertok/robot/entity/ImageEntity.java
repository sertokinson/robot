package ru.sertok.robot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "IMAGE")
public class ImageEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "photo_expected")
    private byte[] photoExpected;

    @Column(name = "photo_actual")
    private byte[] photoActual;

    /**
     * порядок в катором было событие
     */
    @Column(name = "position", nullable = false)
    private int position;

    /**
     * Тест кейс к которому относится данное изображение
     */
    @ManyToOne
    @JoinColumn(name = "TEST_CASE_ID")
    private TestCaseEntity testCase;
}

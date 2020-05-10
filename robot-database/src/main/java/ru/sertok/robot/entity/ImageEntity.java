package ru.sertok.robot.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Arrays;

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

    @Lob
    @Column(name = "photo_expected", length = 10000)
    private byte[] photoExpected;

    @Lob
    @Column(name = "photo_actual", length = 10000)
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TEST_CASE_ID")
    private TestCaseEntity testCase;

    /**
     * Это переопределениме нужно чтоб получить только id тест-кейса
     * З.Ы.: Как это сделать через lombok не знаю
     *
     * @return строку
     */
    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                ", photoExpected=" + Arrays.toString(photoExpected) +
                ", photoActual=" + Arrays.toString(photoActual) +
                ", position=" + position +
                ", testCaseId=" + testCase.getId() +
                '}';
    }
}

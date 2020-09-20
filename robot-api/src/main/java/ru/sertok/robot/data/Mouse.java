package ru.sertok.robot.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Mouse extends BaseData {
    private String xpath;

    public Mouse(String xpath) {
        super(Type.MOUSE);
        this.xpath = xpath;
    }
}

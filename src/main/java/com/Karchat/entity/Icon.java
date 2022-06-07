package com.Karchat.entity;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Data
@Component
@Scope("prototype")
public class Icon {
    String iconString;
    BufferedImage icon;
}

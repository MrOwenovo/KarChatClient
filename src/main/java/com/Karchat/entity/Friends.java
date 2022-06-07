package com.Karchat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
@Scope("prototype")
public class Friends {
    int index;
    String friends;
    String chatLocation;
}

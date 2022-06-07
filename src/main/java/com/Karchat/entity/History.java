package com.Karchat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 历史记录对象，用来记录历史记录
 */
@Data
@AllArgsConstructor
@Component
@Scope("prototype")
public class History {
    public String type;  //有post和get两种状态
    public String message;  //发送的信息
}

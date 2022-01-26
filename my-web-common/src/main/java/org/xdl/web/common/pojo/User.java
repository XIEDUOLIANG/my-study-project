package org.xdl.web.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XieDuoLiang
 * @date 2022/1/24 下午2:54
 */
@Data
public class User implements Serializable {

    private Long id;

    private String code;

    private String name;
}

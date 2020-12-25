package com.seven.admin.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 10:15
 */
@Data
public class ZtreeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Integer id;

    /** 节点父ID */
    private Integer pId;

    /** 节点名称 */
    private String name;

    /** 节点标题 */
    private String title;

    /** 是否勾选 */
    private boolean checked = false;

    /** 是否展开 */
    private boolean open = false;

    /** 是否能勾选 */
    private boolean nocheck = false;
}

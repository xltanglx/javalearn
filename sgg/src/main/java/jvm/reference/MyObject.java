package jvm.reference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义引用
 * <p>仅用于测试强、软、弱、虚四大引用</p>
 *
 * @author txl
 * @date 2022/8/26 0:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyObject {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
}

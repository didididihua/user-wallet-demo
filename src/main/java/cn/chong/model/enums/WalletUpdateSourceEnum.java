package cn.chong.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 17:17
 * @description 用户钱包执行这次更改的来源
 *              0 -- 购买
 *              1 -- 退款
 *              2 -- 提现
 *              3 -- 充值
 */
public enum WalletUpdateSourceEnum {
    BUY("购买", 0),
    REFUND("退款", 1),
    WITHDRAWAL("提现", 2),
    RECHARGE("充值", 3);

    private final String text;

    private final Integer value;

    WalletUpdateSourceEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static WalletUpdateSourceEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (WalletUpdateSourceEnum anEnum : WalletUpdateSourceEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

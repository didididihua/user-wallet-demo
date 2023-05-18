package cn.chong.model.dto.userWalletDetail;

import cn.chong.common.PageRequest;
import lombok.Data;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 22:49
 * @description
 */
@Data
public class UserWalletDetailRequest extends PageRequest {
    private Long userId;

    private Long Id;
}

package io.github.gsq.hm.common.models;

import io.github.gsq.hm.common.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.common.models.LoginDTO
 *
 * @author : gsq
 * @date : 2024-09-30 17:21
 * @note : It's not technology, it's art !
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO extends BaseModel {

    private boolean auth;

    private String data;

}

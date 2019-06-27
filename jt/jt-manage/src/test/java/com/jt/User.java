package com.jt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

//当程序转化对象时如果遇到未知属性则忽略
@JsonIgnoreProperties(ignoreUnknown = true)
class User {
		private Integer id;
		private String name;
		private Integer age;
}

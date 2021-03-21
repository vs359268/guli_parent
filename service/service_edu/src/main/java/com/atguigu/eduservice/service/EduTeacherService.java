package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author sunhao
 * @since 2020-12-24
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> teacherList(QueryWrapper<EduTeacher> wrapperTeacher);
}

package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author sunhao
 * @since 2021-01-05
 */
@CrossOrigin
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    //1.查询所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTearcher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        System.out.println(list);
        //        try {
//            int a=10/0;
//        }catch (Exception e){
//            throw new GuliException(20001,"执行了自定义异常处理");
//        }
        return R.ok().data("items",list);
    }
    //2.逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R deleteTearcher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }
    //3.分页查询
    //current当前页
    //limit每页记录数
    @ApiOperation(value = "分页查询所有记录")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用实现分页
        //调用方法时，底层封装，把分页所有数据封装到pageTeacher对象中
        eduTeacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
//        Map<String, Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);
        return R.ok().data("total",total).data("rows",records);
    }
    //4.条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现条件查询带分页
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //取出条件值
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //构建查询条件
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.gt("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    //添加讲师接口
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }
        else {
            return R.error();
        }
    }
    //根据讲师ID进行查询
    @ApiOperation(value = "根据讲师ID进行查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    //讲师修改
    //post请求和requestbody一起用，否则会有问题
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }
        else {
            return R.error();
        }
    }
}


package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理Controller
 */
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询部门数据
     * @return
     */
    @GetMapping
    public Result list(){
        log.info("查询全部部门数据");
        //调用service查询部门数据
        List<Dept> deptList =  deptService.list();
        return Result.success(deptList);
    }


    /**
     * 删除部门
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门:{}",id);
        //调用service删除部门
        long nums = deptService.delete(id);
        log.info("删除部门成功,受影响行数:{}",nums);
        return Result.success();
    }


    /**
     * 新增部门
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门: {}" , dept);
        //调用service新增部门
        deptService.add(dept);
        return Result.success();
    }
}

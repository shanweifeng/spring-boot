package com.swf.dao;

import org.springframework.data.repository.CrudRepository;

import com.swf.entity.Demo;

public interface DemoRepository extends CrudRepository<Demo, Long> {

}

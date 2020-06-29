package com.muyun.springboot.repository.general;

import com.muyun.springboot.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author muyun
 * @date 2020/6/25
 */
public interface LogRepository extends JpaRepository<Log, Long> {

}

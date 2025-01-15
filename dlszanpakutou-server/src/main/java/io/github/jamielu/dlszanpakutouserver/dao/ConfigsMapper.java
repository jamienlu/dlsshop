package io.github.jamielu.dlszanpakutouserver.dao;

import io.github.jamielu.dlszanpakutouserver.model.ZanpakutouConfigs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jamieLu
 * @create 2024-04-14
 */
@Repository
@Mapper
public interface ConfigsMapper {
    @Select("select * from zanpakutou_configs where app=#{app} and env=#{env} and ns=#{ns}")
    List<ZanpakutouConfigs> list(String app, String env, String ns);

    @Select("select * from zanpakutou_configs where app=#{app} and env=#{env} and ns=#{ns} and pkey=#{pkey}")
    ZanpakutouConfigs select(String app, String env, String ns, String pkey);

    @Insert("insert into zanpakutou_configs (app, env, ns, pkey, pval) values (#{app}, #{env}, #{ns}, #{pkey}, #{pval})")
    int insert(ZanpakutouConfigs configs);

    @Update("update zanpakutou_configs set pval=#{pval} where app=#{app} and env=#{env} and ns=#{ns} and pkey=#{pkey}")
    int update(ZanpakutouConfigs configs);
}

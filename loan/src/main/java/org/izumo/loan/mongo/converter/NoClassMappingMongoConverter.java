package org.izumo.loan.mongo.converter;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.stereotype.Component;

/**
 * @author Kris
 *  调用mongoTemplate的save方法时, spring-data-mongodb的TypeConverter会自动给document添加一个_class属性, 值是你保存的类名.
 *  spring-data-mongodb是为了在把document转换成Java对象时能够转换到具体的子类. 
 *  可以通过设置MappingMongoConverter的MongoTypeMapper来解决这个问题.
 */
@Component
public class NoClassMappingMongoConverter implements FactoryBean<MappingMongoConverter> {

    @Autowired
    private MappingMongoConverter converter;

    @Override
    public MappingMongoConverter getObject() throws Exception {
        //DefaultMongoTypeMapper类的构造函数的第一个参数是Type在MongoDB中名字. 设置为null的话就不会在保存时自动添加_class属性.
        MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
        this.converter.setTypeMapper(typeMapper);
        return this.converter;
    }

    @Override
    public Class<?> getObjectType() {
        return MappingMongoConverter.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public MappingMongoConverter getConverter() {
        return this.converter;
    }

    public void setConverter(MappingMongoConverter converter) {
        this.converter = converter;
    }

}

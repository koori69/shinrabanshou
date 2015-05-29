package org.izumo.mongo.converter;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;

public class NoClassMappingMongoConverter implements FactoryBean<MappingMongoConverter> {

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

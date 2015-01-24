package org.izumo.loan.mongo.dao;

import org.bson.types.ObjectId;
import org.izumo.loan.mongo.entity.LoanApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanApplicationDao extends MongoRepository<LoanApplication, ObjectId> {
}

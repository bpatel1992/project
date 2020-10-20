package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.configuration.annotations.RepositoryDao;
import com.rahul.project.gateway.utility.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rahul malhotra
 * @version 1.0
 * @Date 2019-04-30
 */

@RepositoryDao
public class PetDao {

    @Autowired
    AbstractDao abstractDao;

    @Autowired
    CommonUtility commonUtility;

}

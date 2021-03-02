package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.ServicePackage;
import org.springframework.stereotype.Repository;

/**
 * ServicePackageRepository to handle any ServicePackage related operations
 *
 * @author Rahul Malhotra
 * @Date : 21-May-2019 at 4:20:57 pm
 */
@Repository(value = "ServicePackageRepository")
public interface ServicePackageRepository extends BaseRepository<ServicePackage, Long> {

}

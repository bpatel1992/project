package com.rahul.project.gateway.repository;


import com.rahul.project.gateway.model.Fee;
import org.springframework.stereotype.Repository;

@Repository(value = "FeeRepository")
public interface FeeRepository extends BaseRepository<Fee, Long>{
//   Fee getByServiceAndAuthority(Services serviceTypeId, Authority userTypeId );
}

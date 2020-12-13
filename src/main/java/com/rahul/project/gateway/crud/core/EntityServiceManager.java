package com.rahul.project.gateway.crud.core;

import com.rahul.project.gateway.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Entity Service Manager Singleton
 * <p>
 * This class resolves the repository for the given entity.
 *
 * @author Rahul Malhotra
 */
@Component
public class EntityServiceManager {

    @Autowired
    ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    public <T, ID> BaseRepository<T, ID> serviceForEntity(String entityName) {
        return (BaseRepository<T, ID>) getBean(entityName + "Repository");
    }

    /*@SuppressWarnings("unchecked")
    public <T, ID> BaseRepository<T, ID> serviceForEntity(String entityName) {
        BaseRepository<T, ID> service = null;
        if ("Role".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(RoleRepository.class);
        } else if ("Privilege".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PrivilegeRepository.class);
        } else if ("Functionality".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(FunctionalityRepository.class);
        } else if ("Feature".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(FeatureRepository.class);
        } else if ("Department".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(DepartmentRepository.class);
        } else if ("User".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(UserRepository.class);
        } else if ("Partner".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PartnerRepository.class);
        } else if ("PetBreed".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PetBreedRepository.class);
        } else if ("Pet".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PetRepository.class);
        } else if ("PetDescription".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PetDescriptionRepository.class);
        } else if ("PetType".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PetTypeRepository.class);
        } else if ("Symptom".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(SymptomRepository.class);
        } else if ("SymptomNode".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(SymptomNodeRepository.class);
        } else if ("SymptomNodeAssessment".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(SymptomNodeAssessmentRepository.class);
        } else if ("Assessment".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(AssessmentRepository.class);
        } else if ("AssessmentOption".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(AssessmentOptionRepository.class);
        } else if ("PetSign".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PetSignRepository.class);
        } else if ("Prevention".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PreventionRepository.class);
        } else if ("PetCondition".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PetConditionRepository.class);
        } else if ("WTDN".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(WTDNRepository.class);
        } else if ("Remedy".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(RemedyRepository.class);
        } else if ("PossibleCause".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PossibleCauseRepository.class);
        } else if ("PossibleCausePriority".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PossibleCausePriorityRepository.class);
        } else if ("PossibleCauseFeedback".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(PossibleCauseFeedbackRepository.class);
        } else if ("RemedyType".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(RemedyTypeRepository.class);
        } else if ("UserLoginPassword".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(UserLoginPasswordRepository.class);
        } else if ("UserLoginType".equalsIgnoreCase(entityName)) {
            service = (BaseRepository<T, ID>) getBean(UserLoginTypeRepository.class);
        } else {
            service = (BaseRepository<T, ID>) getBean(entityName + "Repository");
        }

		*//*if (USER.equalsIgnoreCase(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.userRepository;
		} else if (ROLE.equalsIgnoreCase(entityName)) {
			service = (BaseRepository<T, ID>) applicationContext.getBean("RoleRepository");
		} else if (MENU.equalsIgnoreCase(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.menuRepository;
		} else if (PRIVILEGE.equalsIgnoreCase(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.privilegeRepository;
		} else if (ROLE_MENU_MP.equalsIgnoreCase(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.roleMenuMpRepository;
		} else if (PARTNER_M.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.partnerMRepository;
		} else if (GcsgenericParamsM.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.gcsgenericParamsMRepository;
		} else if (DocumentTypeM.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.documentTypeMRepository;
		} else if (BusinessCategoryM.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.businessCategoryMRepository;
		} else if (MerchantDetailM.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.merchantDetailMRepository;
		} else if (CouponDetailsM.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.couponDetailsMRepository;
		} else if (CouponTypeM.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.couponTypeMRepository;
		} else if (UmGcsuserM.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.umGcsuserMRepository;
		} else if (BusinessSubCategoryMP.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.businessSubCategoryMPRepository;
		} else if (TpagoNetFeeConfigurationS.equals(entityName)) {
			service = (BaseRepository<T, ID>) crudCtrl.tpagoNetFeeConfigurationSRepository;
		}*//*
        return service;
    }*/

    public <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }

    private Object getBean(String bean) {
        return applicationContext.getBean(bean);
    }
}

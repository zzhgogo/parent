package ${packageName}.service;
import org.springframework.stereotype.Service;
import com.ffzxnet.framework.base.BaseServiceImpl;
import ${packageName}.api.I${entityName}Service;
import ${packageName}.dao.${entityName}Mapper;
import ${packageName}.model.${entityName};

/**
*
* @ClassName ${entityName}ServiceImpl
* @Description(这里用一句话描述这个类的作用)
* @author ${author}
* @Date ${createDate}
* @version 1.0.0
*/
@Service("${entityLowerName}Service")
public class ${entityName}ServiceImpl extends BaseServiceImpl
<${entityName}Mapper, ${entityName}> implements I${entityName}Service {


}

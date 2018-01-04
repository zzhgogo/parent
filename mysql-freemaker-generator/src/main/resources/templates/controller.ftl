package ${packageName}.controller;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ffzxnet.framework.base.BaseController;
import com.ffzxnet.framework.constant.DBTypeEnum;
import com.ffzxnet.framework.dao.DbContextHolder;
import com.ffzxnet.framework.util.PageConvert;
import com.ffzxnet.framework.util.PageInfo;
import ${packageName}.constant.BusinessExceptionConstant;
import ${packageName}.constant.UpmsResultConstant;
import ${packageName}.exception.BusinessException;
import com.ffzxnet.framework.base.ErrorResult;
import com.ffzxnet.framework.constant.ErrorConstant;
import ${packageName}.result.UpmsResult;
import ${packageName}.result.UpmsResultData;
import ${packageName}.result.UpmsResultPageData;
import ${packageName}.model.${entityName};
import ${packageName}.api.I${entityName}Service;
/**
*
* @ClassName ${entityName}Controller
* @Description(这里用一句话描述这个类的作用)
* @author ${author}
* @Date ${createDate}
* @version 1.0.0
*/
@Controller
@RequestMapping("/${entityLowerName}")
public class ${entityName}Controller extends BaseController {
private  final Logger logger = LoggerFactory.getLogger(${entityName}Controller.class);
@Autowired
private I${entityName}Service ${entityLowerName}Service;
/**
*
* @Description (测试)查询所有数据--->不分页
* @return
* @throws SQLException
*/
@PostMapping("/${entityLowerName}Object")
@ResponseBody
public Object ${entityLowerName}Object() throws Exception{
EntityWrapper<${entityName}> ew = new EntityWrapper<${entityName}>();
List<${entityName}> meList = null;
try{
meList =  ${entityLowerName}Service.selectList(ew);
}catch(Exception e){
logger.error("${entityName}Controller:${entityLowerName}Object"+e);
throw new SQLException("SQL 数据库异常");
}
return new UpmsResultData(UpmsResultConstant.SUCCESS,meList);
}

/**
* 管理页
*
* @return
*/
@PostMapping("/main")
public ModelAndView main() {
return  new ModelAndView("admin/${entityLowerName}/${entityLowerName}");
}

/**
*
* @Description (mybatis plus原生查询写法)
* @param ${entityName}
* @param page
* @param rows
* @param sort
* @param order
* @return
*/
@PostMapping("/dataGrid")
@ResponseBody
public Object dataGrid(${entityName} ${entityLowerName}, Integer nowpage, Integer pagesize, String sort, String order) throws Exception{
PageInfo pageInfo = new PageInfo(nowpage, pagesize, sort, order);
EntityWrapper<${entityName}> ew = new EntityWrapper<${entityName}>();
if (${entityLowerName}.getCreatedateStart() != null && ${entityLowerName}.getCreatedateEnd() != null) {
ew.and().between("create_time", ${entityLowerName}.getCreatedateStart(), ${entityLowerName}.getCreatedateEnd());
}
PageConvert<${entityName}> pc =new PageConvert<${entityName}>();
Page<${entityName}> pageT =  pc.pageConvert(pageInfo);
try{
pageT = ${entityLowerName}Service.selectPage(pageT, ew);
}catch(Exception e){
logger.error("${entityName}Controller:dataGrid"+e);
throw new SQLException("SQL 数据库异常");
}
return  new UpmsResultPageData(UpmsResultConstant.SUCCESS,pageT.getRecords(),pageT.getTotal(),pageT.getCurrent());
}
/**
*
* @param 新增(${entityName})
* @param result
* @return
* @throws Exception
*/
@PostMapping("/add")
@ResponseBody
public Object add(@Valid ${entityName} ${entityLowerName}, BindingResult result) throws Exception {
if (result.hasErrors()) {
return new ErrorResult(ErrorConstant.VALIDATE_ERROR,result);
}
try{
${entityLowerName}.setCreateBy(this.getStaffName());
DbContextHolder.setDbType(DBTypeEnum.one);
${entityLowerName}Service.insert(${entityLowerName});
}catch(Exception e){
logger.error("${entityName}Controller:add"+e);
throw new SQLException("SQL 数据库异常");
}
return new UpmsResult(UpmsResultConstant.SUCCESS);
}

/**
*
* @param ajx请求(${entityName})
* @return
*/
@PostMapping("/editAjax")
@ResponseBody
public Object editPage(${entityName} ${entityLowerName}) {
return  new UpmsResultData(UpmsResultConstant.SUCCESS,${entityLowerName}Service.selectById(${entityLowerName}.getId()));
}

/**
*
* @param 修改(${entityName})
* @param result
* @return
* @throws Exception
*/
@PostMapping("/edit")
@ResponseBody
public Object edit(@Valid ${entityName} ${entityLowerName}, BindingResult result) throws Exception{
if (result.hasErrors()) {
return new ErrorResult(ErrorConstant.VALIDATE_ERROR,result);
}
try{
${entityLowerName}.setModifyTime(new Date());
${entityLowerName}.setModifyBy(this.getStaffName());
DbContextHolder.setDbType(DBTypeEnum.one);
${entityLowerName}Service.updateById(${entityLowerName});
}catch(Exception e){
logger.error("${entityName}Controller:edit"+e);
throw new SQLException("SQL 数据库异常");
}
return new UpmsResult(UpmsResultConstant.SUCCESS);
}


/**
*
* @param 删除(${entityLowerName})
* @return
* @throws Exception
*/
@PostMapping("/delete")
@ResponseBody
public Object delete(${entityName} ${entityLowerName}) throws Exception{
try{
DbContextHolder.setDbType(DBTypeEnum.one);
${entityLowerName}Service.deleteById(${entityLowerName}.getId());
}catch(Exception e){
logger.error("${entityName}Controller:delete"+e);
throw new SQLException("SQL 数据库异常");
}
return new UpmsResult(UpmsResultConstant.SUCCESS);
}
/**
*
* @Description (测试3)
* @return
* @throws BUSINESSEXCEPTION
*/
@PostMapping("/${entityLowerName}3")
@ResponseBody
public Object ${entityLowerName}3() throws Exception{
logger.error("${entityName}Controller:${entityLowerName}3"+BusinessExceptionConstant.BUSINESSEXCEPTION);
throw new BusinessException(BusinessExceptionConstant.BUSINESSEXCEPTION);
}
/**
*
* @Description
* @return
* @throws BUSINESSEXCEPTION
*/
@PostMapping("/${entityLowerName}4")
@ResponseBody
public Object ${entityLowerName}4() throws Exception{
throw new Exception();
}
}
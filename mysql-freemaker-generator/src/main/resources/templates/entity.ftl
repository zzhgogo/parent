package ${packageName}.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ffzxnet.framework.base.BaseClass;
/**
*
* @ClassName ${entityName} for ${tableName}
* @Description(${tableComment})
* @author ${author}
* @Date ${createDate}
* @version 1.0.0
*/
@TableName("${tableName}")
public class ${entityName} extends BaseClass implements Serializable {

@TableField(exist=false)
private static final long serialVersionUID = 1L;
<#list entityFieldList as column>
    <#if column.columnName == 'id' || column.columnName == 'create_by' || column.columnName == 'modify_by' || column.columnName == 'create_time' || column.columnName == 'modify_time'>
    <#else>
    /**${column.comment}*/
    @TableField(value = "${column.columnName}")
    private ${column.propertyType} ${column.lowerPropertyName};
    </#if>
</#list>

<#list entityFieldList as method>
    <#if method.columnName == 'id' || method.columnName == 'create_by' || method.columnName == 'modify_by' || method.columnName == 'create_time' || method.columnName == 'modify_time'>
    <#else>
    public void set${method.upperPropertyName}(${method.propertyType} ${method.lowerPropertyName}) {
    this.${method.lowerPropertyName} = ${method.lowerPropertyName};
    }

    public ${method.propertyType} get${method.upperPropertyName}() {
    return this.${method.lowerPropertyName};
    }
    </#if>
</#list>
}

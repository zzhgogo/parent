package com.ffzxnet.generator;

import com.ffzxnet.generator.AutoGenerator.GeneratorListener;

import java.io.Serializable;

/**
 * <p><b>使用此代码生成器必须遵循以下命名规则，否则无法生成代码</b></p>
 * <b>数据库表名规则：</b>数据库前缀_数据库表名称<br />
 * <b>数据库表字段规则：</b>数据库表前缀_数据库字段名<br />
 *
 * @author summerrains
 */
public class ConfigGenerator implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 保存目录
     */
    private String saveDir;
    /**
     * 数据库驱动
     */
    private String dbDriverName;
    /**
     * 用户名
     */
    private String dbUser;
    /**
     * 密码
     */
    private String dbPassword;
    /**
     * url
     */
    private String dbUrl;
    /**
     * 表名
     */
    private String[] tables;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 表前缀
     */
    private boolean tablePrefix = false;
    /**
     * 字段前缀
     */
    private boolean fieldPrefix = false;
    /**
     * base_column_list前缀
     */
    private boolean columnPrefix = false;

    private String entityTemplete;
    private String daoTemplete;
    private String mapperTemplete;
    private String serviceTemplete;
    private String serviceImplTemplete;
    private String controllerTemplete;
    private String ftlTemplete;

    /**
     * 监听器
     */
    private GeneratorListener generatorListener;


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(boolean tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public boolean isFieldPrefix() {
        return fieldPrefix;
    }

    public void setFieldPrefix(boolean fieldPrefix) {
        this.fieldPrefix = fieldPrefix;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }

    public String getDbDriverName() {
        return dbDriverName;
    }

    public void setDbDriverName(String dbDriverName) {
        this.dbDriverName = dbDriverName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public boolean isColumnPrefix() {
        return columnPrefix;
    }

    public void setColumnPrefix(boolean columnPrefix) {
        this.columnPrefix = columnPrefix;
    }

    public GeneratorListener getGeneratorListener() {
        if (generatorListener == null) {
            generatorListener = new GeneratorListener() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onProcess(String tableName, String filePath) {
                }

                @Override
                public void onError(String message, Throwable throwable) {
                }
            };
        }
        return generatorListener;
    }

    public void setGeneratorListener(GeneratorListener generatorListener) {
        this.generatorListener = generatorListener;
    }

    public String getEntityTemplete() {
        if (entityTemplete == null) {
            entityTemplete = TempleteUtils.readTemplete(ConfigGenerator.class, "/templates/entity.ftl");
        }
        return entityTemplete;
    }

    public void setEntityTemplete(String entityTemplete) {
        this.entityTemplete = entityTemplete;
    }


    public String getFtlTemplete() {
        if (ftlTemplete == null) {
            ftlTemplete = TempleteUtils.readTemplete(ConfigGenerator.class, "/templates/ftl.ftl");
        }
        return ftlTemplete;
    }


    public void setFtlTemplete(String ftlTemplete) {
        this.ftlTemplete = ftlTemplete;
    }

    public String getDaoTemplete() {
        if (daoTemplete == null) {
            daoTemplete = TempleteUtils.readTemplete(ConfigGenerator.class, "/templates/dao.ftl");
        }
        return daoTemplete;
    }

    public void setDaoTemplete(String daoTemplete) {
        this.daoTemplete = daoTemplete;
    }

    public String getMapperTemplete() {
        if (mapperTemplete == null) {
            mapperTemplete = TempleteUtils.readTemplete(ConfigGenerator.class, "/templates/mapper.ftl");
        }
        return mapperTemplete;
    }

    public void setMapperTemplete(String mapperTemplete) {
        this.mapperTemplete = mapperTemplete;
    }

    public String getServiceTemplete() {
        if (serviceTemplete == null) {
            serviceTemplete = TempleteUtils.readTemplete(ConfigGenerator.class, "/templates/service.ftl");
        }
        return serviceTemplete;
    }

    public void setServiceTemplete(String serviceTemplete) {
        this.serviceTemplete = serviceTemplete;
    }

    public String getServiceImplTemplete() {
        if (serviceImplTemplete == null) {
            serviceImplTemplete = TempleteUtils.readTemplete(ConfigGenerator.class, "/templates/serviceImpl.ftl");
        }
        return serviceImplTemplete;
    }

    public void setServiceImplTemplete(String serviceImplTemplete) {
        this.serviceImplTemplete = serviceImplTemplete;
    }

    public String getControllerTemplete() {
        if (controllerTemplete == null) {
            controllerTemplete = TempleteUtils.readTemplete(ConfigGenerator.class, "/templates/controller.ftl");
        }
        return controllerTemplete;
    }

    public void setControllerTemplete(String controllerTemplete) {
        this.controllerTemplete = controllerTemplete;
    }


}

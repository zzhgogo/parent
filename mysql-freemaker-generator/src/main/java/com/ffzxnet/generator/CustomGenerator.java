package com.ffzxnet.generator;

/**
 * 代码生成器Demo
 *
 * @author summerrains
 */
public class CustomGenerator {

    public static void main(String[] args) {

        ConfigGenerator cg = new ConfigGenerator();
        cg.setDbDriverName("com.mysql.jdbc.Driver");
        cg.setDbUrl("jdbc:mysql://192.168.11.220:3306/cms_db");
        cg.setDbUser("root");
        cg.setDbPassword("");
        cg.setSaveDir("D://source");
        //去除表前缀
        cg.setTablePrefix(false);
        //去除字段前缀
        cg.setFieldPrefix(false);
        //base cloumn是否添加前缀
        cg.setColumnPrefix(false);
//        cg.setTables(new String[] { "t_users" });
        cg.setPackageName("com.ffzx.cms");
//		cg.setTables(new String[]{"hibernate_unique_key"});
        cg.setGeneratorListener(new AutoGenerator.GeneratorListener() {
            @Override
            public void onSuccess() {
                System.out.println("dnoe");
            }

            @Override
            public void onProcess(String tableName, String filePath) {
                System.out.println(tableName + " generator to " + filePath);
            }

            @Override
            public void onError(String message, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        AutoGenerator.run(cg);

    }

}

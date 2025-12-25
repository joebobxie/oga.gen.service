package com.gen.service.module.db.service.impl;

import com.gen.service.common.exception.ApiError;
import com.gen.service.common.exception.ApiRest;
import com.gen.service.model.vo.BaseStringVO;
import com.gen.service.module.db.dto.ConfigDTO;
import com.gen.service.module.db.dto.DbConfigDTO;
import com.gen.service.module.db.dto.DbStructureDTO;
import com.gen.service.module.db.dto.GenerateDTO;
import com.gen.service.module.db.service.DBService;
import com.gen.service.module.db.vo.*;
import com.gen.service.utility.JavaTemplate;
import com.gen.service.utility.JdbcHelper;
import com.gen.service.utility.StringUti;
import com.gen.service.utility.ThymeleafHelper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * DB Connection
 */
@Service
public class DBServiceImpl implements DBService {

    /**
     * Database List
     */
    @Override
    public List<DbVO> dbList(DbConfigDTO dto) {
        System.out.println(dto.getUrl());

        List<DbVO> list = new ArrayList<>();
        JdbcHelper db = new JdbcHelper(dto);
        db.openConnection();
        ResultSet rs = db.execQuery("SELECT SCHEMA_NAME as name,DEFAULT_COLLATION_NAME as charset FROM information_schema.SCHEMATA WHERE SCHEMA_NAME NOT IN ('information_schema', 'mysql', 'performance_schema', 'sys'); ");
        try {
            while (rs.next()) {
                DbVO entity = new DbVO();
                entity.setName(rs.getString(1))
                        .setCharset(rs.getString(2))
                        .setLeaf(1)
                        .setLabel(entity.getName());
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeAll();
        }
        return list;
    }

    /**
     * Database Structure
     */
    @Override
    public DbStructureVO dbStructure(DbStructureDTO dto) {
        DbStructureVO result = new DbStructureVO();

        List<String> tabs = new ArrayList<>();

        List<TableVO> tables = new ArrayList<>();
        List<TableVO> views = new ArrayList<>();
        List<TableVO> stores = new ArrayList<>();

        try {
            String sql = String.format("SELECT a.table_name AS TableName,b.CHARACTER_MAXIMUM_LENGTH AS Length,a.table_comment AS TableDesc,b.COLUMN_NAME AS ColumnName,b.column_comment AS ColumnDesc,b.column_type AS ColumnType,(CASE WHEN b.column_key = 'PRI' THEN N'1'ELSE N'0' END) AS PrimaryKey,(CASE WHEN b.IS_NULLABLE = 'YES' THEN N'1'ELSE N'0' END) AS NullAble,b.DATA_TYPE AS DBType,(CASE WHEN b.EXTRA = 'AUTO_INCREMENT' THEN N'1'ELSE N'0' END) AS Identity FROM information_schema.TABLES a LEFT JOIN information_schema.COLUMNS b ON a.table_name = b.TABLE_NAME WHERE a.TABLE_SCHEMA = b.TABLE_SCHEMA AND a.TABLE_SCHEMA = '%s' ORDER BY a.table_name,PrimaryKey DESC,ColumnName ASC", dto.getDb().getDbName());
            JdbcHelper db = new JdbcHelper(dto.getDb());
            db.openConnection();

            ResultSet rs = db.execQuery(sql);
            // System.out.println(sql);
            List<Map<String, Object>> list = new ArrayList<>();
            try {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("TableName", rs.getString("TableName"));
                    map.put("TableDesc", rs.getString("TableDesc"));
                    map.put("ColumnName", rs.getString("ColumnName"));
                    map.put("ColumnDesc", rs.getString("ColumnDesc"));
                    map.put("ColumnType", rs.getString("ColumnType").toUpperCase());
                    map.put("PrimaryKey", rs.getString("PrimaryKey"));
                    map.put("NullAble", rs.getString("NullAble"));
                    map.put("Identity", rs.getString("Identity"));
                    map.put("DBType", rs.getString("DBType"));
                    map.put("Length", rs.getString("Length"));

                    list.add(map);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeAll();
            }
            for (Map<String, Object> m : list) {
                String tableName = m.get("TableName").toString();
                if (!tabs.contains(tableName)) {
                    TableVO tableVO = new TableVO();
                    List<String> dbType = new ArrayList<>();
                    tableVO.setDescribe(m.get("TableDesc").toString())
                            .setName(tableName)
                            .setClassName(getClassName(tableName))
                            .setLowerClassName(this.firstLowerCase(tableVO.getClassName()))
                            .setLabel(tableName)
                            .setLeaf(1);

                    if (StringUtils.isEmpty(tableVO.getDescribe())) {
                        tableVO.setDescribe(tableName);
                    }

                    List<FiledVO> fields = new ArrayList<>();

                    for (Map<String, Object> sub : list) {
                        if (tableName.equals(sub.get("TableName").toString())) {
                            FiledVO filed = new FiledVO();
                            filed.setName(sub.get("ColumnName").toString())
                                    .setDescribe(sub.get("ColumnDesc").toString())
                                    .setColumnType(sub.get("ColumnType").toString())
                                    .setDbType(sub.get("DBType").toString())
                                    .setPrimary(sub.get("PrimaryKey").toString().equals("1") ? 1 : 0)
                                    .setNullable(sub.get("NullAble").toString().equals("1") ? 1 : 0)
                                    .setIdentity(sub.get("Identity").toString().equals("1") ? 1 : 0)
                                    .setSize(StringUti.getLong(sub.get("Length")))
                                    .setJavaName(this.getJavaFiledName(filed.getName()))
                                    .setClassName(this.getClassName(filed.getName()))
                                    .setLabel(String.format("%s <small>%s</small>", filed.getName(), filed.getColumnType()));

                            FiledTypeVO filedTypeVO = this.jdbcToJavaType(filed.getDbType());

                            if (!dbType.contains(filedTypeVO.getDbType())) {
                                dbType.add(filedTypeVO.getDbType());
                            }
                            filed.setFieldType(filedTypeVO.getName())
                                    .setDefaultValue(filedTypeVO.getValue())
                                    .setLabel(String.format("%s(%s)", filed.getName(), filed.getColumnType()))
                                    .setIsLeaf(false);

                            fields.add(filed);

                            if (StringUtils.isEmpty(filed.getDescribe())) {
                                filed.setDescribe(filed.getName());
                            }
                        }
                    }
                    tableVO.setDbType(dbType)
                            .setChildren(fields)
                            .setVueConfig(this.getVueConfig(tableVO, dto.getConfig()));
                    tables.add(tableVO);
                    tabs.add(tableName);
                }
            }
        } catch (Exception e) {
            //msgEntity.setMessage(e.getMessage());
            e.printStackTrace();
        }

        result.setFun(stores)
                .setTable(tables)
                .setView(views);
        return result;
    }

    /**
     * Class Name
     *
     * @param name Table Name
     */
    private String getClassName(String name) {
        List<String> list = this.getTableToFirstUpperList(name);
        return StringUtils.join(list, "");
    }

    /**
     * Table Name To FirstUpper List
     *
     * @param name Table Name
     */
    private List<String> getTableToFirstUpperList(String name) {
        List<String> list = Lists.newArrayList();
        for (String value : name.split("_")) {
            if (!StringUtils.isEmpty(value)) {
                list.add(StringUti.firstUpperCase(value));
            }
        }
        return list;
    }

    /**
     * first word to lower case
     *
     * @param name Table Name
     */
    private String firstLowerCase(String name) {
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }

    /**
     * filed for java
     *
     * @param name Table Name
     */
    private String getJavaFiledName(String name) {
        return this.firstLowerCase(getClassName(name));
    }

    /**
     * JDBC Type To Java Type
     *
     * @param jdbcName JDBC Name
     */
    private FiledTypeVO jdbcToJavaType(String jdbcName) {
        // System.out.println(jdbcName);
        FiledTypeVO filedTypeVO = new FiledTypeVO();
        if (StringUtils.isEmpty(jdbcName)) {
            return filedTypeVO;
        }
        switch (jdbcName.toLowerCase()) {
            case "blob":
                filedTypeVO.setName("byte[]");
                filedTypeVO.setValue("0");
                break;
            case "tinyint":
            case "int":
            case "smallint":
            case "mediumint":
                filedTypeVO.setName("Integer");
                filedTypeVO.setValue("0");
                break;
            case "bit":
                filedTypeVO.setName("Boolean");
                filedTypeVO.setValue("0");
                break;
            case "float":
                filedTypeVO.setName("Float");
                filedTypeVO.setValue("0");
                break;
            case "double":
                filedTypeVO.setName("Double");
                filedTypeVO.setValue("0");
                break;
            case "decimal":
                filedTypeVO.setName("BigDecimal");
                filedTypeVO.setValue("0");
                filedTypeVO.setDbType("import java.math.BigDecimal;");
                break;
            case "bigint":
            case "id":
                filedTypeVO.setName("Long");
                filedTypeVO.setValue("0");
                break;
            case "date":
            case "time":
            case "datetime":
            case "timestamp":
            case "year":
                filedTypeVO.setName("Date");
                filedTypeVO.setValue("new Date()");
                filedTypeVO.setDbType("import java.util.Date;");
                break;
            case "longtext":
            case "varchar":
            case "char":
            case "text":
            case "mediumtext":
            default:
                filedTypeVO.setName("String");
                filedTypeVO.setValue("''");
                break;
        }
        return filedTypeVO;
    }

    /**
     * Get Vue Config
     *
     * @param table Table
     */
    private VueConfigVO getVueConfig(TableVO table, ConfigDTO config) {
        VueConfigVO entity = new VueConfigVO();
        List<String> func = getTableToFirstUpperList(table.getName());
        List<String> path = getTableToFirstUpperList(table.getName());
        if (path.size() > 1) {
            path.remove(0);
        }
        entity.setCatalog(getModule(table.getName()).toLowerCase());
        entity.setFun(func.get(func.size() - 1));
        entity.setPrefix(table.getClassName());
        entity.setRouterName(String.join("-", func).toLowerCase());
        entity.setI18n(firstLowerCase(String.join("", path)));
        entity.setPath(String.join("-", path).toLowerCase());
        entity.setClassName(entity.getI18n());
        if (config != null) {
            entity.setRouter(
                    String.format("/%s/api/%s/%s",
                            config.getName(),
                            !func.isEmpty() ? func.get(0).toLowerCase() : "",
                            entity.getPath()
                    )
            );
            entity.setJavaPath(
                    String.format("%s/src/main/java/%s/module/%s",
                            config.getPath(),
                            config.getPackageName().replace(".", "/"),
                            !func.isEmpty() ? func.get(0).toLowerCase() : ""
                    )
            );
            entity.setMapperPath(
                    String.format("%s/src/main/resources/mapper/%s",
                            config.getPath(),
                            entity.getCatalog()
                    )
            );
            entity.setVuePath(
                    String.format("/src/views/app/%s/%s/%s/",
                            config.getName(),
                            !func.isEmpty() ? func.get(0).toLowerCase() : "",
                            entity.getPath()
                    )
            );
        }
        return entity;
    }

    /**
     * function module
     *
     * @param name Table Name
     */
    private String getModule(String name) {
        List<String> list = this.getTableToFirstUpperList(name);
        return !list.isEmpty() ? list.get(0) : "";
    }

    /**
     * Database HTML
     */
    @Override
    public ApiRest<BaseStringVO> dbDocument(DbStructureDTO dto) {
        BaseStringVO baseStringVO = new BaseStringVO();
        DbStructureVO db = this.dbStructure(dto);
        ApiRest<BaseStringVO> resultBase = new ApiRest<>();
        try {
            List<String> modules = Lists.newArrayList();
            List<String> left = Lists.newArrayList();
            List<String> right = Lists.newArrayList();
            for (TableVO table : db.getTable()) {

                String module = getModule(table.getName());

                if (!modules.contains(module)) {

                    left.add(String.format("<h3>%s</h3>", module));
                    left.add("<dl>");

                    for (TableVO sub : db.getTable()) {
                        String subModule = getModule(sub.getName());
                        if (subModule.equals(module)) {
                            right.add(String.format("<div class=\"block\" id=\"%s\">", sub.getName()));
                            right.add(String.format("<small>%s</small>", sub.getName()));
                            right.add(String.format("<h3>%s</h3>", sub.getDescribe()));
                            right.add("<table>");
                            right.add("<tr>");
                            right.add("<td>Filed</td>");
                            right.add("<td>JAVA Variable</td>");
                            right.add("<td>Filed Type</td>");
                            right.add("<td>Length</td>");
                            right.add("<td>Comment</td>");
                            right.add("<td>Not Null</td>");
                            right.add("<td>Auto Increment</td>");
                            right.add("<td>Key</td>");
                            right.add("</tr>");
                            //ㄨ
                            for (FiledVO field : sub.getChildren()) {
                                right.add("<tr>");
                                right.add(String.format("<td>%s</td>", field.getName()));
                                right.add(String.format("<td>%s</td>", getJavaFiledName(field.getName())));
                                right.add(String.format("<td>%s</td>", field.getDbType().toUpperCase()));
                                right.add(String.format("<td>%s</td>", field.getColumnType()));
                                right.add(String.format("<td>%s</td>", field.getDescribe()));
                                right.add(String.format("<td>%s</td>", field.getNullable() == 0 ? "◍" : ""));
                                right.add(String.format("<td>%s</td>", field.getIdentity() == 1 ? "◍" : ""));
                                right.add(String.format("<td>%s</td>", field.getPrimary() == 1 ? "◍" : ""));
                                right.add("</tr>");
                            }

                            right.add("</table>");
                            right.add("</div>");

                            left.add(String.format("<dd rel=\"%s\">%s</dd>", sub.getName(), sub.getDescribe()));
                        }

                    }
                    left.add("</dl>");
                    modules.add(module);
                }

            }
            String html = this.getClasResources(JavaTemplate.db_structure);
            System.out.println("html" + html);

            html = html.replace("{menu}", StringUtils.join(left, ""))
                    .replace("{item}", StringUtils.join(right, ""))
                    .replace("{table.module}", StringUti.getString(modules.size(), ""))
                    .replace("{table.count}", StringUti.getString(db.getTable().size(), "0"))
                    .replace("{db.name}", dto.getDb().getDbName())
                    .replace("{date}", StringUti.getCurrDateString());
            baseStringVO.setContent(html);
            resultBase.setCode(ApiError.Success.code);
            resultBase.setMessage(ApiError.Success.message);
        } catch (Exception e) {
            resultBase.setCode(ApiError.Failed.code);
            resultBase.setMessage(ApiError.Failed.message);
        }

        resultBase.setData(baseStringVO);
        return resultBase;
    }

    /**
     * read text file
     *
     * @param templateFile template file
     */
    private String getClasResources(String templateFile) {
        String fileName = Objects.requireNonNull(DBServiceImpl.class.getClassLoader().getResource(templateFile)).getPath();
        StringBuilder sb = new StringBuilder();
        try {
            File file = ResourceUtils.getFile("classpath:" + templateFile);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(read);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                read.close();
            } else {
                System.out.printf("cont find file：%s%n", fileName);
            }
        } catch (Exception e) {
            System.out.printf("read file error：%s%n", fileName);
        }
        return sb.toString();
    }

    /**
     * Code Source
     */
    @Override
    public ApiRest<BaseStringVO> codeSource(GenerateDTO dto) {
        ApiRest<BaseStringVO> resultBase = new ApiRest<>();
        BaseStringVO baseString = new BaseStringVO();
        String html = "";
        if (dto.getCodeType().equals("file")) {
            html = "";
        } else {
            html = ThymeleafHelper.generateTemplate(dto, dto.getCodeType());
        }
        baseString.setContent(html);
        resultBase.setData(baseString);
        resultBase.setCode(ApiError.Success.code);
        resultBase.setMessage(ApiError.Success.message);
        return resultBase;
    }
}

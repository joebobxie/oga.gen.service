package com.gen.service.utility;

import com.alibaba.fastjson.JSON;
import com.gen.service.module.db.dto.GenerateDTO;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Thymeleaf Helper
 */
public class ThymeleafHelper {
    /**
     * 渲染模板、产生渲染后的HTML或字符
     *
     */
    public static String generateTemplate(GenerateDTO generateEntity, String template) {

        String html = "";
        try {
            generateEntity.getConfig().setAppName(StringUti.firstUpperCase(generateEntity.getConfig().getName()));
            html = loadTemplate(template);

            Map<String, Object> data = JSON.parseObject(JSON.toJSONString(generateEntity), Map.class);
            StringTemplateResolver resolver = new StringTemplateResolver();
            resolver.setTemplateMode(TemplateMode.TEXT);
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);
            Context context = new Context();

            if (data != null && !data.isEmpty()) {
                for (String key : data.keySet()) {
                    context.setVariable(key, data.get(key));
                }
            }

            html = templateEngine.process(html, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    /**
     * Load template
     *
     */
    private static String loadTemplate(String fileName) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        InputStream stream = resourceLoader.getResource(String.format("classpath:templates/%s.txt", fileName)).getInputStream();

        StringBuilder sb = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(stream); BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
